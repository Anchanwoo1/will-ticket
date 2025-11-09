package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import VO.ReservationsVO;
import VO.UserVO;
import com.mystudy.DB.DBService;

import org.apache.ibatis.session.SqlSession;

import dao.ReservationDAO;

@WebServlet("/review/write")
public class ReviewWritePageController extends HttpServlet {  // <-- 여기 수정!
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        // 1. 콘서트 ID 파라미터 받기 (선택 사항)
        String concertIdStr = req.getParameter("concertId");
        if (concertIdStr != null && !concertIdStr.trim().isEmpty()) {
            int concertId = 0;
            try {
                concertId = Integer.parseInt(concertIdStr);
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/error.jsp");
                return;
            }

            // 2. MyBatis 세션 열고 리뷰 존재 여부 체크
            try (SqlSession sqlSession = DBService.getFactory().openSession()) {
                Map<String, Object> params = new HashMap<>();
                params.put("userId", loginUser.getId());
                params.put("concertId", concertId);

                int count = sqlSession.selectOne("review.checkReviewExistsByUserAndConcert", params);

                if (count > 0) {
                    req.setAttribute("message", "이미 이 콘서트에 대한 리뷰를 작성하셨습니다.");
                    req.getRequestDispatcher("/Review/alreadyWritten.jsp").forward(req, resp);
                    return;
                }
            }
        }

        // 3. 리뷰 미작성 예약 목록 가져오기
        List<ReservationsVO> reserveList = ReservationDAO.getReservationsWithoutReview(loginUser.getId());
        req.setAttribute("reserveList", reserveList);

        // 4. 리뷰 작성 페이지로 포워딩
        RequestDispatcher dispatcher = req.getRequestDispatcher("/Review/writeReview.jsp");
        dispatcher.forward(req, resp);
    }
}
