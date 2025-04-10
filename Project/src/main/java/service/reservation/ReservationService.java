package service.reservation;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import model.board.Comment;
import model.petSitter.PetSitter;
import model.reservation.Reservation;
import model.board.Board;

public interface ReservationService {

	//예약신청
	public boolean createReservation(Reservation reservation, HttpServletRequest request);
	
    //펫시터목록을 조회하는 메서드
    public List<PetSitter> getPetSitterList();
    
    // 예약 목록 조회 메서드
    public List<Reservation> getReservationList();

}