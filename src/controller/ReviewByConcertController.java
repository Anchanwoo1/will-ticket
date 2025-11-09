package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import VO.ReviewVO;
import dao.ReviewDAO;
import com.mystudy.DB.DBService;

@WebServlet("/concertReviewList")
public class ReviewByConcertController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // DAO 인스턴스 생성
    private final ReviewDAO reviewDao = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 콘서트 ID 파라미터 추출
        String concertIdStr = req.getParameter("concertId");

        // 2. 리뷰 데이터 담을 변수 선언
        // 콘서트 ID가 있으면 리스트, 없으면 Map (공연 제목 기준 그룹핑) 으로 구분
        List<ReviewVO> reviewList = null;
        Map<String, List<ReviewVO>> groupedReviews = null;

        if (concertIdStr == null || concertIdStr.trim().isEmpty()) {
            // concertId가 없으면 전체 리뷰를 공연 제목별로 그룹핑해서 Map으로 조회
            groupedReviews = reviewDao.getReviewsGroupedByConcertTitle();

            // JSP에 Map 전달
            req.setAttribute("groupedReviews", groupedReviews);

            // 포워딩할 JSP 경로(전체 리뷰 그룹핑용 JSP)
            RequestDispatcher rd = req.getRequestDispatcher("/Review/concertReviewList.jsp");
            rd.forward(req, resp);
            return;  // 여기서 종료
        } else {
            try {
                int concertId = Integer.parseInt(concertIdStr);

                // 해당 콘서트의 리뷰 목록 가져오기
                reviewList = reviewDao.getReviewsByConcert(concertId);

            } catch (NumberFormatException e) {
                // concertId가 숫자가 아닐 경우 예외 처리
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/error.jsp");
                return;
            }
        }

        // 3. JSP로 단일 리스트 전달
        req.setAttribute("reviewList", reviewList);

        // 4. 단일 콘서트 리뷰 리스트 JSP 포워딩
        RequestDispatcher rd = req.getRequestDispatcher("/Review/concertReviewList.jsp");
        rd.forward(req, resp);
    }
}
