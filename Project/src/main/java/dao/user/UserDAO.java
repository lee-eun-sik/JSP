package dao.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.user.User;
import util.MybatisUtil;


public class UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAO.class); // Logger 인스턴스 생성
    private SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory(); // Mybatis 세션 팩토리 추가

    /**
     * 사용자 회원가입
     * @param userId 사용자 ID
     * @param username 사용자 이름
     * @param password 비밀번호 (SHA-256 암호화 적용)
     * @param email 이메일
     * @return 성공 여부
     */
    public boolean registerUser(SqlSession session, User user) {
        try {
        	int result = session.insert("UserMapper.registerUser", user);
        	session.commit(); // 트랜잭션 커밋
        	return result >0;
        } catch (Exception e) {
        	session.rollback(); // 오류 발생 시 롤백
        	logger.error("Error in registerUser: ", e);
        	return false;
        }
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
    	return session.selectOne("UserMapper.getUserById", userId);
    }
    
    public boolean validateUser(SqlSession session, User user) {
        User storedUser = getUserById(session, user.getUserId());
        
        if (storedUser != null && storedUser.getPassword().equals(user.getPassword())) {
        	return true;
        }
        return false;
    }
    
    public boolean deleteUser(SqlSession session, String  userId) {
    	// 사용자 정보를 DB에서 검색
    	try {
    		int result = session.delete("UserMapper.deleteUser", userId);
    		session.commit();
    		return result >0;
    	} catch (Exception e) {
    		session.rollback();// 오류 발생 시 롤백
    		logger.error("Error in deleteUser: ", 0);
    		return false;
    	}
    }
    
    public boolean updateUser(User user) {
    	try (SqlSession session = sqlSessionFactory.openSession()) { // 세션 열기
    		int updateRows = session.update("UserMapper.updateUser", user);
    		session.commit(); // 트랜잭션 커밋
    		return updateRows > 0;
    	} catch (Exception e) {
    		logger.error("Error in updateUser: ", e);
    		return false;		
    	}
    }
    
    public boolean isUserIdDuplicate(SqlSession session, String userId) {
    	Integer count = session.selectOne("UserMapper.checkUserIdDuplicate", userId);
    	return count != null && count > 0;
    }
}