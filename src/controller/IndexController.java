package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import com.mystudy.DB.*;

import VO.ConcertVO;
import VO.UserVO;

@WebServlet("/index")
public class IndexController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 사용자 정보 읽기
		HttpSession session = req.getSession();
		UserVO user = (UserVO) session.getAttribute("loginUser");
		req.setAttribute("user", user);

		// 최신 4개 공연 조회
		try (SqlSession ss = DBService.getFactory().openSession()) {
			List<ConcertVO> hotList = ss.selectList("concert.listLatest");
			req.setAttribute("hotList", hotList);

			List<ConcertVO> bannerList = ss.selectList("concert.listLatest");
			req.setAttribute("bannerList", bannerList);

			List<Map<String, Object>> groupRateList = ss.selectList("seats.selectSeatsAvg");
			// JSP로 전달
			req.setAttribute("groupRateList", groupRateList);

			// 콘솔에 전체 출력
			for (Map<String, Object> map : groupRateList) {
				// 컬럼명은 쿼리 AS와 반드시 일치, 대부분 대문자
				String title = (String) map.get("TITLE");
				String category = (String) map.get("CATEGORY");
				Object reservationRateObj = map.get("RESERVATION_RATE");
				double reservationRate = reservationRateObj != null ? ((Number) reservationRateObj).doubleValue() : 0.0;

				System.out.println(
						"공연명: " + title + ", 카테고리: " + category + ", 예매율: " + (reservationRate) + "%");
			}
		}

		// JSP로 포워딩
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
}