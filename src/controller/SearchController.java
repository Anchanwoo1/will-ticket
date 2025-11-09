package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import VO.ConcertVO;
import dao.ConcertDAO;

@WebServlet("/search")
public class SearchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        if (query == null || query.trim().isEmpty()) {
            req.setAttribute("results", new ArrayList<>());
        } else {
            ConcertDAO dao = new ConcertDAO();
            List<ConcertVO> results = dao.searchConcertsByTitle(query);
            req.setAttribute("results", results);
        }
        req.getRequestDispatcher("/searchResult.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}