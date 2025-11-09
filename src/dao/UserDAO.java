package dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import VO.UserVO;

public class UserDAO {
	private final SqlSessionFactory sqlSessionFactory;

	public UserDAO(SqlSessionFactory factory) {
		this.sqlSessionFactory = factory;
	}

	/** 회원가입 */
	public boolean join(UserVO user) {
		try (SqlSession session = sqlSessionFactory.openSession(true)) {
			return session.insert("members.join", user) > 0;
		}
	}

	/** 전체 사용자 목록 조회 */
	public List<UserVO> list() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.selectList("members.list");
		}
	}

	/** 로그인 */
	public UserVO login(UserVO credentials) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.selectOne("members.login", credentials);
		}
	}

	/** ID로 사용자 조회 */
	public UserVO findById(int id) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.selectOne("members.findById", id);
		}
	}

	/** 사용자 정보 수정 */
	public boolean updateUser(UserVO user) {
		try (SqlSession session = sqlSessionFactory.openSession(true)) {
			return session.update("members.updateUser", user) > 0;
		}
	}

	/** 이름 + 전화번호로 아이디 찾기 */
	public String findUserId(UserVO param) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.selectOne("members.findUserId", param);
		}
	}

	/** 아이디 + 전화번호로 비밀번호 찾기 */
	public String findPassword(UserVO param) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.selectOne("members.findPassword", param);
		}
	}

	/** ID(PK)로 사용자 조회 (예: 예약 취소 검증용) */
	public UserVO getUserById(int id) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.selectOne("members.getUserById", id); // "user." → "members."로 일치시킴
		}
	}

	/** 사용자 아이디(String)로 회원 정보 조회 - 미구현 */
	public UserVO selectUserByUserId(String userId) {
		// 필요 시 구현
		return null;
	}
}