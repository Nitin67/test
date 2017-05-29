package org.request;

import java.util.List;

public class RequestValue {

	private List<String> doctorRequests;
	private List<String> pharmcistRequests;
	public List<String> getDoctorRequests() {
		return doctorRequests;
	}
	public void setDoctorRequests(List<String> doctorRequests) {
		this.doctorRequests = doctorRequests;
	}
	public List<String> getPharmcistRequests() {
		return pharmcistRequests;
	}
	public void setDharmcistRequests(List<String> pharmcistRequests) {
		this.pharmcistRequests = pharmcistRequests;
	}
	
	
}
