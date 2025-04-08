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
        String path = request.getServletPath();

        if ("/find/findId.do".equals(path)) {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String birthdate = request.getParameter("birthdate");

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDateParsed = sdf.parse(birthdate);

                User user = userService.findUserByInfo(name, phone, email, birthDateParsed);

                response.setContentType("application/json; charset=UTF-8");
                PrintWriter out = response.getWriter();

                if (user != null) {
                    out.print("{\"success\": true, \"userId\": \"" + user.getUserId() + "\"}");
                } else {
                    out.print("{\"success\": false}");
                }
                out.flush();
            } catch (Exception e) {
                logger.error("생년월일 파싱 오류", e);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().print("{\"success\": false, \"message\": \"Invalid birthdate format.\"}");
            }
        } else if ("/find/findPw.do".equals(path)) {
             String id = request.getParameter("id");
             String name = request.getParameter("name");
             String phone = request.getParameter("phone");
             String email = request.getParameter("email");
             String birthdate = request.getParameter("birthdate");

             try {
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                 Date birthDateParsed = sdf.parse(birthdate);

                 User user = new User();
                 user.setUserId(id);
                 user.setUsername(name);
                 user.setPhonenumber(phone);
                 user.setEmail(email);
                 user.setBirthdate(birthDateParsed);

                 User foundUser = userService.findUserForPasswordReset(user);

                 response.setContentType("application/json; charset=UTF-8");
                 PrintWriter out = response.getWriter();

                 if (foundUser != null) {
                     out.print("{\"success\": true, \"message\": \"사용자 인증 완료. 비밀번호를 재설정하세요.\"}");
                 } else {
                     out.print("{\"success\": false, \"message\": \"입력한 정보가 일치하지 않습니다.\"}");
                 }
                 out.flush();
             } catch (Exception e) {
                 logger.error("비밀번호 찾기 오류", e);
                 response.setContentType("application/json; charset=UTF-8");
                 response.getWriter().print("{\"success\": false, \"message\": \"입력값 오류 또는 서버 오류\"}");
             }
         }
     }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    }
}