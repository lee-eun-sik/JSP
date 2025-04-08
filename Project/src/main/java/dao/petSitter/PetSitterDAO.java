package dao.petSitter;

import java.util.List;



import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.board.Comment;
import model.petSitter.PetSitter;
import model.reservation.Reservation;
import model.board.Board;



public class PetSitterDAO {
    private static final Logger logger = LogManager.getLogger(PetSitterDAO.class); // Logger 인스턴스 생성
   
 // 예약 가능한 펫시터 리스트 조회
    public List<PetSitter> getPetSitterList(SqlSession session) {
        return session.selectList("PetSitterMapper.getPetSitterList");
        
    }
}  