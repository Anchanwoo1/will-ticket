package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.ibatis.session.SqlSessionFactory;
import com.mystudy.DB.DBService;
import dao.UserDAO;
import VO.UserVO;

@WebServlet("/findPwd")
public class FindPwdController extends HttpServlet {
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
        req.getRequestDispatcher("/findPwdForm.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String userId = req.getParameter("userId");
        String phone  = req.getParameter("phone");

        UserVO param = new UserVO();
        param.setUserId(userId);
        param.setPhone(phone);

        String foundPw = null;
        String errorMsg = null;

        try {
            foundPw = userDao.findPassword(param);
        } catch (Exception e) {
            errorMsg = "비밀번호 찾기 중 오류가 발생했습니다.";
            System.err.println("비밀번호 찾기 오류: " + e.getMessage());
            e.printStackTrace();
        }

        req.setAttribute("foundPw", foundPw);
        req.setAttribute("errorMsg", errorMsg);
        req.getRequestDispatcher("/findPwdResult.jsp").forward(req, resp);
    }
}
