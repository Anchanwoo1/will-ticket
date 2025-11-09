package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.apache.ibatis.session.SqlSessionFactory;
import com.mystudy.DB.DBService;
import dao.UserDAO;
import VO.UserVO;

@WebServlet("/findId")
public class FindIdController extends HttpServlet {
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
        req.getRequestDispatcher("/findIdForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        UserVO criteria = new UserVO();
        criteria.setName(req.getParameter("name"));
        criteria.setPhone(req.getParameter("phone"));
        String foundId = userDao.findUserId(criteria);
        req.setAttribute("foundId", foundId);
        req.getRequestDispatcher("/findIdResult.jsp").forward(req, resp);
    }
}