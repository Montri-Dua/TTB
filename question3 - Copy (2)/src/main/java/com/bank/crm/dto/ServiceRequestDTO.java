package com.bank.crm.dto;


import com.bank.crm.enums.RequestStatus;
import com.bank.crm.enums.RequestType;
import com.bank.crm.enums.RequestPriority;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

import com.bank.crm.enums.RequestStatus;
import com.bank.crm.enums.RequestType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDTO {
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RequestStatus getStatus() {
		return status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	public RequestPriority getPriority() {
		return priority;
	}
	public void setPriority(RequestPriority priority) {
		this.priority = priority;
	}
	public String getAssignedDepartment() {
		return assignedDepartment;
	}
	public void setAssignedDepartment(String assignedDepartment) {
		this.assignedDepartment = assignedDepartment;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Map<String, Object> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
	private Long id;
    private String customerId;
    private RequestType type;
    private String description;
    private RequestStatus status; //
    private RequestPriority priority;
    private String assignedDepartment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<String, Object> metadata;
}