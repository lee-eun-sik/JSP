package model.reservation;

import java.sql.Date;
import java.util.List;

import model.board.Comment;
import model.Model;


public class Reservation extends Model {
	
	
	private String boardId;
	private Date startDate;
	private Date endDate;
	private int reservationDays; // 기존 reservationDate → 예약 일수
	private String address;
	private String variety;
	private String addressDetail;
	private String petName;
	private String phoneNumber;
	private String sitterName;
	private int price;
	private String reply;
	
	private int rn;
	private int startRow;
	private int endRow;
	
	private int page;
	private int size;
	
	private int totalCount;
	private int totalPage;
	private Date reservationDate; // 예약 날짜 추가
   
   
   private List<Comment> comments;
	
	
	@Override
	public String toString() {
	    return "Reservation [boardId=" + boardId + ", reservationDate=" + reservationDays + 
	    	   ", startDate=" + startDate+ ", endDate=" + endDate + 
	           ", address=" + address + ", variety=" + variety + ", petName=" + petName + 
	           ", phoneNumber=" + phoneNumber + ", sitter=" + sitterName + ", price=" + price + 
	           
	           ", rn=" + rn + ", startRow=" + startRow + ", endRow=" + endRow +
	           ", page=" + page + ", size=" + size + ", totalCount=" + totalCount +
	            ", totalPage=" + totalPage + ", createId=" + createId + ", updateId=" + updateId +
	            ", createDt=" + createDt + ", updateDt=" + updateDt + 
	           ", reply=" + reply + ", addressDetail=" + addressDetail +"]";
	}
	
	

	public String getBoardId() {
		return boardId;
	}



	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	


	public int getReservationDays() {
		return reservationDays;
	}



	public void setReservationDays(int reservationDays) {
		this.reservationDays = reservationDays;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}
	
	

	public String getVariety() {
		return variety;
	}



	public void setVariety(String variety) {
		this.variety = variety;
	}


	public String getPetName() {
		return petName;
	}



	public void setPetName(String petName) {
		this.petName = petName;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public String getSitterName() {
		return sitterName;
	}



	public void setSitterName(String sitterName) {
		this.sitterName = sitterName;
	}



	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}



	public String getReply() {
		return reply;
	}



	public void setReply(String reply) {
		this.reply = reply;
	}



	public Reservation() {
		
	}
	
	public String getAddressDetail() {
		return addressDetail;
	}



	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
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



	public int getTotalPage() {
		return totalPage;
	}



	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}





	public List<Comment> getComments() {
		return comments;
	}



	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}



	public Date getReservationDate() {
		return reservationDate;
	}



	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}



	



	
	
	
}

	
	


	
