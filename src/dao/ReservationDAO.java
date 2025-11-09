package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.mystudy.DB.DBService;

import VO.ReservationsVO;

public class ReservationDAO {
	private static SqlSessionFactory sqlSessionFactory = DBService.getFactory();

	// 예약 저장
	public static int insertReservation(ReservationsVO vo) {
		SqlSession ss = sqlSessionFactory.openSession(true);
		try {
			return ss.insert("concert.insertReservation", vo);
		} finally {
			ss.close();
		}
	}

	// 전체 예약 목록
	public List<ReservationsVO> getAllReservations() {
		SqlSession ss = sqlSessionFactory.openSession();
		try {
			return ss.selectList("concert.selectAllReservations");
		} finally {
			ss.close();
		}
	}

	// 사용자 ID로 예약 목록 조회
	public List<ReservationsVO> getReservationsByUserId(int userId) {
		SqlSession ss = sqlSessionFactory.openSession();
		try {
			return ss.selectList("reservations.selectReservationsByUserId", userId);
		} finally {
			ss.close();
		}
	}

	// 사용자 예약 + 상세정보
	public static List<ReservationsVO> getReservationsWithDetailsByUserId(int userId) {
		SqlSession ss = sqlSessionFactory.openSession();
		try {
			return ss.selectList("reservations.getReservationsWithDetailsByUserId", userId);
		} finally {
			ss.close();
		}
	}

	// 예약 ID로 예약 조회
	public ReservationsVO getReservationById(int reservationId) {
		SqlSession ss = sqlSessionFactory.openSession();
		try {
			return ss.selectOne("reservations.getReservationById", reservationId);
		} finally {
			ss.close();
		}
	}

	// 예약 취소 처리 (예약 상태 변경 또는 삭제)
	public void cancelReservation(int reservationId) {
		SqlSession ss = sqlSessionFactory.openSession();
		try {
			ss.update("reservations.cancelReservation", reservationId); // mapper에서 상태값을 "취소"로 변경하거나 삭제
			ss.commit();
		} finally {
			ss.close();
		}
	}

	public java.sql.Date getConcertDateByReservationId(int concertId) {
		SqlSession ss = sqlSessionFactory.openSession();
		try {
			return ss.selectOne("reservations.getConcertDateByReservationId", concertId);
		} finally {
			ss.close();
		}
	}

	// 리뷰 작성 안 한 예약 내역 조회
	public static List<ReservationsVO> getReservationsWithoutReview(int userId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			// 네임스페이스 'review'에 있는 selectWithoutReview 호출
			return session.selectList("review.selectWithoutReview", userId);
		}
	}

	// 예약 ID로 콘서트 ID 조회
	public static int getConcertIdByReservationId(int reservationId) {
		try (SqlSession ss = sqlSessionFactory.openSession()) {
			Integer concertId = ss.selectOne("reservations.selectConcertIdByReservationId", reservationId);
			return concertId != null ? concertId : -1;
		}
	}
	// ReservationDAO.java

	// 리뷰 작성 안 한, 공연 종료된 예약 내역 조회
	public static List<ReservationsVO> getPastReservationsWithoutReview(int userId) {
		try (SqlSession ss = sqlSessionFactory.openSession()) {
			return ss.selectList("reservations.selectPastReservationsWithoutReview", userId);
		}
	}
}
