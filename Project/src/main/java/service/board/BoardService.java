package service.board;

import java.util.List;

import exception.HException;
import jakarta.servlet.http.HttpServletRequest;
import model.board.Board;
import model.board.Comment;

public interface BoardService {

	List<Board> getBoardList(Board board);

	boolean createBoard(Board board, HttpServletRequest request);

	boolean updateBoard(Board board, HttpServletRequest request);

	boolean deleteBoard(Board board);

	boolean createComment(Comment comment);

	boolean updateComment(Comment comment);

	boolean deleteComment(Comment comment);

	Board getBoardById(String boardId);
    
    
}
