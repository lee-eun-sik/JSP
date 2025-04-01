package service.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.user.UserDAO;
import model.user.User;
import util.MybatisUtil;
import util.SHA256Util;

public class UserServiceImpl implements UserService {// 보안때문, 인터페이스 호출, 스프링때문에 생긴이유
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;
    private static Map<String, User> userDB = new HashMap<>();
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
    	SqlSession session = sqlSessionFactory.openSession();
    	boolean result = false; 
    	
    	try {// 암호화 시키기
    		String password = user.getPassword();
    		String encryptedpPassword = password != null ? SHA256Util.encrypt(password) : null;
    		user.setPassword(encryptedpPassword);
    		
    		// DAO를 통해 회원가입 진행
            result = userDAO.registerUser(session, user);
            session.commit(); // 트랜잭션 커밋,통신 채널 넣음, 넘겨줌
    	} catch (Exception e) {
    		e.printStackTrace();
    		session.rollback(); //DB원상 복구, 하나의 통신 이어줌. 통신이 끊어지면 
		}
        return result;
    }

	@Override
	public boolean validateUser(User user) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		boolean result = false;
		try {
			User selectUser = userDAO.getUserById(session, user.getUserId());
			// 사용자 정보가 없으면 false 반환
			if(selectUser == null) {
				return false; // 사용자 ID가 존재하지 않을 경우
			}
			String password = user.getPassword();
    		String encryptedpPassword = password != null ? SHA256Util.encrypt(password) : null;
			
    		// 입력된 비밀번호와 DB에 저장된 비밀번호 비교
			result =  encryptedpPassword.equals(selectUser.getPassword()); // 비밀번호 비교
			
			session.commit(); // 트랜잭션 커밋
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		}
		return result;
	}
	
	 public User getUserById(String userId) {
		 SqlSession session = sqlSessionFactory.openSession();
		 User selectUser = userDAO.getUserById(session, userId); // 사용자 
		 return selectUser;
	 }

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		boolean result = false;
		
		try {
			// 기존 사용자 정보 확인
			User existingUser = userDAO.getUserById(session, user.getUserId());
			if (existingUser == null) {
				return false; // 사용자가 존재하지 않음
			}
			
			//비밀번호 암호화
			if (user.getPassword() != null && !user.getPassword().isEmpty()) {
				String encryptedPassword = SHA256Util.encrypt(user.getPassword());
				user.setPassword(encryptedPassword);
			} else {
				user.setPassword(existingUser.getPassword()); // SQL에서 기존 값 유지
			}
			
			// DAO를 통해 사용자 정보 업데이트
			result = userDAO.updateUser(session, user);
			
			if (result) {
				session.commit(); // 성공 시 커밋
				User updatedUser = userDAO.getUserById(session, user.getUserId()); // userDB에서도 업데이트
				userDB.put(user.getUserId(), updateUser); //캐시 업데이트
			} else {
				session.rollback(); // 실패시 롤백
			}
		} catch (Exception e) {
			logger.error("Error updating user: ", e);
			session.rollback(); // 예외 발생 시 롤백
		} finally {
			session.close(); // 세션 닫기
		}
		return result;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}

	
	 

}