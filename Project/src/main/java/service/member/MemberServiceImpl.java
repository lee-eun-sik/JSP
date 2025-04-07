package service.member;

import java.util.List;

import dao.member.MemberDAO;
import model.user.User;
public class MemberServiceImpl implements MemberService {
	private MemberDAO memberDAO = new MemberDAO();
	
	@Override
	public List<User> getAllMembers() {
		return memberDAO.selectAllMembers();
	}
	
	
	@Override
	public boolean deleteMember(String userId) {
		return memberDAO.deleteMemberById(userId) > 0;
	}
	
	@Override
	public List<User> getMembersByPage(int page, int pageSize) {
	    return memberDAO.selectMembersByPage(page, pageSize);
	}
	@Override
	public int getTotalMemberCount() {
	    return memberDAO.selectTotalMemberCount();
	}
	
	@Override
	public List<User> searchMembersByKeyword(String searchType, String searchKeyword, int page, int pageSize) {
	    // 부분일치 검색을 위해 와일드카드 % 추가
	    String keyword = "%" + searchKeyword + "%";
	    return memberDAO.searchMembersByKeyword(searchType, keyword, page, pageSize);
	}

	@Override
	public int getSearchMemberCount(String searchType, String searchKeyword) {
	    // 부분일치 검색을 위해 와일드카드 % 추가
	    String keyword = "%" + searchKeyword + "%";
	    return memberDAO.getSearchMemberCount(searchType, keyword);
	}
}
