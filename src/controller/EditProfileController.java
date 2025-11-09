package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import VO.UserVO;
import org.apache.ibatis.session.SqlSessionFactory;

import com.mystudy.DB.DBService;

@WebServlet("/editProfile")
public class EditProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public EditProfileController() {
        SqlSessionFactory factory = DBService.getFactory();
        this.userDAO = new UserDAO(factory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 세션 확인
        VO.UserVO user = (VO.UserVO) request.getSession().getAttribute("loginUser");
        if (user == null || user.getId() <= 0) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        request.getRequestDispatcher("/editProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        VO.UserVO user = (VO.UserVO) request.getSession().getAttribute("loginUser");
        if (user == null || user.getId() <= 0) {
            System.err.println("[EditProfileController] 세션에 로그인 사용자 정보가 없습니다.");
            request.setAttribute("msg", "로그인 정보가 없습니다. 다시 로그인하세요.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        int id = (idStr != null && !idStr.trim().isEmpty()) ? Integer.parseInt(idStr.trim()) : user.getId();

        try {
            if (id <= 0) {
                System.err.println("[EditProfileController] id 파라미터가 유효하지 않습니다.");
                request.setAttribute("msg", "잘못된 접근입니다. (필수값 누락)");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("[EditProfileController] id 파라미터 변환 오류: " + idStr);
            e.printStackTrace();
            request.setAttribute("msg", "잘못된 접근입니다. (값 오류)");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        String name = request.getParameter("name");
        String userId = request.getParameter("userId");
        String phone = request.getParameter("phone");
        String pw = request.getParameter("pw");

        UserVO vo = new UserVO();
        vo.setId(id);
        vo.setName(name);
        vo.setUserId(userId);
        vo.setPhone(phone);
        vo.setPw(pw);

        boolean result = userDAO.updateUser(vo);

        if (result) {
        	UserVO updatedUser = userDAO.findById(id);
            request.getSession().setAttribute("loginUser", updatedUser);
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            request.setAttribute("msg", "프로필 수정에 실패하였습니다.");
            response.sendRedirect(request.getContextPath() + "/editProfile");
        }
    }
}