package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.logging.Logger;
import VO.ReviewVO;
import VO.UserVO;
import dao.ReviewDAO;
import com.mystudy.DB.DBService;

@WebServlet("/reviewList")
public class ReviewListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ReviewDAO reviewDao = new ReviewDAO();
    private static final Logger logger = Logger.getLogger(ReviewListController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 로그인 여부 확인
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        int userId = loginUser.getId();   // PK(int) 사용

        logger.info("dddd");
        // 2. 리뷰 조회
        List<ReviewVO> reviewList = reviewDao.getReviewsByUser(userId);

        // 3. 디버깅용 콘솔 출력
        if (reviewList == null) {
            System.out.println("[reviewList] null");
        } else if (reviewList.isEmpty()) {
            System.out.println("[reviewList] size = 0");
        } else {
            System.out.println("[reviewList] size = " + reviewList.size());
        }

        // 4. JSP 전달 및 포워딩
        request.setAttribute("reviewList", reviewList);
        RequestDispatcher rd = request.getRequestDispatcher("/Review/reviewList.jsp");
        rd.forward(request, response);
    }
}
