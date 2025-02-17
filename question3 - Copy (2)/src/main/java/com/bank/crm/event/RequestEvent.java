package com.bank.crm.event;

import com.bank.crm.enums.RequestEventType;
import com.bank.crm.enums.RequestStatus;
import com.bank.crm.enums.RequestType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequestEvent extends BaseEvent {
    public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	private Long requestId;
    private String customerId;
    private RequestType type;
    private RequestStatus status;
    private Map<String, Object> metadata;

    
    
    public RequestEvent() {
        super();
        this.setEventType("REQUEST_EVENT");
    }
}