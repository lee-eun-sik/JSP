package service.user;

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
}