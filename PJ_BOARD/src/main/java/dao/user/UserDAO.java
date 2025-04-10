package dao.user;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.user.User;
import util.MybatisUtil;


public class UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAO.class); // Logger 인스턴스 생성
   

    /**
     * 사용자 회원가입
     * @param userId 사용자 ID
     * @param username 사용자 이름
     * @param password 비밀번호 (SHA-256 암호화 적용)
     * @param email 이메일
     * @return 성공 여부
     */
    public boolean registerUser(SqlSession session, User user) {
        int result = session.insert("UserMapper.registerUser", user); // 사용자 등록 쿼리 실행
        return result > 0; // 삽입 성공 여부 반환
    }
    /**
     * 사용자 로그인 검증
     * @param session
     * @param userId 사용자 ID
     * @param password 입력된 비밀번호 (SHA-256 암호화 후 비교)
     * @return
     */
    public User getUserById(SqlSession session, String  userId) {
    	// 사용자 정보를 DB에서 검색
    	User user = session.selectOne("UserMapper.getUserById", userId);
    	return user;
    }
    
    public boolean validateUser(SqlSession session, User user) {
        int result = session.insert("UserMapper.validateUser", user); // 사용자 등록 쿼리 실행
        return result > 0; // 삽입 성공 여부 반환
    }
    
    public User deleteUser(SqlSession session, String  userId) {
    	// 사용자 정보를 DB에서 검색
    	User user = session.selectOne("UserMapper.deleteUser", userId);
    	return user;
    }
    
    public String getUserRole(SqlSession session, String userId) {
        return session.selectOne("UserMapper.getUserRoleById", userId);
    }
    
    public User findUserByInfo(SqlSession session, String name, String phone, Date birthdate) {
        Map<String, Object> paramMap = new HashMap<>();
       
        paramMap.put("name", name);
        paramMap.put("phone", phone);
        paramMap.put("birthdate", birthdate);
        return session.selectOne("UserMapper.findUserByInfo", paramMap);
    }
    
    public User findUserByCredentials(SqlSession session, String name, String userId, String phone, Date birthdate) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("userId", userId);
        params.put("phone", phone);
        params.put("birthdate", birthdate);
        return session.selectOne("UserMapper.findUserByCredentials", params);
    }
}
