package service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private static final String NAMESPACE = "UserMapper";
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
	 
	@Override 
	public boolean isUserIdDuplicate(String userId) {
	    try (SqlSession session = sqlSessionFactory.openSession()) {
	        return userDAO.isUserIdDuplicate(session, userId); // 메서드명 일치
	    } catch (Exception e) {
	        logger.error("Error in isUserIdDuplicate: ", e);
	        return false;
	    }
	}
	@Override
	public boolean changePassword(String userId, String currentPassword, String newPassword) {
	    try (SqlSession session = sqlSessionFactory.openSession()) {
	        User user = userDAO.getUserById(session, userId);
	        
	        if (user == null) {
	            return false; // 사용자가 존재하지 않음
	        }

	        // 현재 비밀번호 암호화 후 비교
	        String encryptedCurrentPassword = SHA256Util.encrypt(currentPassword);
	        if (encryptedCurrentPassword.equals(user.getPassword())) {  // ✅ 올바른 String 비교 방법
	            System.out.println("비밀번호 일치");
	        } else {
	            System.out.println("현재 비밀번호가 일치하지 않습니다.");
	        }
	        System.out.println("입력된 비밀번호(암호화됨): " + encryptedCurrentPassword);
	        System.out.println("DB 저장 비밀번호: " + user.getPassword());
	        // 새로운 비밀번호 암호화 후 저장
	        String encryptedNewPassword = SHA256Util.encrypt(newPassword);
	        boolean result = userDAO.updatePassword(session, userId, encryptedNewPassword);

	        if (result) {
	            session.commit(); // 변경 성공 시 커밋
	        } else {
	            session.rollback(); // 변경 실패 시 롤백
	        }
	        return result;
	    } catch (Exception e) {
	        logger.error("Error in changePassword: ", e);
	        return false;
	    }
	    
	    
	}

	@Override
	public List<User> getAllUsers() {
	    try (SqlSession session = sqlSessionFactory.openSession()) {
	        return userDAO.getAllUsers(session);
	    } catch (Exception e) {
	        logger.error("Error in getAllUsers: ", e);
	        return new ArrayList<>();
	    }
	}
	
	@Override
	public User findUserByInfo(String name, String phone, String email, Date birthDate) {
	    try (SqlSession session = sqlSessionFactory.openSession()) {
	        return userDAO.findUserByInfo(session, name, phone, email, birthDate);
	    } catch (Exception e) {
	        logger.error("Error in findUserByInfo: ", e);
	        return null;
	    }
	}
}