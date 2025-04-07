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
	
	 // 구현 추가
    @Override
    public List<User> getMembersByPage(int offset, int pageSize) {
        return memberDAO.getMembersByOffset(offset, pageSize);
    }
	@Override
	public int getTotalMemberCount() {
	    return memberDAO.selectTotalMemberCount();
	}
}
