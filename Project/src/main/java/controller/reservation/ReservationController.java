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
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

@WebServlet("/reservation/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class ReservationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ReservationController.class);
    private ReservationService reservationService;

    public ReservationController() {
        super();
        reservationService = new ReservationServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = uri.substring(contextPath.length());

        if (command.equals("/reservation/reservation.do")) {
            List<PetSitter> sitterList = reservationService.getPetSitterList();
            request.setAttribute("sitterList", sitterList);
            request.getRequestDispatcher("/WEB-INF/jsp/reservation/reservation.jsp").forward(request, response);
        } else if (command.equals("/reservation/Reservationlist.do")) {
            List<Reservation> reservationList = reservationService.getReservationList();  // 예약 목록 조회
            request.setAttribute("reservationList", reservationList);
            request.getRequestDispatcher("/WEB-INF/jsp/reservation/Reservationlist.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        JSONObject json = new JSONObject();
        PrintWriter out = response.getWriter();

        try {
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String reservationDateStr = request.getParameter("reservationDate");
            String address = request.getParameter("address");

            if (startDateStr == null || endDateStr == null || reservationDateStr == null || address == null) {
                throw new IllegalArgumentException("필수 파라미터 누락");
            }

            int reservationDate = Integer.parseInt(reservationDateStr);
            String addressDetail = request.getParameter("addressDetail");
            String variety = request.getParameter("variety");
            String petName = request.getParameter("petName");
            String phoneNumber = request.getParameter("phoneNumber");
            String sitter = request.getParameter("sitter");
            int price = Integer.parseInt(request.getParameter("price"));
            String reply = request.getParameter("reply");
            String createId = request.getParameter("createId");
            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);

            // 예약 일 수 계산
            long diff = endDate.getTime() - startDate.getTime();
            int reservationDays = (int)(diff / (1000 * 60 * 60 * 24)); // 하루 단위로 변환
            Reservation reservation = new Reservation();
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservation.setReservationDays(reservationDays); 
            reservation.setReservationDate(new Date(System.currentTimeMillis()));;
            reservation.setAddress(address);
            reservation.setAddressDetail(addressDetail);
            reservation.setVariety(variety);
            reservation.setPetName(petName);
            reservation.setPhoneNumber(phoneNumber);
            reservation.setSitterName(sitter);
            reservation.setPrice(price);
            reservation.setReply(reply);
            reservation.setCreateId(createId);

            logger.info("예약 요청 - createId: {}, petName: {}, sitter: {}", createId, petName, sitter);

            boolean success = reservationService.createReservation(reservation, request);

            json.put("success", success);
           

        } catch (Exception e) {
            logger.error("예약 처리 중 예외 발생", e);
            json.put("success", false);
            json.put("message", "예약 처리 중 오류가 발생했습니다.");
        }

        out.print(json.toString());
    }
}
