package com.bank.crm.entity;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

import com.bank.crm.converter.MapToJsonConverter;
import com.bank.crm.enums.RequestPriority;
import com.bank.crm.enums.RequestStatus;
import com.bank.crm.enums.RequestType;

@Entity
@Table(name = "service_requests")
@Data
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@Column(length = 20)
    private String customerId;

	@Enumerated(EnumType.STRING)
    private RequestType type;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    
    @Enumerated(EnumType.STRING)
    private RequestPriority priority;
    
    private String assignedDepartment;
    
    private LocalDateTime createdAt;
    
    
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

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

    public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	private LocalDateTime updatedAt;
    
    public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

    @Convert(converter = MapToJsonConverter.class)
    @Column(columnDefinition = "nvarchar(MAX)")
    private Map<String, Object> metadata;
	   
    // Explicit getters and setters if needed
    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }


    
}
