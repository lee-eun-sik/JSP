package controller.find;

import java.io.BufferedReader;
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
    	String path = request.getPathInfo();
    	if ("/find/findId.do".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/jsp/find/FindID.jsp").forward(request, response);
        }
     }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 String path = request.getPathInfo();
    	 response.setContentType("application/json; charset=UTF-8");
    	    if ("/find/findId.do".equals(path)) {
    	        StringBuilder sb = new StringBuilder();
    	        BufferedReader reader = request.getReader();
    	        String line;
    	        System.out.println(path);
    	        while ((line = reader.readLine()) != null) {
    	            sb.append(line);
    	        }

    	        try {
    	            JSONObject json = new JSONObject(sb.toString());

    	            String name = json.getString("name");
    	            String phone = json.getString("phone");
    	            String email = json.getString("email");
    	            String birthdateStr = json.getString("birthdate");

    	            // MM/dd/yyyy -> yyyy-MM-dd
    	            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
    	            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
    	            Date birthdate = inputFormat.parse(birthdateStr);
    	            String formattedBirthdate = outputFormat.format(birthdate);
    	            Date birthDateParsed = outputFormat.parse(formattedBirthdate);

    	            User user = userService.findUserByInfo(name, phone, email, birthDateParsed);
    	            
    	            response.setContentType("application/json; charset=UTF-8");
    	            PrintWriter out = response.getWriter();

    	            if (user != null) {
    	                JSONObject result = new JSONObject();
    	                result.put("success", true);
    	                result.put("userId", user.getUserId());
    	                out.print(result.toString());
    	            } else {
    	                out.print("{\"success\": false}");
    	            }
    	            out.flush();
    	            
    	        } catch (Exception e) {
    	            logger.error("아이디 찾기 오류", e);
    	            
    	            response.getWriter().print("{\"success\": false, \"message\": \"서버 오류\"}");
    	        }
    	    }
    }
}