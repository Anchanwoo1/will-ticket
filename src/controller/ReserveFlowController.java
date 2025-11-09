package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import VO.ConcertVO;
import VO.ReviewVO;
import dao.ConcertDAO;
import dao.ReviewDAO;

@WebServlet("/reserveFlow")
public class ReserveFlowController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String category = request.getParameter("category");

        System.out.println("Request - title: " + title + ", category: " + category);

        // 파라미터 맵 생성 (추후 필요시 사용)
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("title", title);
        paramMap.put("category", category);

        // 콘서트 목록 조회 (title+category가 있으면 해당 콘서트만, 없으면 전체)
        List<ConcertVO> concertList;
        ConcertDAO dao = new ConcertDAO();
        if (title != null && category != null && !title.isEmpty() && !category.isEmpty()) {
            concertList = dao.getConcertsByTitleAndCategory(title, category);
            System.out.println("getConcertsByTitleAndCategory() 결과 개수: " + (concertList != null ? concertList.size() : 0));
        } else {
            concertList = dao.getAllConcerts();
            System.out.println("getAllConcerts() 결과 개수: " + (concertList != null ? concertList.size() : 0));
        }

        // 리뷰 리스트 조회
        ReviewDAO reviewDao = new ReviewDAO();
        List<ReviewVO> reviewList = reviewDao.getReviewsByTitleAndCategory(title, category);
        request.setAttribute("reviewList", reviewList);
        System.out.println("getAllreviewList : " + reviewList.size());

        if (concertList == null) {
            concertList = new ArrayList<>();
            System.out.println("concertList is null, initialized as empty list");
        }
        request.setAttribute("concertList", concertList);

        // 대표 공연 1건만 별도로 concert로 전달 (info, caster 등 단일값 출력용)
        ConcertVO concert = !concertList.isEmpty() ? concertList.get(0) : null;
        request.setAttribute("concert", concert);

        if (concert != null) {
            System.out.println("concert.info = " + concert.getInfo());
            System.out.println("concert.caster = " + concert.getCaster());
        }

        System.out.println("Forwarding to: /reserveFlow.jsp");
        request.getRequestDispatcher("Time/reserveFlow.jsp").forward(request, response);
    }
}