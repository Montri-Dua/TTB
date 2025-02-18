package com.bank.crm.dto;

import com.bank.crm.enums.RequestStatus;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
	@NotNull(message = "New status is required")
	private RequestStatus newStatus;
	@NotBlank(message = "Comment is required")
    private String comment;
	@NotBlank(message = "UpdatedBy is required")
    private String updatedBy;
    private String reasonCode;
}