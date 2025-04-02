package service.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.user.UserDAO;
import exception.HException;
import model.user.User;
import util.MybatisUtil;
import util.SHA256Util;

public class UserServiceImpl implements UserService {// 보안때문, 인터페이스 호출, 스프링때문에 생긴이유
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;
 
    private SqlSessionFactory sqlSessionFactory; // MyBatis SQL 세션 팩토리
    
    /**
     * UserServiceImpl 생성자
     */
    public UserServiceImpl() {
        this.userDAO = new UserDAO();
        try {
            sqlSessionFactory = MybatisUtil.getSqlSessionFactory(); // SQL 세션 팩토리 초기화
        } catch (Exception e) {
            logger.error("Mybatis 오류", e); // 오류 발생 시 로그 출력
        }
    }

    /**
     * 사용자 회원가입 서비스
     * @param userId 사용자 ID
     * @param username 사용자 이름
     * @param password 비밀번호 (SHA-256 암호화 적용)
     * @param email 이메일
     * @return 성공 여부
     */
    @Override
    public boolean registerUser(User user) {
    	try (SqlSession session = sqlSessionFactory.openSession()){// 암호화 시키기
    		String password = user.getPassword();
    		String encryptedpPassword = password != null ? SHA256Util.encrypt(password) : null;
    		user.setPassword(encryptedpPassword);
    		
    		// DAO를 통해 회원가입 진행
            boolean result = userDAO.registerUser(session, user);
            if (result) session.commit(); // 트랜잭션 커밋,통신 채널 넣음, 넘겨줌
            return result;
    	} catch (Exception e) {
    		logger.error("Error in registerUser: ", e);
    		return false; //DB원상 복구, 하나의 통신 이어줌. 통신이 끊어지면 
		}
    }

	@Override
	public boolean validateUser(User user) throws HException {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			User selectUser = userDAO.getUserById(session, user.getUserId());
			if (selectUser == null) return false;
			
			String password = user.getPassword();
			String encryptedPassword = password != null ? SHA256Util.encrypt(password) : null;
			
			return encryptedPassword.equals(selectUser.getPassword());
		}
	}
	
	 public User getUserById(String userId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return userDAO.getUserById(session, userId);
		}
	 }

	@Override
	public boolean updateUser(User user) {
		return userDAO.updateUser(user);
	}

	@Override
	public boolean deleteUser(String userId) {
		try (SqlSession session = MybatisUtil.getSqlSessionFactory().openSession()) {
			boolean result = userDAO.deleteUser(session, userId);
			if (result) {
				session.commit(); // 회원 탈퇴 성공 시 커밋 추가
			} else {
				session.rollback();
			}
			return result;
		} catch (Exception e) {
			logger.error("Error in deleteUser: ", e);
			return false;
		}
	}
	 

}