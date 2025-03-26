package service.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.board.BoardDAO;
import dao.file.FileDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import model.board.Board;
import model.board.Comment;
import model.common.PostFile;
import util.FileUploadUtil;
import util.MybatisUtil;

public class BoardServiceImpl implements BoardService {// 보안때문, 인터페이스 호출, 스프링때문에 생긴이유
    private static final Logger logger = LogManager.getLogger(BoardServiceImpl.class);
    private BoardDAO boardDAO; //DB접속용 클래스 서비스를 왜만들까? 하나의 비지니스 로직이 모든 서비스의 비지니스 로직을 갖는다. 하나의 로직 비지니스로직 트랜젝션, 이 함수가 트랜젝션, 하나의 비지니스는 하나의 트랜젝션
    private FileDAO fileDAO;
    private SqlSessionFactory sqlSessionFactory; // MyBatis SQL 세션 팩토리
    
    /**
     * boardServiceImpl 생성자
     */
    public BoardServiceImpl() {
        this.boardDAO = new BoardDAO();
        this.fileDAO = new FileDAO();
        try {
            sqlSessionFactory = MybatisUtil.getSqlSessionFactory(); // SQL 세션 팩토리 초기화, 통신하나가 트랜젝션을 유지함
        } catch (Exception e) {
            logger.error("Mybatis 오류", e); // 오류 발생 시 로그 출력
        }
    }

    //함수남김
    
	 public Board getBoardById(String boardId) {
		 SqlSession session = sqlSessionFactory.openSession();
		 Board selectBoard = boardDAO.getBoardById(session, boardId); // 사용자 
		 //파일 목록 조회
		 selectBoard.setPostFiles(fileDAO.getFilesByBoardId(session, boardId));
		 
		 //댓글 목록 조회
		 selectBoard.setComments(boardDAO.getCommentList(session, boardId)); //뷰에서만 댓글담, 호출될때
		 
		 return selectBoard;
	 }
	 
	  @Override
	    public boolean createBoard(Board board,HttpServletRequest request) {
	    	SqlSession session = sqlSessionFactory.openSession();
	    	boolean result = false; 
	    	try {
	    		result = boardDAO.createBoard(session, board);
	    		// 파일 업로드 파트 필터링
				List<Part> fileParts = new ArrayList<>();
				for (Part part : request.getParts()) {
					if ("files".equals(part.getName()) && part.getSize() > 0) {
						fileParts.add(part);
						
					}
				}
				
				// 업로드된 파일들을 처리하여 PostFile 객체 리스트 반환
				List<PostFile> fileList = FileUploadUtil.uploadFiles(fileParts, "board", Integer.parseInt(board.getBoardId()), board.getCreateId());
			
				for (PostFile postFile : fileList) {
					fileDAO.insertBoardFile(session, postFile);
				}
	    		// DAO를 통해 회원가입 진행
	            
	            session.commit(); // 트랜잭션 커밋,통신 채널 넣음, 넘겨줌
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		session.rollback(); //DB원상 복구, 하나의 통신 이어줌. 통신이 끊어지면 
			}
	        return result;
	    }

