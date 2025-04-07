package controller.member;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.user.User;

import java.io.IOException;
import java.util.List;
import service.member.MemberService;
import service.member.MemberServiceImpl;
/**
 * Servlet implementation class MemberListServlet
 */
@WebServlet("/member/Memberlist.do")
public class MemberListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 👇 이 부분이 누락되어 있었음 (memberService 선언)
    private MemberService memberService = new MemberServiceImpl();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String path = request.getPathInfo(); // /Memberlist.do 또는 /delete.do 등
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // 관리자 권한 확인
        if (loginUser == null || !"admin".equals(loginUser.getRole())) {
            session.setAttribute("redirectAfterLogin", contextPath + "/member/Memberlist.do");
            response.sendRedirect(contextPath + "/user/login.do");
            return;
        }

        if (path == null || path.equals("/") || path.equals("/Memberlist.do")) {
            // --- 회원 목록 처리 ---
            int page = 1;
            int pageSize = 10;

            String pageParam = request.getParameter("page");
            if (pageParam != null && pageParam.matches("\\d+")) {
                page = Integer.parseInt(pageParam);
            }
            if (page < 1) page = 1;

            String searchType = request.getParameter("searchType");
            String searchKeyword = request.getParameter("searchKeyword");

            int totalUsers;
            List<User> userList;

            if (searchType != null && searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                // 검색 처리
                userList = memberService.searchMembersByKeyword(searchType, searchKeyword, page, pageSize);
                totalUsers = memberService.getSearchMemberCount(searchType, searchKeyword);
            } else {
                // 전체 조회
                totalUsers = memberService.getTotalMemberCount();
                userList = memberService.getMembersByPage(page, pageSize);
            }

            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);
            if (page > totalPages) page = totalPages;
            userList = memberService.getMembersByPage(page, pageSize);
            request.setAttribute("userList", userList);
            request.setAttribute("page", page);
            request.setAttribute("totalPages", totalPages);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/member/Memberlist.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404 처리
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if ("/delete.do".equals(path)) {
            String userId = request.getParameter("userId");
            boolean result = memberService.deleteMember(userId);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"success\":" + result + "}");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
