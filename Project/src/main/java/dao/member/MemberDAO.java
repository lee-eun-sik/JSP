package dao.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.user.User;
import util.MybatisUtil;

public class MemberDAO {

    public List<User> selectAllMembers() {
        try (SqlSession session = MybatisUtil.getSqlSessionFactory().openSession()) {
            return session.selectList("MemberMapper.selectAllMembers");
        }
    }

    public int deleteMemberById(String userId) {
        try (SqlSession session = MybatisUtil.getSqlSessionFactory().openSession()) {
            int result = session.delete("MemberMapper.deleteMemberById", userId);
            session.commit();
            return result;
        }
    }
    
    public List<User> selectMembersByPage(int page, int pageSize) {
        try (SqlSession session = MybatisUtil.getSqlSessionFactory().openSession()) {
            int startRow = (page - 1) * pageSize + 1;
            int endRow = page * pageSize;

            Map<String, Integer> param = new HashMap<>();
            param.put("startRow", startRow);
            param.put("endRow", endRow);

            return session.selectList("MemberMapper.selectMembersByPage", param);
        }
    }
   
    
    public int selectTotalMemberCount() {
        try (SqlSession session = MybatisUtil.getSqlSessionFactory().openSession()) {
            return session.selectOne("MemberMapper.selectTotalMemberCount");
        }
    }
    
    public List<User> searchMembersByKeyword(String searchType, String searchKeyword, int page, int pageSize) {
        try (SqlSession session = MybatisUtil.getSqlSessionFactory().openSession()) {
            int startRow = (page - 1) * pageSize + 1;
            int endRow = page * pageSize;

            Map<String, Object> param = new HashMap<>();
            param.put("searchType", searchType);
            param.put("searchKeyword", "%" + searchKeyword + "%");
            param.put("startRow", startRow);
            param.put("endRow", endRow);

            return session.selectList("MemberMapper.searchMembersByKeyword", param);
        }
    }

    public int getSearchMemberCount(String searchType, String searchKeyword) {
        try (SqlSession session = MybatisUtil.getSqlSessionFactory().openSession()) {
            Map<String, Object> param = new HashMap<>();
            param.put("searchType", searchType);
            param.put("searchKeyword", searchKeyword);

            return session.selectOne("MemberMapper.getSearchMemberCount", param);
        }
    }
}
