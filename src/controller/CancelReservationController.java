package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.mystudy.DB.DBService;

import dao.ReservationDAO;
import dao.UserDAO;
import dao.SeatDAO;
import dao.ConcertDAO;

import VO.ReservationsVO;
import VO.UserVO;
import VO.ConcertVO;

@WebServlet("/cancelReservation")
public class CancelReservationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ReservationDAO reservationDAO = new ReservationDAO();
    private UserDAO userDAO = new UserDAO(DBService.getFactory());
    private SeatDAO seatDAO = new SeatDAO();
    private ConcertDAO concertDAO = new ConcertDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 0. 세션 확인 및 로그인 사용자 정보
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("loginUser") == null) {
                out.println("로그인이 필요합니다.");
                return;
            }

            UserVO user = (UserVO) session.getAttribute("loginUser");

            // 1. 파라미터 수신
            int reservationId = Integer.parseInt(request.getParameter("reservationId"));
            String inputPw = request.getParameter("pw");

            // 2. 비밀번호 검증
            if (!user.getPw().equals(inputPw)) {
                out.println("비밀번호가 일치하지 않습니다.");
                return;
            }

            // 3. 예약 정보 확인
            ReservationsVO reservation = reservationDAO.getReservationById(reservationId);
            if (reservation == null) {
                out.println("예약 정보를 찾을 수 없습니다.");
                return;
            }

            // 4. 본인 예약인지 확인
            if (reservation.getUserId() != user.getId()) {
                out.println("본인의 예약만 취소할 수 있습니다.");
                return;
            }

            // 5. 공연 시작 1시간 전 여부 확인
            ConcertVO concert = concertDAO.getConcertById(reservation.getConcertId());
            if (concert == null) {
                out.println("공연 정보를 찾을 수 없습니다.");
                return;
            }

            Date concertDate = concert.getConcertDate(); // java.util.Date
            String startTimeStr = concert.getStartTime(); // "HH:mm"

            LocalDate datePart = concertDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime timePart = LocalTime.parse(startTimeStr);
            LocalDateTime concertStart = LocalDateTime.of(datePart, timePart);
            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(concertStart.minusHours(1))) {
                out.println("공연 시작 1시간 전에는 예약을 취소할 수 없습니다.");
                return;
            }

            // 6. 예약 취소 및 좌석 복구
            reservationDAO.cancelReservation(reservationId);
            seatDAO.updateSeatAvailability(reservation.getSeatId(), "Y");

            out.println("예약이 성공적으로 취소되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("예약 취소 중 오류가 발생했습니다.");
        }
    }
}
