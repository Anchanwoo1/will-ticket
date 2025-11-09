package controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import VO.ConcertVO;
import VO.ReservationsVO;
import VO.UserVO;
import dao.ConcertDAO;
import dao.ReservationDAO;
import dao.SeatDAO;

@WebServlet("/myreserve")
public class ReserveController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ConcertDAO concertDao = new ConcertDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdParam = request.getParameter("userId");
        String title = request.getParameter("title");
        String category = request.getParameter("category");

        if (userIdParam != null && !userIdParam.isEmpty()) {
            try {
                int userId = Integer.parseInt(userIdParam);
                List<ReservationsVO> reservations = ReservationDAO.getReservationsWithDetailsByUserId(userId);

                Map<Integer, Date> concertDates = new HashMap<>();
                for (ReservationsVO r : reservations) {
                    Date concertDate = reservationDAO.getConcertDateByReservationId(r.getConcertId());
                    concertDates.put(r.getReservationId(), concertDate);

                    // 콘솔 로그
                    System.out.println("🎵 콘서트 날짜 확인 - 예약ID: " + r.getReservationId() + " / 콘서트ID: " + r.getConcertId() + " / 날짜: " + concertDate);
                }

                request.setAttribute("reservations", reservations);
                request.setAttribute("concertDates", concertDates);
                request.getRequestDispatcher("/reservationsByUser.jsp").forward(request, response);
                return;
            } catch (NumberFormatException e) {
                request.setAttribute("error", "유효한 userId가 아닙니다.");
            }
        }

        List<ConcertVO> concertList = null;
        if (title != null && category != null && !title.isEmpty() && !category.isEmpty()) {
            concertList = concertDao.getConcertsByTitleAndCategory(title, category);
        }

        request.setAttribute("concertList", concertList);
        request.getRequestDispatcher("/Reserve.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            session.setAttribute("errorMsg", "로그인이 필요합니다.");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String selectedSeats = req.getParameter("selectedSeats");
        
        String concertIdStr = req.getParameter("concertId");
        String title = req.getParameter("title");

        if (selectedSeats == null || selectedSeats.isEmpty() || concertIdStr == null) {
            session.setAttribute("errorMsg", "예약할 좌석 정보가 부족합니다.");
            resp.sendRedirect(req.getContextPath() + "/seatSelect.jsp?concertId=" + concertIdStr);
            return;
        }

        int concertId = Integer.parseInt(concertIdStr);
        String[] seats = selectedSeats.split(",");
        
     // << 여기 추가 >>
        if (seats.length > 4) {
            resp.sendRedirect(req.getContextPath() + "/seatSelect.jsp?concertId=" + concertIdStr
                    + "&errorCode=LIMIT_EXCEEDED&selectedCount=" + seats.length);
            return;
        }

        SeatDAO seatDao = new SeatDAO();
        int userId = loginUser.getId();

        // 좌석 중복 확인
        for (String seatNum : seats) {
            seatNum = seatNum.trim();
            int seatId = seatDao.selectSeatId(concertId, seatNum);
            String availability = seatDao.selectAvailabilityById(seatId);
            if (!"Y".equals(availability)) {
                session.setAttribute("errorMsg", "이미 예약된 좌석이 포함되어 있습니다: " + seatNum);
                resp.sendRedirect(req.getContextPath() + "/seatSelect.jsp?concertId=" + concertId);
                return;
            }
        }

        // 총 가격 계산
        int totalPrice = 0;
        for (String seatNum : seats) {
            seatNum = seatNum.trim();
            totalPrice += seatDao.selectPriceBySeatNumber(concertId, seatNum);
        }
        
        

        req.setAttribute("selectedSeats", selectedSeats);
        req.setAttribute("totalPrice", totalPrice);
        req.setAttribute("concertId", concertIdStr);
        req.setAttribute("title", title);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/pay/payment.jsp");
        dispatcher.forward(req, resp);
    }
}
