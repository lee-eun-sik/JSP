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
    

    public int insertReservation(SqlSession session, Reservation reservation) {
        try {
            return session.insert("mapper.reservation.ReservationMapper.insertReservation", reservation);
        } catch (Exception e) {
            logger.error("예약 정보 삽입 중 오류 발생", e);
            return 0;
        }
    }
    
    public List<Reservation> getAllReservations(SqlSession session) {
        try {
            return session.selectList("mapper.reservation.ReservationMapper.selectAllReservations");
        } catch (Exception e) {
            logger.error("예약 목록 조회 중 오류 발생", e);
            return null;
        }
    }
}