package com.bank.crm.event;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public abstract class BaseEvent {

    private String eventId;
	private String eventType;
    private LocalDateTime timestamp;
    
    public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}



    public BaseEvent() {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
    }
}