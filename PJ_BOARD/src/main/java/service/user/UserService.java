package service.user;

import java.util.Date;

import model.user.User;

public interface UserService {
    /**
     * 사용자 회원가입
     */
    boolean registerUser(User user);
    
    boolean validateUser(User user);
    
    public User getUserById(String userId);
    
    public User deleteUser(String userId);

	
}
