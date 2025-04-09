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
	public boolean insertReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		try (SqlSession session = MybatisUtil.getSqlSessionFactory().openSession(true)) {
	        return reservationDAO.createReservation(session, reservation);
	    } catch (Exception e) {
	        logger.error("예약 등록 중 오류 발생", e);
	        return false;
	    }
	}

	@Override
	public boolean createReservation(Reservation reservation, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Reservation getReservationById(String boardId) {
	    try (SqlSession session = MybatisUtil.getSqlSessionFactory().openSession()) {
	        return reservationDAO.getReservationById(session, boardId);
	    } catch (Exception e) {
	        logger.error("예약 상세 조회 중 오류 발생", e);
	        return null;
	    }
	}

	@Override
	public List getReservationList(Reservation reservation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateReservation(Reservation reservation, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
