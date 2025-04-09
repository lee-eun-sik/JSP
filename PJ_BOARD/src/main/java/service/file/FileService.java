package service.file;


import java.util.HashMap;
import java.util.List;
import model.common.PostFile;
import jakarta.servlet.http.HttpServletRequest;


public interface FileService {
    public PostFile getFileByBoardIdAndFileId(PostFile  file);
    public HashMap insertBoardFiles(HttpServletRequest request);
    
}
