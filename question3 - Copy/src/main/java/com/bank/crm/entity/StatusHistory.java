package com.bank.crm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.bank.crm.enums.RequestStatus;

@Entity
@Table(name = "status_history")
@Data
public class StatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long requestId;

    @Enumerated(EnumType.STRING)
    private RequestStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private RequestStatus newStatus;

    private String comment;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	private String updatedBy;

    @Column(columnDefinition = "DATETIME2")
    private LocalDateTime updatedAt;
}