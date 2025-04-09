package dao.petSitter;

import java.util.List;



import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.board.Comment;
import model.petSitter.PetSitter;
import model.reservation.Reservation;
import util.MybatisUtil;
import model.board.Board;



public class PetSitterDAO {
    private static final Logger logger = LogManager.getLogger(PetSitterDAO.class); // Logger 인스턴스 생성
   
 // 예약 가능한 펫시터 리스트 조회
    public List<PetSitter> getPetSitterList(SqlSession session) {
    	 List<PetSitter> sitterList = session.selectList("PetSitterMapper.getPetSitterList");
	     logger.info("DAO에서 가져온 펫시터 리스트: {}", sitterList);
	     return sitterList;
        
    }
    

	public List<PetSitter> getAllPetSitters() {
	    try (SqlSession session = MybatisUtil.getSqlSessionFactory().openSession()) {
	        return getPetSitterList(session);
	    } catch (Exception e) {
	        logger.error("getAllPetSitters() 오류 발생", e);
	        return null;
	    }
	}
}  