package dao.reservation;

import java.util.List;



import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.board.Comment;
import model.reservation.Reservation;
import model.board.Board;



public class ReservationDAO {
    private static final Logger logger = LogManager.getLogger(ReservationDAO.class); // Logger 인스턴스 생성
   
    //예약신청 생성
    public boolean createReservation(SqlSession session, Reservation reservation) {
        int result = session.insert("ReservationMapper.create", reservation); // 사용자 등록 쿼리 실행
        return result > 0; // 삽입 성공 여부 반환
    }
    
    //예약목록 아이디로 조회
    public Reservation getReservationById(SqlSession session, String boardId) {
        // 사용자 정보를 DB에서 검색
    	Reservation reservation = 
        			//selectOned는 select할건데 하나만 가져올거야.
        			//mapper namespace="BoardMapper"에서 select id="getBoardById"를 BoardMapper.xml에서 가져옴
        		session.selectOne("ReservationMapper.getReservationById", boardId);
        				 return reservation;

    }
        //조건에 맞는 게시판 목록조회
        public List  getReservationList(SqlSession session, Reservation reservation) {

        	List ReservationList =   
            		session.selectList("ReservationMapper.getReservationList", reservation);
            				 return ReservationList;
        }
        //전체 예약신청 총 개수
        public int  getTotalReservationCount(SqlSession session) {

        	int totalCount =   
            		session.selectOne("ReservationMapper.getTotalReservationCount");
            				 return totalCount;
        }
        
        //신청 수정
        public boolean updateReservation(SqlSession session, Reservation reservation) {
            int result = session.update("ReservationMapper.updateReservation", reservation);
            return result > 0; // 업데이트 성공 여부 반환
        }
        
        //신청 삭제
        public boolean deleteReservation(SqlSession session, Reservation reservation) {
            int result = session.update("ReservationMapper.deleteReservation", reservation);  // 삭제 처리 쿼리 실행
            return result > 0;  // 업데이트 성공 여부 반환
        }
}  