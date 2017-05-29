package org.request;

import org.models.Role;

public class AccessPermissionRequest {

	private String requestedUserId;
	private Role role;
	private String patientId;
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getRequestedUserId() {
		return requestedUserId;
	}
	public void setRequestedUserId(String requestedUserId) {
		this.requestedUserId = requestedUserId;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
}
