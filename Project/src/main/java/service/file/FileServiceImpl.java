package service.file;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.file.FileDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import util.FileUploadUtil;
import model.common.PostFile;

import util.MybatisUtil;

public class FileServiceImpl implements FileService {// 보안때문, 인터페이스 호출, 스프링때문에 생긴이유
    private static final Logger logger = LogManager.getLogger(FileServiceImpl.class);
    private FileDAO fileDAO; //DB접속용 클래스 서비스를 왜만들까? 하나의 비지니스 로직이 모든 서비스의 비지니스 로직을 갖는다. 하나의 로직 비지니스로직 트랜젝션, 이 함수가 트랜젝션, 하나의 비지니스는 하나의 트랜젝션

    private SqlSessionFactory sqlSessionFactory; // MyBatis SQL 세션 팩토리
    
    /**
     * boardServiceImpl 생성자
     */
    public FileServiceImpl() {
        this.fileDAO = new FileDAO();
        try {
            sqlSessionFactory = MybatisUtil.getSqlSessionFactory(); // SQL 세션 팩토리 초기화, 통신하나가 트랜젝션을 유지함
        } catch (Exception e) {
            logger.error("Mybatis 오류", e); // 오류 발생 시 로그 출력
        }
    }
    
	@Override
	public PostFile getFileByBoardIdAndFileId(PostFile file) {
		SqlSession session = sqlSessionFactory.openSession();
		PostFile selectPostFile = fileDAO.getFileByBoardIdAndFileId(session, file); // 사용자
		return selectPostFile;
	}

	public HashMap insertBoardFiles(HttpServletRequest request) { //오브젝트가 안에 들어감. 꺼낼때 강제로 형변환, 안에 들어갈 것을 정함 그것이 제네릭
		SqlSession session = sqlSessionFactory.openSession();
		boolean result = false;
		HashMap resultMap = new HashMap();
		try {
		
			// 파일 업로드 처리
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			String userId = request.getParameter("userId");
			String basePath = request.getParameter("basePath");
		
			// 파일 업로드 파트 필터링
			List<Part> fileParts = new ArrayList<>();
			for (Part part : request.getParts()) {
				if ("files".equals(part.getName()) && part.getSize() > 0) {
					fileParts.add(part);
				}
			}
			
			// 업로드된 파일들을 처리하여 PostFile 객체 리스트 반환
			List<PostFile> fileList = FileUploadUtil.uploadFiles(fileParts, basePath, boardId, userId);
		
			for (PostFile postFile : fileList) {
				fileDAO.insertBoardFile(session, postFile);
			}
			if(fileList.size() > 0) { // 파일이 단일이고 이미지 업로드를 할때
				resultMap.put("fileId", String.valueOf(fileList.get(0).getFileId()));
			}
			resultMap.put("result", true);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			resultMap.put("result", false);
		}
		return resultMap;
	}
}
