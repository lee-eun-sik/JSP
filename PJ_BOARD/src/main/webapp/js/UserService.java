package service.user;

import model.user.User;

public interface UserService {
    /**
     * 사용자 회원가입
     */
    boolean registerUser(User user);

}
