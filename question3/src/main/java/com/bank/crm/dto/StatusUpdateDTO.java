package com.bank.crm.dto;

import com.bank.crm.enums.RequestStatus;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusUpdateDTO {
    public RequestStatus getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(RequestStatus newStatus) {
		this.newStatus = newStatus;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	private RequestStatus newStatus;
    private String comment;
    private String updatedBy;
    private String reasonCode;
}