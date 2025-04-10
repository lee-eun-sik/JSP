package service.reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.board.BoardDAO;
import dao.petSitter.PetSitterDAO;
import dao.reservation.ReservationDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import model.board.Comment;
import model.board.Board;
import model.petSitter.PetSitter;
import model.reservation.Reservation;
import util.MybatisUtil;

public class ReservationServiceImpl implements ReservationService {
private static final Logger logger = LogManager.getLogger(ReservationServiceImpl.class);
    
    private PetSitterDAO petSitterDAO = new PetSitterDAO();
    private ReservationDAO reservationDAO = new ReservationDAO(); // DAO 객체 선언 추가
    @Override
    public List<PetSitter> getPetSitterList() {
        try {
            return petSitterDAO.getAllPetSitters();
        } catch (Exception e) {
            logger.error("펫시터 목록 조회 중 오류 발생", e);
            return null;
        }
    }

	

    @Override
    public boolean createReservation(Reservation reservation, HttpServletRequest request) {
        SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int result = reservationDAO.insertReservation(session, reservation);
            session.commit(); // 성공 시 커밋
            return result > 0;
        } catch (Exception e) {
            logger.error("예약 등록 중 오류 발생", e);
            return false;
        }
    }

	
    @Override
    public List<Reservation> getReservationList() {
        SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return reservationDAO.getAllReservations(session);
        } catch (Exception e) {
            logger.error("예약 목록 조회 중 오류 발생", e);
            return new ArrayList<>();
        }
    }
}
