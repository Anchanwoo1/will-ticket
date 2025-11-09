package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import VO.ReservationsVO;
import VO.UserVO;
import dao.ReservationDAO;
import dao.SeatDAO;
import com.mystudy.DB.*;

@WebServlet("/pay/success")
public class PaymentSuccessController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String concertIdStr = req.getParameter("concertId");
        String selectedSeats = req.getParameter("selectedSeats");

        if (concertIdStr == null || selectedSeats == null || selectedSeats.trim().isEmpty()) {
            req.setAttribute("errorMsg", "결제 정보가 올바르지 않습니다.");
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            return;
        }

        int concertId = Integer.parseInt(concertIdStr);
        String[] seats = selectedSeats.split(",");

        SeatDAO seatDao = new SeatDAO();
        int userId = loginUser.getId();

        // ✅ 예매 수 제한 검사 (기존 예약 + 현재 선택 > 4)
        int existingReservationCount = seatDao.countReservationsByUser(userId, concertId);
        if (existingReservationCount + seats.length > 4) {
            req.setAttribute("errorCode", "LIMIT_EXCEEDED");
            req.setAttribute("currentCount", existingReservationCount);
            req.setAttribute("selectedCount", seats.length);
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            return;
        }

        // ✅ 중복 예매 방지를 위한 좌석 가능 여부 확인
        for (String seatNum : seats) {
            seatNum = seatNum.trim();
            int seatId = seatDao.selectSeatId(concertId, seatNum);
            String availability = seatDao.selectAvailabilityById(seatId);
            if (!"Y".equalsIgnoreCase(availability)) {
                req.setAttribute("errorMsg", "결제 후 좌석 예약 실패 - 이미 예약된 좌석 포함: " + seatNum);
                req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
                return;
            }
        }

        // ✅ 좌석 예약 처리 및 예약 테이블 insert
        for (String seatNum : seats) {
            seatNum = seatNum.trim();
            int seatId = seatDao.selectSeatId(concertId, seatNum);

            seatDao.updateSeatAvailabilityAndUser(seatId, "N", userId);

            ReservationsVO resVO = new ReservationsVO();
            resVO.setUserId(userId);
            resVO.setConcertId(concertId);
            resVO.setSeatId(seatId);
            resVO.setStatus("결제완료");

            ReservationDAO.insertReservation(resVO);
        }

        // ✅ 결제 완료 페이지로 이동
        String concertTitle = seatDao.selectTitleByConcertId(concertId); //추가
        
        req.setAttribute("concertId", concertIdStr);
        req.setAttribute("selectedSeats", selectedSeats);
        req.setAttribute("paymentMethod", req.getParameter("paymentMethod"));
        req.setAttribute("title", concertTitle); // 추가
        
        req.getRequestDispatcher("/pay/paymentSuccess.jsp").forward(req, resp);
    }
}