	@Override
	public boolean updateBoard(Board board, HttpServletRequest request) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
    	boolean result = false; 
    	try {
    		// DAO를 통해 회원가입 진행
            result = boardDAO.updateBoard(session, board);
            //기존 파일을 어떻게 할까
            if (result) {
            	
            	String postFilesParam = request.getParameter("remainingFileIds");
            	List<String> postFiles = new ArrayList<String>(); //안 넘어가는 것은 다 삭제
            	if(postFilesParam != null && !postFilesParam.trim().isEmpty()) {
            		postFiles = Arrays.asList(postFilesParam.split(",")); //쉼표로 구분된 파일명 리스트
            	}
            	
            	//기존 파일 조회
            	List<PostFile> existingFiles = fileDAO.getFilesByBoardId(session, 
            								board.getBoardId());
	            //기존 파일 리스트가 있을때
	            if (existingFiles != null&& existingFiles.size() > 0) {	
	            	boolean fileExists = false;
	            	for (PostFile existingFile : existingFiles) {
	            		fileExists = false;
	            		// 새로 넘어온 파일 목록에 기존 파일이 포함되어 있는지 체크
	            		for (String fileId : postFiles) {
	            			if (existingFile.getFileId() == Integer.parseInt(fileId)) {
	            				fileExists = true;
	            				break;
	            			}
	            		}
	            		// 넘어온 파일목록에 없으면 삭제
	            		if (!fileExists) {
	            			existingFile.setUpdateId(board.getUpdateId());
	            			boolean deleteSuccess = fileDAO.deleteFile(session, existingFile);
	            			if (!deleteSuccess) {
	            				session.rollback(); // 파일 삭제 실패 시 롤백
	            				return false;
	            			}
	            		}
	            		
	            	}
	            }
	            	
	        
	            	
	            // 파일 업로드 파트 필터링
	            //새로운 파일을 업로드
				List<Part> fileParts = new ArrayList<>();
				for (Part part : request.getParts()) {
					if ("files".equals(part.getName()) && part.getSize() > 0) {
						fileParts.add(part);
					}
				}
				
				// 업로드된 파일들을 처리하여 PostFile 객체 리스트 반환
				List<PostFile> fileList = FileUploadUtil.uploadFiles(fileParts, "board", 
										Integer.parseInt(board.getBoardId()), board.getUpdateId());
			
				for (PostFile postFile : fileList) {
					fileDAO.insertBoardFile(session, postFile);
				}
		            session.commit(); // 트랜잭션 커밋,통신 채널 넣음, 넘겨줌
		    }
	     } catch (Exception e) {
	    		e.printStackTrace();
	    		session.rollback(); //DB원상 복구, 하나의 통신 이어줌. 통신이 끊어지면 
		}
	       return result;
	}

	@Override
	public boolean deleteBoard(Board board) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
    	boolean result = false; 
    	try {
    		// DAO를 통해 회원가입 진행
            result = boardDAO.deleteBoard(session, board);
            session.commit(); // 트랜잭션 커밋,통신 채널 넣음, 넘겨줌
    	} catch (Exception e) {
    		e.printStackTrace();
    		session.rollback(); //DB원상 복구, 하나의 통신 이어줌. 통신이 끊어지면 
		}
        return result;
	}

	@Override
	public List getBoardList(Board board) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		int page = board.getPage();
		int size = board.getSize();
		
		int totalCount = boardDAO.getTotalBoardCount(session);
		int totalPages = (int) Math.ceil((double) totalCount / size);
		
		int startRow = (page - 1) * size + 1;
		int endRow = page *size;
		
		board.setTotalCount(totalCount);
		board.setTotalPages(totalPages);
		board.setStartRow(startRow);
		board.setEndRow(endRow);
		
		List list = boardDAO.getBoardList(session, board);
		return list;
	}

	@Override
	public boolean createComment(Comment comment) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
    	boolean result = false; 
    	try {
    		result = boardDAO.insertComment(session, comment);
            session.commit(); // 트랜잭션 커밋,통신 채널 넣음, 넘겨줌
    	} catch (Exception e) {
    		e.printStackTrace();
    		session.rollback(); //DB원상 복구, 하나의 통신 이어줌. 통신이 끊어지면 
		}
        return result;
	}

	@Override
	public boolean updateComment(Comment comment) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
    	boolean result = false; 
    	try {
    		result = boardDAO.updateComment(session, comment);
            session.commit(); // 트랜잭션 커밋,통신 채널 넣음, 넘겨줌
    	} catch (Exception e) {
    		e.printStackTrace();
    		session.rollback(); //DB원상 복구, 하나의 통신 이어줌. 통신이 끊어지면 
		}
        return result;
	}

	@Override
	public boolean deleteComment(Comment comment) {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
    	boolean result = false; 
    	try {
    		result = boardDAO.deleteComment(session, comment);
            session.commit(); // 트랜잭션 커밋,통신 채널 넣음, 넘겨줌
    	} catch (Exception e) {
    		e.printStackTrace();
    		session.rollback(); //DB원상 복구, 하나의 통신 이어줌. 통신이 끊어지면 
		}
        return result;
	}
}
