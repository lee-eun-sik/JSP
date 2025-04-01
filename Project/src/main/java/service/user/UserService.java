package service.user;

import model.user.User;

public interface UserService {
    /**
     * 사용자 회원가입
     */
    boolean registerUser(User user);
    
    boolean validateUser(User user);
    
    User getUserById(String userId);
    //회원정보 수정
   
    //회원정보 탈퇴
    boolean deleteUser(String userId);

	boolean updateUser(User user);
}