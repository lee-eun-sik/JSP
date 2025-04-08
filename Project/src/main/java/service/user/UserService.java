package service.user;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import exception.HException;
import model.user.User;

public interface UserService {
    /**
     * 사용자 회원가입
     */
    boolean registerUser(User user);
    
    /**
     * 사용자 로그인 검증
     * @throws HException 
     */
    
    boolean validateUser(User user) throws HException;
    
    /**
     * 사용자 정보 가져오기
     */
    
    User getUserById(String userId);
    //회원정보 수정
   
    /**
     * 회원 정보 수정
     */
    boolean updateUser(User user);  // 수정
    
    boolean deleteUser(String userId); //삭제
    
    boolean isUserIdDuplicate(String userId); //아이디 중복체크
    
    boolean changePassword(String userId, String currentPassword, String newPassword); // 비밀번호 변경
    
    List<User> getAllUsers(); // 이게 있어야 합니다!
    
    // UserService.java
    String findUserId(String name, String phone, String email, java.util.Date birthdate);
    
 // 비밀번호 초기화를 위한 사용자 정보 확인
    User findUserForPasswordReset(User user);
    
    public User findUserIdByInfo(String name, String phone, String email, Date birthdate);
}