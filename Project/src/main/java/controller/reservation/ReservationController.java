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
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import java.util.Date;

/**
 * ReservationController 클래스
 * - 게시글과 관련된 요청을 처리하는 서블릿입니다.
 * - 조회, 생성 기능을 포함합니다.
 */
@WebServlet("/reservation/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1MB
        maxFileSize = 10 * 1024 * 1024,  // 10MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class ReservationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ReservationController.class);
    private ReservationService reservationService;

    public ReservationController() {
        super();
        reservationService = new ReservationServiceImpl();
    }

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("ReservationController doGet");
        String path = request.getRequestURI();
        logger.info("ReservationController doGet path: " + path);

        try {
            if ("/reservation/create.do".equals(path)) {
                List<PetSitter> sitterList = reservationService.getPetSitterList();
                logger.info("sitterList: " + sitterList);
                request.setAttribute("sitterList", sitterList);
                request.getRequestDispatcher("/WEB-INF/jsp/reservation/create.jsp").forward(request, response);

            } else if ("/reservation/list.do".equals(path)) {
                int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : DEFAULT_PAGE;
                int size = request.getParameter("size") != null ? Integer.parseInt(request.getParameter("size")) : DEFAULT_SIZE;
                Reservation reservation = new Reservation();
                reservation.setSize(size);
                reservation.setPage(page);

                List<Reservation> reservationList = reservationService.getReservationList(reservation);

                request.setAttribute("reservationList", reservationList);
                request.setAttribute("currentPage", page);
                request.setAttribute("size", size);
                request.setAttribute("totalPage", reservation.getTotalPage());

                request.getRequestDispatcher("/WEB-INF/jsp/reservation/list.jsp").forward(request, response);

            } else if ("/reservation/view.do".equals(path)) {
                String boardId = request.getParameter("id");
                Reservation reservation = reservationService.getReservationById(boardId);
                request.setAttribute("reservation", reservation);
                request.getRequestDispatcher("/WEB-INF/jsp/reservation/view.jsp").forward(request, response);

            } else if ("/reservation/update.do".equals(path)) {
                String boardId = request.getParameter("id");
                logger.info("Received boardId: " + boardId);  // boardId 값이 제대로 들어왔는지 확인
                Reservation reservation = reservationService.getReservationById(boardId);
                logger.info("Fetched reservation: " + reservation.toString());

                // 날짜 포맷 변환
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    // 날짜를 받아와서 포맷 변경
                   

                    // 변환된 날짜를 JSP에 전달할 포맷으로 변경
                	String formattedStartDate = outputFormat.format(reservation.getStartDate());
                	String formattedEndDate = outputFormat.format(reservation.getEndDate());
                    logger.info("Formatted Start Date: " + formattedStartDate);
                    logger.info("Formatted End Date: " + formattedEndDate);

                    // PetSitter 목록과 변환된 날짜를 JSP에 전달
                    List<PetSitter> sitterList = reservationService.getPetSitterList();
                    request.setAttribute("sitterList", sitterList);
                    request.setAttribute("reservation", reservation);
                    request.setAttribute("formattedStartDate", formattedStartDate);
                    request.setAttribute("formattedEndDate", formattedEndDate);

                    // JSP로 포워딩
                    request.getRequestDispatcher("/WEB-INF/jsp/reservation/update.jsp").forward(request, response);

                } catch (Exception e) {
                    logger.error("날짜 포맷 파싱 오류", e);
                    request.setAttribute("errorMessage", "날짜 형식이 잘못되었습니다.");
                    request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            logger.error("Error in ReservationController doGet", e);
            request.setAttribute("errorMessage", "서버 오류 발생");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("ReservationController doPost");
        String path = request.getRequestURI();
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
        	if ("/reservation/create.do".equals(path)) {
        	    String startDateStr = request.getParameter("startDate");
        	    String endDateStr = request.getParameter("endDate");
        	    String reservationDateStr = request.getParameter("reservationDate");
        	    String address = request.getParameter("address");
        	    String variety = request.getParameter("variety");
        	    String petName = request.getParameter("petName");
        	    String phoneNumber = request.getParameter("phoneNumber");
        	    String sitter = request.getParameter("sitter");
        	    String price = request.getParameter("price");
        	    String reply = request.getParameter("reply");
        	    String createId = request.getParameter("createId");
        	    String addressDetail = request.getParameter("addressDetail");

        	    logger.info("startDate: " + startDateStr);
        	    logger.info("endDate: " + endDateStr);
        	    logger.info("reservationDateStr: " + reservationDateStr);
        	    try {
        	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        	        if (startDateStr == null || startDateStr.isEmpty() ||
        	            endDateStr == null || endDateStr.isEmpty() ||
        	            reservationDateStr == null || reservationDateStr.isEmpty()) {
        	            throw new ParseException("하나 이상의 날짜가 비어 있습니다.", 0);
        	        }

        	        java.sql.Date sqlStartDate = new java.sql.Date(sdf.parse(startDateStr).getTime());
        	        java.sql.Date sqlEndDate = new java.sql.Date(sdf.parse(endDateStr).getTime());
        	        java.sql.Date sqlReservationDate = new java.sql.Date(sdf.parse(reservationDateStr).getTime());
        	        logger.info("reservationDateStr: " + reservationDateStr);
        	        // 이후 Reservation 객체 생성 및 저장 생략
        	    } catch (ParseException e) {
        	        logger.error("날짜 파싱 오류: ", e);
        	        jsonResponse.put("success", false);
        	        jsonResponse.put("message", "날짜 형식 오류 또는 값 누락으로 예약 실패");
        	    }
        	} else if ("/reservation/update.do".equals(path)) {
        	    String boardId = request.getParameter("id");
        	    logger.info("Received boardId: " + boardId);
        	    Reservation reservation = reservationService.getReservationById(boardId);
        	    logger.info("Fetched reservation: " + reservation.toString());

        	    try {
        	        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        	        String formattedStartDate = outputFormat.format(reservation.getStartDate());
        	        String formattedEndDate = outputFormat.format(reservation.getEndDate());

        	        logger.info("Formatted Start Date: " + formattedStartDate);
        	        logger.info("Formatted End Date: " + formattedEndDate);

        	        List<PetSitter> sitterList = reservationService.getPetSitterList();
        	        request.setAttribute("sitterList", sitterList);
        	        request.setAttribute("reservation", reservation);
        	        request.setAttribute("formattedStartDate", formattedStartDate);
        	        request.setAttribute("formattedEndDate", formattedEndDate);
        	        
        	        request.getRequestDispatcher("/WEB-INF/jsp/reservation/update.jsp").forward(request, response);

        	    } catch (Exception e) {
        	        logger.error("날짜 포맷 오류", e);
        	        request.setAttribute("errorMessage", "날짜 처리 중 오류가 발생했습니다.");
        	        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        	    }
        	}else if ("/reservation/delete.do".equals(path)) {
                String boardId = request.getParameter("boardId");
                String updateId = request.getParameter("updateId");

                Reservation reservation = new Reservation();
                reservation.setBoardId(boardId);
                reservation.setUpdateId(updateId);

                boolean isDelete = reservationService.deleteReservation(reservation);

                jsonResponse.put("success", isDelete);
                jsonResponse.put("message", isDelete ? "게시글이 성공적으로 삭제 되었습니다." : "게시글 삭제 실패");
            }
        } catch (Exception e) {
            logger.error("Error in ReservationController doPost", e);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "서버 오류 발생");
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
