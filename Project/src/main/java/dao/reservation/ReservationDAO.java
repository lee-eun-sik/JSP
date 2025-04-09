package dao.reservation;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import model.reservation.Reservation;

public class ReservationDAO {
    private static final Logger logger = LogManager.getLogger(ReservationDAO.class);

    // 예약 생성
    public boolean createReservation(SqlSession session, Reservation reservation) {
        int result = session.insert("ReservationMapper.create", reservation);
        logger.info("예약 생성 결과: {}", result);
        return result > 0;
    }

    // 예약 ID로 조회
    public Reservation getReservationById(SqlSession session, String boardId) {
        Reservation reservation = session.selectOne("ReservationMapper.getReservationById", boardId);
        logger.info("예약 상세 조회: {}", reservation);
        return reservation;
    }

    // 예약 목록 조회
    @SuppressWarnings("unchecked")
    public List<Reservation> getReservationList(SqlSession session, Reservation reservation) {
        List<Reservation> list = session.selectList("ReservationMapper.getReservationList", reservation);
        logger.info("예약 목록 조회 결과 수: {}", list != null ? list.size() : 0);
        return list;
    }

    // 전체 예약 수
    public int getTotalReservationCount(SqlSession session) {
        int total = session.selectOne("ReservationMapper.getTotalReservationCount");
        logger.info("총 예약 건수: {}", total);
        return total;
    }

    // 예약 수정
    public boolean updateReservation(SqlSession session, Reservation reservation) {
        int result = session.update("ReservationMapper.updateReservation", reservation);
        logger.info("예약 수정 결과: {}", result);
        return result > 0;
    }

    // 예약 삭제
    public boolean deleteReservation(SqlSession session, Reservation reservation) {
        int result = session.update("ReservationMapper.deleteReservation", reservation);
        logger.info("예약 삭제 결과: {}", result);
        return result > 0;
    }
}