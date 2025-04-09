package dao.reservation;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.petSitter.PetSitter;
import model.reservation.Reservation;
import util.MybatisUtil;

public class ReservationDAO {
    private static final Logger logger = LogManager.getLogger(ReservationDAO.class);
    private SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
    

    // 예약 ID로 조회
    public Reservation getReservationById(SqlSession session, String boardId) {
        Reservation reservation = session.selectOne("ReservationMapper.getReservationById", boardId);
        logger.info("예약 상세 조회: {}", reservation);
        return reservation;
    }

 // 예약 등록 메서드 추가
    public boolean createReservation(SqlSession session, Reservation reservation) {
        try {
            int result = session.insert("ReservationMapper.insertReservation", reservation);
            logger.info("예약 등록 결과: {}", result);
            return result > 0;
        } catch (Exception e) {
            logger.error("예약 등록 중 예외 발생", e);
            return false;
        }
    }
   
}