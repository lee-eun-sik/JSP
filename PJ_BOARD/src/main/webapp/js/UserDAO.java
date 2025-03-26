package dao.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.user.User;
import util.MybatisUtil;


public class UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAO.class); // Logger 인스턴스 생성
   

    /**
     * 사용자 회원가입
     * @param userId 사용자 ID
     * @param username 사용자 이름
     * @param password 비밀번호 (SHA-256 암호화 적용)
     * @param email 이메일
     * @return 성공 여부
     */
    public boolean registerUser(SqlSession session, User user) {
        int result = session.insert("UserMapper.registerUser", user); // 사용자 등록 쿼리 실행
        return result > 0; // 삽입 성공 여부 반환
    }

}
