package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.mystudy.DB.DBService;
import VO.ReservationsVO;
import VO.UserVO;
import dao.ReservationDAO;
import dao.SeatDAO;

@WebServlet("/concert/reserveSeats")
public class ReserveSeatsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final ReservationDAO resDao = new ReservationDAO();
	private final SeatDAO seatDao = new SeatDAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}

		int concertId = Integer.parseInt(req.getParameter("concertId"));
		String seatNumber = req.getParameter("seatNumber");
		
		// Java 8 호환 방식으로 공백 체크
		if (seatNumber == null || seatNumber.trim().isEmpty()) {
			session.setAttribute("errorMsg", "좌석을 선택해주세요.");
			resp.sendRedirect(req.getContextPath() + "/seatSelect.jsp?concertId=" + concertId);
			return;
		}

		int seatId = seatDao.selectSeatId(concertId, seatNumber);

		ReservationsVO reservation = new ReservationsVO();
		reservation.setUserId(loginUser.getId());
		reservation.setConcertId(concertId);
		reservation.setSeatId(seatId);
		reservation.setStatus("RESERVED");

		resDao.insertReservation(reservation);

		session.setAttribute("successMsg", "예약이 완료되었습니다.");
		resp.sendRedirect(req.getContextPath() + "/reserve/list");
	}
}
