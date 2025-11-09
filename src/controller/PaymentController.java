package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/pay/process")
public class PaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 예시: 결제 페이지에서 결제 요청 POST 처리
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 결제 관련 파라미터 받기
        String concertId = req.getParameter("concertId");
        String selectedSeats = req.getParameter("selectedSeats");
        String paymentMethod = req.getParameter("paymentMethod");
        String title = req.getParameter("title");

        // TODO: 결제 로직 처리 (결제 API 연동 등)

        // 결제 성공 시 결제 성공 서블릿으로 포워딩 or 리다이렉트
        req.setAttribute("concertId", concertId);
        req.setAttribute("selectedSeats", selectedSeats);
        req.setAttribute("paymentMethod", paymentMethod);
        req.setAttribute("title", title);

        // 여기서는 결제 성공 JSP로 포워딩 (보통은 리다이렉트도 많이 사용)
        req.getRequestDispatcher("/pay/paymentSuccess.jsp").forward(req, resp);
        
        
        
    }
}
