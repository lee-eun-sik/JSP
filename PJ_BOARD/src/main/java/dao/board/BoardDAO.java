package dao.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.board.Board;
import model.board.Comment;

public class BoardDAO {
	private static final Logger logger = LogManager.getLogger(BoardDAO.class); // Logger 인스턴스 생성
	
	public List getBoardList(SqlSession session, Board  board) { //넘겨줄 것과 넘겨받을 것을 정함, 마이바티스가 어떤 쿼리를 돌릴지 정함 실행결과를 넣어줌
    	// 사용자 정보를 DB에서 검색
    	List boardList = session.selectList("BoardMapper.getBoardList", board);// 맵퍼에 넘겨줌 매개변수 값 넘겨줌. 쿼리실행함수 돌려줌. 아이디를 가진 쿼리를 찾아서 돌림 필요값 스트링 넘겨준 값을 boardId에 넘겨준다. session 통신을 하기 위함
    	return boardList;
    } 
	public int getTotalBoardCount(SqlSession session) { //넘겨줄 것과 넘겨받을 것을 정함, 마이바티스가 어떤 쿼리를 돌릴지 정함 실행결과를 넣어줌
    	// 사용자 정보를 DB에서 검색
    	int totalCount = session.selectOne("BoardMapper.getTotalBoardCount");// 맵퍼에 넘겨줌 매개변수 값 넘겨줌. 쿼리실행함수 돌려줌. 아이디를 가진 쿼리를 찾아서 돌림 필요값 스트링 넘겨준 값을 boardId에 넘겨준다. session 통신을 하기 위함
    	return totalCount;
    }
	
	public Board getBoardById(SqlSession session, String  boardId) { //넘겨줄 것과 넘겨받을 것을 정함, 마이바티스가 어떤 쿼리를 돌릴지 정함 실행결과를 넣어줌
    	// 사용자 정보를 DB에서 검색
    	Board  board = session.selectOne("BoardMapper.getBoardById", boardId);// 맵퍼에 넘겨줌 매개변수 값 넘겨줌. 쿼리실행함수 돌려줌. 아이디를 가진 쿼리를 찾아서 돌림 필요값 스트링 넘겨준 값을 boardId에 넘겨준다. session 통신을 하기 위함
    	return board;
    }
	
	public boolean createBoard(SqlSession session, Board board) {
		int result = session.insert("BoardMapper.create",board);
		return result >0;		//삽입 성공 여부 반환
	}
	
	public boolean updateBoard(SqlSession session, Board board) {
		int result = session.update("BoardMapper.update",board);
		return result >0; 		// 수정 성공 여부 반환
	}
	
	public boolean deleteBoard(SqlSession session, Board board) {
		int result = session.update("BoardMapper.delete",board);
		return result >0; 		// 삭제 성공 여부 반환
	}
	
	
    
	/*댓글 목록 조회
	@param boardId 게시글 ID
	@return 댓글 목록*/
	public List<Comment> getCommentList(SqlSession session, String boardId) {
	        return session.selectList("BoardMapper.getCommentsByBoardId", boardId);
	}

	    
	/*     
	댓글 등록
	@param comment 댓글 객체
	@return 성공 여부*/
	public boolean insertComment(SqlSession session, Comment comment) {
	        int result = session.insert("BoardMapper.insertComment", comment);
	        return result > 0;
	}

	    /*
	     
	댓글 수정
	@param comment 댓글 객체 (수정할 내용 포함)
	@return 성공 여부*/
	public boolean updateComment(SqlSession session, Comment comment) {
	        int result = session.update("BoardMapper.updateComment", comment);
	        return result > 0;
	}

	    /*
	     
	댓글 삭제
	@param commentId 댓글 ID
	@return 성공 여부*/
	public boolean deleteComment(SqlSession session, Comment comment) {
	    
	        int result = session.update("BoardMapper.deleteComment", comment);
	        return result > 0;
	}


}
