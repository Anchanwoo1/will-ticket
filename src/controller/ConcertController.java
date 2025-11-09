package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import VO.ConcertVO;
import dao.ConcertDAO;

@WebServlet("/concertsview")
public class ConcertController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ConcertDAO concertDao = new ConcertDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");

        List<ConcertVO> list;

        if (category != null && !category.isEmpty()) {
            // 카테고리별 공연 목록 조회
            list = concertDao.listByCategory(category);
        } else {
            // 전체 공연 목록 조회
            list = concertDao.listDistinctTitleCategory();
        }

        request.setAttribute("list", list);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Concert.jsp");
        dispatcher.forward(request, response);
    }
}
