package controller.member;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.user.User;
import service.member.MemberService;
import service.member.MemberServiceImpl;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4526094828156713556L;
	private MemberService memberService = new MemberServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String path = request.getRequestURI();
	    String contextPath = request.getContextPath();
	    String command = path.substring(contextPath.length());

	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("loginUser");

	    if (loginUser == null || !"admin".equals(loginUser.getRole())) {
	        session.setAttribute("redirectAfterLogin", path);
	        response.sendRedirect(contextPath + "/user/login.do");
	        return;
	    }

	    // 페이지 번호를 요청 파라미터에서 가져오되, 기본값을 1로 설정
	    int page = 1;
	    String pageParam = request.getParameter("page");
	    if (pageParam != null && !pageParam.isEmpty()) {
	        try {
	            page = Integer.parseInt(pageParam);
	        } catch (NumberFormatException e) {
	            // 페이지 번호가 잘못되었을 경우 기본값 1로 설정
	            page = 1;
	        }
	    }

	    // 한 페이지에 표시할 회원 수 (예: 10명)
	    int pageSize = 10;

	    // 전체 회원 목록을 가져와서 페이지 계산
	    List<User> userList = memberService.getAllMembers();

	    // 전체 회원 수
	    int totalUsers = userList.size();

	    // 전체 페이지 수 계산
	    int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

	    // 페이지 번호와 전체 페이지 수를 request에 속성으로 설정
	    request.setAttribute("userList", userList);
	    request.setAttribute("page", page);
	    request.setAttribute("totalPages", totalPages);

	    // 페이지 목록을 보여주는 JSP로 포워딩
	    request.getRequestDispatcher("/member/Memberlist.jsp").forward(request, response);
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = path.substring(contextPath.length());

        if ("/member/delete.do".equals(command)) {
            String userId = request.getParameter("userId");
            response.setContentType("application/json; charset=UTF-8");
            JSONObject json = new JSONObject();

            try {
                boolean success = memberService.deleteMember(userId);
                json.put("status", success ? "success" : "fail");
                if (!success) {
                    json.put("message", "삭제 실패");
                }
            } catch (Exception e) {
                json.put("status", "error");
                json.put("message", e.getMessage());
            }

            PrintWriter out = response.getWriter();
            out.print(json.toString());
            out.flush();
        }
    }
}
