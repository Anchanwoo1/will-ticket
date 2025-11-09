package controller;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import VO.ReviewVO;
import VO.UserVO;
import dao.ReviewDAO;
import dao.ReservationDAO;

@WebServlet("/writeReview")
public class WriteReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ReviewDAO reviewDao = new ReviewDAO();
	private final ReservationDAO reservationDAO = new ReservationDAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}

		try {
			UserVO loginUser = (UserVO) session.getAttribute("loginUser");
			int userId = loginUser.getId();

			int reservationId = Integer.parseInt(req.getParameter("reservationId"));
			int score = Integer.parseInt(req.getParameter("score"));
			String content = req.getParameter("content");

			// 예약에서 콘서트 ID 가져오기
			int concertId = ReservationDAO.getConcertIdByReservationId(reservationId);
			if (concertId == -1) {
				resp.sendRedirect(req.getContextPath() + "/error.jsp");
				return;
			}

			// 공연 종료 여부 체크
			Date concertDate = reservationDAO.getConcertDateByReservationId(concertId);
			Date today = new Date(System.currentTimeMillis());
			if (concertDate == null || !concertDate.before(today)) {
				resp.setContentType("text/html; charset=UTF-8");
				resp.getWriter().write("<script>alert('아직 공연이 종료되지 않아 리뷰를 작성할 수 없습니다.'); history.back();</script>");
				resp.getWriter().flush();
				return;
			}

			// 중복 리뷰 체크
			int count = reviewDao.checkReviewExistsByUserAndConcert(userId, concertId);
			if (count > 0) {
				resp.setContentType("text/html; charset=UTF-8");
				resp.getWriter().write("<script>alert('이미 이 콘서트에 대한 리뷰를 작성하셨습니다.'); history.back();</script>");
				resp.getWriter().flush();
				return;
			}

			ReviewVO vo = new ReviewVO();
			vo.setUserId(userId);
			vo.setReservationId(reservationId);
			vo.setScore(score);
			vo.setContent(content);

			reviewDao.insertReview(vo);

			resp.sendRedirect(req.getContextPath() + "/index");

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		}
	}
}
