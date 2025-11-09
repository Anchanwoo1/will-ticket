package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSessionFactory;
import com.mystudy.DB.DBService;
import dao.UserDAO;
import VO.UserVO;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
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
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String userId = req.getParameter("userId");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        UserVO user = new UserVO();
        user.setName(name);
        user.setUserId(userId);
        user.setPhone(phone);
        user.setPw(password);

        boolean success = false;
        String errorMsg = null;
        try {
            success = userDao.join(user);
        } catch (PersistenceException e) {
            // 중복 키 등 제약 조건 위반
            errorMsg = "이미 사용 중인 아이디입니다.";
        } catch (Exception e) {
            errorMsg = "서버 오류로 회원가입에 실패했습니다.";
            e.printStackTrace();
        }

        req.setAttribute("success", success);
        req.setAttribute("errorMsg", errorMsg);
        req.getRequestDispatcher("/registerResult.jsp").forward(req, resp);
    }
}