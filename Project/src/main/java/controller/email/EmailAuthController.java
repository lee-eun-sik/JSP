package controller.email;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class EmailAuthController
 */
@WebServlet("/EmailAuthController")
public class EmailAuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailAuthController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String toEmail = request.getParameter("email");  // 인증 대상 이메일
		String authCode = generateAuthCode();           // 인증코드 생성

		try {
			// 이메일 전송
			util.EmailUtil.sendEmail(toEmail, "이메일 인증 코드", "인증 코드는 다음과 같습니다: " + authCode);

			// 세션에 인증 코드 저장
			request.getSession().setAttribute("authCode", authCode);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("{\"success\": true}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"success\": false}");
		}
	}

	// 인증 코드 생성
	private String generateAuthCode() {
		int code = (int)(Math.random() * 900000) + 100000; // 6자리 숫자
		return String.valueOf(code);
	}
	

}
