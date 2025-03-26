package service.file;


import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import model.common.PostFile;

public interface FileService {
    public PostFile getFileByBoardIdAndFileId(PostFile  file);
    public HashMap insertBoardFiles(HttpServletRequest request);
    
}
