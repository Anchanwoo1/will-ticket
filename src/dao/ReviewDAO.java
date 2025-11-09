package dao;

import java.util.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.mystudy.DB.DBService;

import VO.ReviewVO;

public class ReviewDAO {
   private SqlSessionFactory factory = DBService.getFactory();

   public ReviewDAO() {
      this.factory = DBService.getFactory();
   }

   // 리뷰 등록
   public void insertReview(ReviewVO vo) {
      try (SqlSession session = factory.openSession(true)) {
         session.insert("review.insertReview", vo);
      }
   }

   // 전체 리뷰 조회
   public List<ReviewVO> getAllReviews() {
      try (SqlSession session = factory.openSession()) {
         return session.selectList("review.selectAllReviews");
      }
   }

   // 특정 유저가 작성한 리뷰 조회
   public List<ReviewVO> getReviewsByUser(int userId) {
      try (SqlSession session = factory.openSession()) {
         return session.selectList("review.selectReviewsByUser", userId);
      }
   }

   // 콘서트 ID로 전체 리뷰 조회
   public List<ReviewVO> getReviewsByConcert(int concertId) {
      try (SqlSession session = factory.openSession()) {
         return session.selectList("review.selectReviewsByConcert", concertId);
      }
   }

   /**
    * 공연 제목(concertTitle) 기준으로 리뷰들을 그룹핑해서 Map으로 반환 key = concertTitle, value = 그 공연
    * 리뷰 리스트
    */
   public Map<String, List<ReviewVO>> getReviewsGroupedByConcertTitle() {
      List<ReviewVO> allReviews = getAllReviews();

      Map<String, List<ReviewVO>> groupedMap = new LinkedHashMap<>();

      for (ReviewVO review : allReviews) {
         String title = review.getConcertTitle();
         if (title == null)
            title = "제목 없음";

         groupedMap.computeIfAbsent(title, k -> new ArrayList<>()).add(review);
      }

      return groupedMap;
   }

   // 중복 리뷰 체크 : userId + concertId 기준
   public int checkReviewExistsByUserAndConcert(int userId, int concertId) {
      try (SqlSession session = factory.openSession()) {
         Map<String, Object> params = new HashMap<>();
         params.put("userId", userId);
         params.put("concertId", concertId);
         return session.selectOne("review.checkReviewExistsByUserAndConcert", params);
      }
   }

   // 추가
   public List<ReviewVO> getReviewsByTitleAndCategory(String title, String category) {
      Map<String, String> param = new HashMap<>();
      param.put("title", title);
      param.put("category", category);

      try (SqlSession session = factory.openSession()) {
         return session.selectList("review.selectReviewsByTitleAndCategory", param);
      }
   }

}
