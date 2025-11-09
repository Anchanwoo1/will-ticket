package dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import VO.ConcertVO;
import VO.SeatsVO;
import com.mystudy.DB.DBService;

public class SeatDAO {
    private final SqlSessionFactory factory = DBService.getFactory();

    /** 공연ID로 전체 좌석 목록 조회 */
    public List<SeatsVO> selectByConcertId(int concertId) {
        try (SqlSession session = factory.openSession()) {
            return session.selectList("seats.selectByConcertId", concertId);
        }
    }

    /** 공연ID + 좌석번호로 좌석ID 조회 */
    public int selectSeatId(int concertId, String seatNumber) {
        try (SqlSession session = factory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("concertId", concertId);
            params.put("seatNumber", seatNumber);
            return session.selectOne("seats.selectSeatId", params);
        }
    }

    /** 공연ID + 좌석번호로 좌석 객체 조회 */
    public SeatsVO selectByConcertIdAndSeatNumber(int concertId, String seatNumber) {
        try (SqlSession session = factory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("concertId", concertId);
            params.put("seatNumber", seatNumber);
            return session.selectOne("seats.selectByConcertIdAndSeatNumber", params);
        }
    }

    /** 좌석 등록 */
    public int insertSeat(SeatsVO seat) {
        try (SqlSession session = factory.openSession()) {
            int result = session.insert("seats.insertSeat", seat);
            session.commit();
            return result;
        }
    }

    /** 좌석 예약 상태만 업데이트 (Y/N) */
    public void updateSeatAvailability(int seatId, String isAvailable) {
        try (SqlSession session = factory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("seatId", seatId);
            params.put("isAvailable", isAvailable);
            session.update("seats.updateSeatAvailability", params);
            session.commit();
        }
    }

    /** 좌석 상태 및 사용자ID 동시 업데이트 */
    public void updateSeatAvailabilityAndUser(int seatId, String isAvailable, int userId) {
        try (SqlSession session = factory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("seatId", seatId);
            params.put("isAvailable", isAvailable);
            params.put("userId", userId);
            session.update("seats.updateAvailabilityAndUser", params);
            session.commit();
        }
    }

    /** 좌석 예약 가능 여부 조회 (boolean) */
    public boolean isSeatAvailable(int seatId) {
        try (SqlSession session = factory.openSession()) {
            String isAvailable = session.selectOne("seats.selectAvailabilityById", seatId);
            return "Y".equalsIgnoreCase(isAvailable);
        }
    }

    /** 좌석 예약 가능 여부 조회 (문자열 반환) */
    public String selectAvailabilityById(int seatId) {
        try (SqlSession session = factory.openSession()) {
            return session.selectOne("seats.selectAvailabilityById", seatId);
        }
    }

    /** 공연ID + 좌석번호로 좌석 가격 조회 */
    public int selectPriceBySeatNumber(int concertId, String seatNumber) {
        try (SqlSession session = factory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("concertId", concertId);
            params.put("seatNumber", seatNumber);
            Integer price = session.selectOne("seats.selectPriceBySeatNumber", params);
            return price != null ? price : 0;
        }
    }

    /** 사용자의 해당 공연 예매 수 조회 */
    public int countReservationsByUser(int userId, int concertId) {
        try (SqlSession session = factory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("concertId", concertId);
            return session.selectOne("reservations.countUserReservations", params);
        }
    }
    // tittle 가져오기
    public String selectTitleByConcertId(int concertId) {
        try (SqlSession session = factory.openSession()) {
            return session.selectOne("seats.getConcertTitleById", concertId);
        }
    }

    }
