package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.UserDAO;
import VO.UserVO;
import org.apache.ibatis.session.SqlSessionFactory;
import com.mystudy.DB.DBService;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao;

    @Override
    public void init() {
        SqlSessionFactory factory = DBService.getFactory();
        this.userDao = new UserDAO(factory);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        UserVO freshUser = userDao.findById(loginUser.getId());
        if (freshUser == null) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp"); // 사용자 데이터 없음 처리
            return;
        }
        req.setAttribute("user", freshUser);
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}