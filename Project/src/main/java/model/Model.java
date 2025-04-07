package model;

import java.util.Date;

public class Model {
   
	public String createId; // 계정 생성자 ID
	public String updateId; // 계정 수정자 ID
	public Date createDt; // 생성일
	public Date updateDt; // 수정일
	

	public Model() {
    	
    }

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

}
