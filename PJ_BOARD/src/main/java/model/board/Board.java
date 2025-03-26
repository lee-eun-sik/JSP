package model.board;

import java.util.List;

import model.Model;
import model.common.PostFile;

public class Board extends Model{
	private String boardId;//자동 게터세터 넣어줌. 
	private String title; 
	private String content; 
	private String viewCount; //공통부분삭제
	
	private int rn;
	private int startRow;
	private int endRow;
	
	private int page;
	private int size;
	
	private int totalCount;
	private int totalPages;
	
	private List<PostFile> postFiles;
	
	private List<Comment> comments;
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", title=" + title + ", content=" + content + ", viewCount=" + viewCount
				+ ", rn=" + rn + ", startRow=" + startRow + ", endRow=" + endRow + ", page=" + page + ", size=" + size
				+ ", totalCount=" + totalCount + ", totalPages=" + totalPages + ", postFiles=" + postFiles
				+ ", createId=" + createId + ", updateId=" + updateId + ", createDt=" + createDt + ", updateDt="
				+ updateDt + "]";
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public int getRn() {
		return rn;
	}

	public void setRn(int rn) {
		this.rn = rn;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<PostFile> getPostFiles() {
		return postFiles;
	}

	public void setPostFiles(List<PostFile> postFiles) {
		this.postFiles = postFiles;
	}
	
	
	
}
