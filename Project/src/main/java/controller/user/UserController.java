package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.user.User;
import service.user.UserService;
import service.user.UserServiceImpl;


@WebServlet("/user/*")
public class UserController extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4856921015036695792L;
	private static final Logger logger = LogManager.getLogger(UserController.class); 
	private UserService userService;
	
	
	public UserController() {
        super();
        userService = new UserServiceImpl(); 
    }
	/**
	 * GET 화면 이동용 및 조회용
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  logger.info("UserController doGet"); 
	      String path = request.getRequestURI();
	      logger.info("UserController doGet path" + path); 
	      
	      if ("/user/login.do".equals(path)) {
	            request.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp").forward(request, response);
	      } else if ("/user/join.do".equals(path)) {
	            request.getRequestDispatcher("/WEB-INF/jsp/user/join.jsp").forward(request, response);
	      } else if ("/user/main.do".equals(path)) {
	            request.getRequestDispatcher("/WEB-INF/jsp/user/main.jsp").forward(request, response);
	      } else if ("/user/userInfo.do".equals(path)) {
	            request.getRequestDispatcher("/WEB-INF/jsp/user/userInfo.jsp").forward(request, response);
	      } else if("/user/header.do".equals(path)) {
          	request.getRequestDispatcher("/WEB-INF/jsp/user/header.jsp").forward(request, response);    	
          }
	}

	/**
	 * POST ajax 로직 처리용 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("UserController doPost");
        String path = request.getRequestURI();
        response.setContentType("application/json; charset=UTF-8"); // 응답 타입 설정
        PrintWriter out = response.getWriter(); // PrintWriter 객체 생성
        JSONObject jsonResponse = new JSONObject(); // JSON 응답 객체 생성
        try {
            logger.info("UserController doPost path: " + path);
            //유저 가입시 처리
            if ("/user/register.do".equals(path)) { 
            	// User 객체 생성
                User user = new User();
                user.setUserId(request.getParameter("userId"));
                user.setUsername(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
                user.setPassword_confirm(request.getParameter("password_confirm"));
                user.setGender(request.getParameter("gender").substring(0, 1));
                user.setPhonenumber(request.getParameter("phonenumber"));
                //날짜 변환 추가
                String birthdateStr = request.getParameter("birthdate");
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date birthdate = sdf.parse(birthdateStr);
                user.setBirthdate(birthdate);
                user.setEmail(request.getParameter("email"));
                user.setCreateId("SYSTEM");
                
                if(!user.getPassword().equals(user.getPassword_confirm())) {
                	jsonResponse.put("error", "비밀번호가 일치하지 않습니다.");
                	out.print(jsonResponse.toString());
                	out.flush();
                	return;
                }
                jsonResponse.put("success", userService.registerUser(user));
                
            } else if ("/user/loginCheck.do".equals(path)) { 
            	
        		User user = new User();
        		user.setUserId(request.getParameter("id"));
                user.setPassword(request.getParameter("pass")); 
                
        		boolean loginCheck = userService.validateUser(user);
        		
    			//로그인 체크
    			if(loginCheck) {
    				//성공시
    				HttpSession session = request.getSession();
    				User selectUser = userService.getUserById(user.getUserId());
    				
    				session.setAttribute("user", selectUser);
    				
    				jsonResponse.put("success", true); // 성공
    				jsonResponse.put("redirectUrl", "/user/header.do"); // 성공 시 header.jsp로 이동
    			} else {
    				//실패시
    				jsonResponse.put("success", false); // 실패 
    			}
        		
            } else if ("/user/logout.do".equals(path)) { 
            	
            	HttpSession session = request.getSession();
            	User user = (User) session.getAttribute("user");
				User selectUser = userService.getUserById(user.getUserId());
				
				if(selectUser != null) {
					//세션 삭제
					session.invalidate();
					jsonResponse.put("success", true); // 성공 
				} else {
					jsonResponse.put("success", false); // 실패 
				}
				
            } else if ("/user/update.do".equals(path)) {
            	HttpSession session = request.getSession();
            	User sessionUser = (User) session.getAttribute("user");
            	
            	if (sessionUser == null) {
            		jsonResponse.put("success", false);
            		jsonResponse.put("message", "로그인이 필요합니다.");
            	} else {
            		User updateUser = new User();
            		updateUser.setUserId(sessionUser.getUserId());
            		updateUser.setPhonenumber(request.getParameter("phone"));
            		updateUser.setEmail(request.getParameter("email"));
            		
            		try {
            		    String birthdateStr = request.getParameter("birthdate");
            		    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            		    java.util.Date parsedDate = sdf.parse(birthdateStr); // 문자열을 java.util.Date로 변환
            		    java.sql.Date birthdate = new java.sql.Date(parsedDate.getTime()); // java.sql.Date로 변환
            		    updateUser.setBirthdate(birthdate);
            		} catch (Exception e) {
            		    jsonResponse.put("success", false);
            		    jsonResponse.put("message", "생년월일 형식이 올바르지 않습니다.");
            		    out.print(jsonResponse.toString());
            		    out.flush();
            		    return;
            		}
            		
            		boolean updateResult = userService.updateUser(updateUser);
            		
            		if (updateResult) {
            			session.setAttribute("user", updateUser);
            			jsonResponse.put("success", true);
            			jsonResponse.put("message", "회원 정보가 성공적으로 수정되었습니다.");
            		} else {
            			jsonResponse.put("success", false);
            			jsonResponse.put("message", "회원 정보 수정에 실패했습니다.");
            		}
            	}
            } else if ("/user/delete.do".equals(path)) {
            	HttpSession session = request.getSession();
            	User sessionUser = (User) session.getAttribute("user");
            	
            	if (sessionUser == null) {
            		jsonResponse.put("success", false);
            		jsonResponse.put("message", "로그인이 필요합니다.");
            	} else {
            		boolean deleteResult = userService.deleteUser(sessionUser.getUserId());
            		
            		if (deleteResult) {
            			session.invalidate(); // 세션 삭제
            			jsonResponse.put("success", true);
            			jsonResponse.put("message", "회원 탈퇴가 완료되었습니다.");
            		} else {
            			jsonResponse.put("success", false);
            			jsonResponse.put("message", "회원 탈퇴에 실패했습니다.");
            		}
            	}
            } else if("/user/checkDuplicate.do".equals(path)) {
            	String userId = request.getParameter("userId");
            	boolean isDuplicate = userService.isUserIdDuplicate(userId);
            	
            	jsonResponse.put("success", isDuplicate);
            	jsonResponse.put("message", isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.");
            } 
         
        } catch (Exception e) {
            jsonResponse.put("success", false); // 오류 발생 시
            jsonResponse.put("message", "서버 오류 발생"); // 오류 메시지
            logger.error("Error in UserController doPost", e); // 오류 로그 추가
        }
        
        logger.info("jsonResponse.toString() : ", jsonResponse.toString()); 
        // JSON 응답 출력
        out.print(jsonResponse.toString());
        out.flush();
	}

}