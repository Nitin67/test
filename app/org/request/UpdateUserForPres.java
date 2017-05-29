package org.request;

import java.util.List;

import org.models.Doctor;
import org.models.Pharmcist;

public class UpdateUserForPres {

	private List<Pharmcist> pharmcists;
	private List<Doctor> doctors;
	public List<Pharmcist> getPharmcists() {
		return pharmcists;
	}
	public void setPharmcists(List<Pharmcist> pharmcists) {
		this.pharmcists = pharmcists;
	}
	public List<Doctor> getDoctors() {
		return doctors;
	}
	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}
	
}
