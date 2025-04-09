package controller.reservation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.petSitter.PetSitter;
import model.reservation.Reservation;
import service.reservation.ReservationService;
import service.reservation.ReservationServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


/**
 * ReservationController 클래스
 * - 게시글과 관련된 요청을 처리하는 서블릿입니다.
 * - 조회, 생성 기능을 포함합니다.
 */
@WebServlet("/reservation/*")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,     // 1MB 임시 파일 버퍼
	    maxFileSize = 1024 * 1024 * 10,      // 개별 파일 최대 10MB
	    maxRequestSize = 1024 * 1024 * 50    // 전체 요청 최대 50MB
	)
public class ReservationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ReservationController.class);
    private ReservationService reservationService;

    public ReservationController() {
        super();
        reservationService = new ReservationServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	    	// 요청 URI 파싱
	        String uri = request.getRequestURI();
	        String contextPath = request.getContextPath();
	        String command = uri.substring(contextPath.length());
	
	        if (command.equals("/reservation/reservation.do")) {
	            // 펫시터 목록 조회 예시 (펫시터 리스트가 필요한 경우)
	            List<PetSitter> sitterList = reservationService.getPetSitterList();
	            request.setAttribute("sitterList", sitterList);
	
	            // reservation.jsp로 포워딩
	            request.getRequestDispatcher("/WEB-INF/jsp/reservation/reservation.jsp").forward(request, response);
	        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        try {
            String uri = request.getRequestURI();
            String contextPath = request.getContextPath();
            String command = uri.substring(contextPath.length());

            logger.debug("POST 요청 경로: " + command);

            if (command != null && command.equals("/reservation/reservation.do")) {
                // 1. 파라미터 수신
            	Reservation reservation = new Reservation();
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");
                String reservationDateStr = request.getParameter("reservationDate");
                String address = request.getParameter("address");
                String addressDetail = request.getParameter("addressDetail");
                String variety = request.getParameter("variety");
                String petName = request.getParameter("petName");
                String phoneNumber = request.getParameter("phoneNumber");
                String sitter = request.getParameter("sitter");
                String reply = request.getParameter("reply");
                String createId = request.getParameter("createId");
                int price = Integer.parseInt(request.getParameter("price"));

                logger.debug("예약 정보 수신 완료: {}", petName);

                // 2. 날짜 변환
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date start = sdf.parse(startDateStr); // ParseException 발생 가능
                java.util.Date end = sdf.parse(endDateStr);

                Date startDate = new Date(start.getTime()); // java.sql.Date로 변환
                Date endDate = new Date(end.getTime());
                reservation.setStartDate(startDate);
                reservation.setEndDate(endDate);
                reservation.setReservationDays(Integer.parseInt(reservationDateStr)); // ✔ 수정 완료
                reservation.setAddress(address);
                reservation.setAddressDetail(addressDetail);
                reservation.setVariety(variety);
                reservation.setPetName(petName);
                reservation.setPhoneNumber(phoneNumber);
                reservation.setSitterName(sitter); // ✔ 수정 완료
                reservation.setPrice(price);
                reservation.setReply(reply);
                reservation.setCreateId(createId);

                // 4. 저장 서비스 호출
                boolean result = reservationService.insertReservation(reservation);

                if (result) {
                    json.put("success", true);
                    json.put("message", "예약이 성공적으로 완료되었습니다.");
                } else {
                    json.put("success", false);
                    json.put("message", "예약 저장에 실패했습니다.");
                }
            } else {
                json.put("success", false);
                json.put("message", "잘못된 요청입니다.");
            }
        } catch (ParseException e) {
            logger.error("날짜 파싱 오류", e);
            json.put("success", false);
            json.put("message", "날짜 형식 오류");
        } catch (Exception e) {
            logger.error("예약 처리 중 오류", e);
            json.put("success", false);
            json.put("message", "서버 오류: " + e.getMessage());
        } finally {
            out.print(json.toString());
            out.flush();
        }
    }
}
