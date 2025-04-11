package controller.find;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.User;
import service.user.UserService;
import service.user.UserServiceImpl;

@WebServlet("/find/*")
public class FindController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(FindController.class);
    private UserService userService;

    public FindController() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length()); // context path 제거
    	System.out.println("GET path: " + path);
    	if ("/find/FindID.do".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/jsp/find/findId.jsp").forward(request, response);
        } else if ("/find/FindPw.do".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/jsp/find/findPw.jsp").forward(request, response);
        }
     }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length()); // context path 제거
    	 
    	 response.setContentType("application/json; charset=UTF-8");
    	 if ("/find/FindID.do".equals(path)) {
    		    // 하나의 처리 로직만 유지
    		    StringBuilder sb = new StringBuilder();
    		    BufferedReader reader = request.getReader();
    		    String line;
    		    while ((line = reader.readLine()) != null) {
    		        sb.append(line);
    		    }

    		    try {
    		        JSONObject json = new JSONObject(sb.toString());
    		        String name = json.getString("name");
    		        String phone = json.getString("phone");
    		       
    		        String email = json.optString("email", ""); // email optional
    		        String birthdateStr = json.getString("birthdate");

    		        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    		        Date birthDate = formatter.parse(birthdateStr);

    		        List<User> userList = userService.findUsersByInfo(name, phone, email, birthDate);

    		        JSONObject result = new JSONObject();
    		        if (userList != null && !userList.isEmpty()) {
    		            result.put("success", true);

    		            JSONArray userIdArray = new JSONArray();
    		            for (User u : userList) {
    		                userIdArray.put(u.getUserId());
    		            }

    		            result.put("userId", userIdArray);
    		        } else {
    		            result.put("success", false);
    		            result.put("message", "일치하는 사용자가 없습니다.");
    		        }


    		        response.setContentType("application/json");
    		        response.getWriter().print(result.toString());

    		    } catch (Exception e) {
    		        logger.error("아이디 찾기 오류", e);
    		        response.getWriter().print("{\"success\": false, \"message\": \"서버 오류\"}");
    		    }
    		}else if ("/find/FindPw.do".equals(path)) {
    	        StringBuilder sb = new StringBuilder();
    	        BufferedReader reader = request.getReader();
    	        String line;
    	        while ((line = reader.readLine()) != null) {
    	            sb.append(line);
    	        }

    	        try {
    	            JSONObject json = new JSONObject(sb.toString());
    	            String name = json.getString("name");
    	            String userId = json.getString("userId");
    	            String phone = json.getString("phone");
    	            String email = json.optString("email", ""); // ✅ email도 받아오기
    	            String birthdateStr = json.getString("birthdate");

    	            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
    	            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
    	            Date birthdate = inputFormat.parse(birthdateStr);
    	            String formattedBirthdate = outputFormat.format(birthdate);
    	            Date birthDateParsed = outputFormat.parse(formattedBirthdate);

    	            List<User> users = userService.findUsersByInfo(name, phone, email, birthDateParsed);

    	            JSONObject result = new JSONObject();
    	            if (users != null && !users.isEmpty()) {
    	                User u = users.get(0); // 첫 번째 사용자만 사용
    	                result.put("success", true);
    	                result.put("userId", u.getUserId());
    	                result.put("password", u.getPassword()); // 💡 단일 비밀번호 추가
    	            } else {
    	                result.put("success", false);
    	            }
    	            response.getWriter().print(result.toString());
    	        } catch (Exception e) {
    	            logger.error("비밀번호 찾기 오류", e);
    	            response.getWriter().print("{\"success\": false, \"message\": \"서버 오류\"}");
    	        }
    	    } 
    }
}