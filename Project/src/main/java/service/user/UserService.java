package service.user;

import model.user.User;

public interface UserService {
    /**
     * 사용자 회원가입
     */
    boolean registerUser(User user);
    
    /**
     * 사용자 로그인 검증
     */
    
    boolean validateUser(User user);
    
    /**
     * 사용자 정보 가져오기
     */
    
    User getUserById(String userId);
    //회원정보 수정
   
    /**
     * 회원 정보 수정
     */
    boolean updateUser(User user);  // 추가
}