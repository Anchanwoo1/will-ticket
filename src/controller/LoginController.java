package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import dao.UserDAO;
import VO.UserVO;
import com.mystudy.DB.*;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String pw = request.getParameter("pw");
        HttpSession session = request.getSession();
        SqlSessionFactory sqlSessionFactory = DBService.getFactory();

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // ★ XML 매퍼 방식 호출
            UserVO userVO = new UserVO();
            userVO.setUserId(userId);
            userVO.setPw(pw);

            // "members.login" = <mapper namespace="members"> + <select id="login">
            UserVO user = sqlSession.selectOne("members.login", userVO);

            if (user != null) {
                session.setAttribute("loginUser", user);
                request.setAttribute("loginSuccess", true);
                request.setAttribute("userId", user.getUserId());
            } else {
                session.removeAttribute("loginUser"); // 실패시 세션 정리
				request.setAttribute("loginSuccess", false);
			}
            RequestDispatcher rd = request.getRequestDispatcher("/loginResult.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("loginSuccess", false);
            RequestDispatcher rd = request.getRequestDispatcher("/loginResult.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
    }
}