package service.member;

import java.util.List;

import model.user.User;

public interface MemberService {
	List<User> getAllMembers();
	boolean deleteMember(String userId);
	
	List<User> getMembersByPage(int page, int pageSize);
	int getTotalMemberCount();
	
	List<User> searchMembersByKeyword(String searchType, String searchKeyword, int page, int pageSize);
	int getSearchMemberCount(String searchType, String searchKeyword);
	
}
