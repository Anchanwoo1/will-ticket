package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mystudy.DB.DBService;

import VO.SeatsVO;
import dao.SeatDAO;

@WebServlet("/concert/seatSelect")
public class SeatSelectController extends HttpServlet {
    private SeatDAO seatDao = new SeatDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int concertId = Integer.parseInt(req.getParameter("concertId"));
        
        List<SeatsVO> seats = seatDao.selectByConcertId(concertId);
        
        //추가
        String title = seatDao.selectTitleByConcertId(concertId);

        System.out.println(seats.toString());
        
        req.setAttribute("seatList", seats);
        
        //수정
        req.setAttribute("title", title);

        req.getRequestDispatcher("/concert/seatSelect.jsp")
           .forward(req, resp);
    }
}