package com.bank.crm.event;

import com.bank.crm.enums.RequestStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatusUpdateEvent extends BaseEvent {
    private Long requestId;
    public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public RequestStatus getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(RequestStatus oldStatus) {
		this.oldStatus = oldStatus;
	}

	public RequestStatus getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(RequestStatus newStatus) {
		this.newStatus = newStatus;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	private RequestStatus oldStatus;
    private RequestStatus newStatus;
    private String updatedBy;
    private String comment;

    public StatusUpdateEvent() {
        super();
        this.setEventType("STATUS_UPDATE_EVENT");
    }
}