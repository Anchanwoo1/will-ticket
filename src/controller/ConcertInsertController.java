package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.mystudy.DB.DBService;
import VO.ConcertVO;
import VO.SeatsVO;

@WebServlet("/concertInsert")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,     // 1MB
    maxFileSize       = 5 * 1024 * 1024, // 5MB
    maxRequestSize    = 10 * 1024 * 1024 // 10MB
)
public class ConcertInsertController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/concertInsertForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        Part imagePart = req.getPart("imageFile");
        if (imagePart == null || imagePart.getSize() == 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "이미지 파일이 필요합니다.");
            return;
        }

        String submitted = imagePart.getSubmittedFileName();
        int idx = Math.max(submitted.lastIndexOf('/'), submitted.lastIndexOf('\\'));
        String original = (idx >= 0) ? submitted.substring(idx + 1) : submitted;

        String uploadDir = getServletContext().getRealPath("/WebContent/img");
        File uploads = new File(uploadDir);
        if (!uploads.exists()) {
            uploads.mkdirs();
        }

        String savedFile = System.currentTimeMillis() + "_" + original;
        File dest = new File(uploads, savedFile);
        imagePart.write(dest.getAbsolutePath());

        String dbImagePath = "WebContent/img/" + savedFile;

        // ConcertVO 생성 및 세팅
        ConcertVO vo = new ConcertVO();
        vo.setTitle(req.getParameter("title"));
        vo.setCategory(req.getParameter("category"));
        vo.setLocation(req.getParameter("location"));
        Date concertDate = Date.valueOf(req.getParameter("concertDate"));
        vo.setConcertDate(concertDate);
        vo.setStartTime(req.getParameter("startTime"));
        vo.setImage(dbImagePath);
        vo.setInfo(req.getParameter("info"));
        vo.setCaster(req.getParameter("caster"));


        // 콘서트 + 좌석 생성 통합 처리
        SqlSessionFactory factory = DBService.getFactory();
        try (SqlSession sqlSession = factory.openSession(false)) { // 트랜잭션 직접 제어

            // 1. 콘서트 등록
            int result = sqlSession.insert("concert.insertConcert", vo);
            if (result > 0) {
                // selectKey 사용 시 concertId 자동 주입됨
                int concertId = vo.getConcertId();

                // 2. 좌석 10x10 생성 (row: 1~10, col: 1~10)
                for (int row = 1; row <= 10; row++) {
                    for (int col = 1; col <= 10; col++) {
                        SeatsVO seat = new SeatsVO();
                        seat.setConcertId(concertId);
                        seat.setSeatNumber(row + "-" + col);
                        seat.setSeatType(row <= 3 ? "V" : "N");
                        seat.setIsAvailable("Y");
                        seat.setPrice(row <= 3 ? 150000 : 100000); // 가격 예시

                        sqlSession.insert("seats.insertSeat", seat);
                    }
                }

                // 3. 모두 성공시 commit
                sqlSession.commit();
                req.getSession().setAttribute("successMsg", "콘서트 및 좌석 등록 완료");
                resp.sendRedirect(req.getContextPath() + "/index");
            } else {
                sqlSession.rollback();
                req.setAttribute("errorMsg", "콘서트 등록 실패");
                req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMsg", "예기치 못한 오류 발생: " + e.getMessage());
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
        }
    }
}
