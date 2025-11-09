package dao;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import VO.ConcertVO;
import com.mystudy.DB.DBService;

public class ConcertDAO {

   private SqlSessionFactory factory = DBService.getFactory();

   // 제목 + 카테고리로 검색
   public List<ConcertVO> getConcertsByTitleAndCategory(String title, String category) {
      SqlSession session = factory.openSession();
      List<ConcertVO> list = null;
      try {
         Map<String, String> param = new HashMap<>();
         param.put("title", title);
         param.put("category", category);
         list = session.selectList("concert.selectByTitleAndCategory", param);
      } finally {
         session.close();
      }
      return list;
   }

   // 전체 콘서트 조회
   public List<ConcertVO> getAllConcerts() {
      SqlSession session = factory.openSession();
      List<ConcertVO> list = null;
      try {
         System.out.println("전체 콘서트 불러오기 시도 중...");
         list = session.selectList("concert.list");
         System.out.println("불러온 콘서트 개수: " + list.size());
      } finally {
         session.close();
      }
      return list;
   }

   // concertId로 일정 가져오기
   public List<ConcertVO> getScheduleByConcertId(int concertId) {
      SqlSession session = factory.openSession();
      List<ConcertVO> list = null;
      try {
         list = session.selectList("concert.getScheduleByConcertId", concertId);
      } finally {
         session.close();
      }
      return list;
   }

   // 제목, 카테고리 중복 제거 목록
   public List<ConcertVO> listDistinctTitleCategory() {
      SqlSession session = factory.openSession();
      try {
         return session.selectList("concert.listDistinctTitleCategory");
      } finally {
         session.close();
      }
   }

   // concertId로 단일 콘서트 가져오기
   public ConcertVO selectById(int concertId) {
      SqlSession session = factory.openSession();
      try {
         return session.selectOne("concert.selectById", concertId);
      } finally {
         session.close();
      }
   }

   // 콘서트 등록
   public int insertConcert(ConcertVO vo) {
      try (SqlSession session = factory.openSession(true)) {
         return session.insert("concert.insertConcert", vo);
      }
   }

   public ConcertVO getConcertById(int concertId) {
      try (SqlSession session = factory.openSession()) {
         return session.selectOne("reservations.getConcertById", concertId);
      }
   }

   public List<ConcertVO> searchConcertsByTitle(String title) {
      SqlSession session = factory.openSession();
      List<ConcertVO> list = null;
      try {
         if (title == null || title.length() < 2) {
            return new ArrayList<>(); // 2글자 미만이면 빈 리스트 반환
         }
         list = session.selectList("concert.searchByTitle", title);
      } finally {
         session.close();
      }
      return list;
   }

   public List<ConcertVO> listByCategory(String category) {
      try (SqlSession ss = DBService.getFactory().openSession()) {
         return ss.selectList("selectByCategory", category);
      }
   }
}
