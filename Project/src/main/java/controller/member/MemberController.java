package controller.member;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.user.User;
import service.user.UserServiceImpl;
import service.user.UserService;
import util.AESUtil;
import util.MybatisUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;

import controller.user.UserController;
import dao.user.UserDAO;

/**
 * Servlet implementation class memberController
 */
@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MemberController.class);     
	private UserService userService = new UserServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI(); 
        String contextPath = request.getContextPath(); 
        String command = path.substring(contextPath.length()); 

        System.out.println("path: " + path);
        System.out.println("command: " + command);

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // 로그인 여부 및 관리자 권한 체크
        if (loginUser == null || !"admin".equals(loginUser.getRole())) {
            session.setAttribute("redirectAfterLogin", path);
            response.sendRedirect(contextPath + "/user/login.do");
            return;
        }

        List<User> userList = userService.getAllUsers(); // 인스턴스 변수 userService 사용

        // ✅ 비밀번호 복호화 로직 추가
        for (User u : userList) {
            try {
                String decryptedPassword = AESUtil.decrypt(u.getPassword());
                u.setDecryptedPassword(decryptedPassword);
                u.setPassword(decryptedPassword);
            } catch (Exception e) {
                logger.error("비밀번호 복호화 실패 - 사용자 ID: " + u.getUserId(), e);
                u.setPassword("복호화 실패");
            }
        }

        request.setAttribute("userList", userList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/member/Memberlist.jsp");
        dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
}
