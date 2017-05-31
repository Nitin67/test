package org.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.PrePersist;

@Entity("patient")
public class PatientEntity {

	@Id
	@Indexed(unique=true, dropDups=true)
	private ObjectId id;
	private Patient patient;
	private List<Doctor> authorisedDoctors;
	private List<Pharmcist> authorisedPharmcists;
	private List<Doctor> requestPendingDoctors;
	private List<Pharmcist> requestPendingPharmcists;
	private Date updated;
	private Date created;
	public PatientEntity() {
		created = new Date();
	}
	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}
	@PrePersist
	public void setUpdated() {
		this.updated = new Date();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<Doctor> getAuthorisedDoctors() {
		if (authorisedDoctors == null) {
			authorisedDoctors = new ArrayList<Doctor>();
		}
		return authorisedDoctors;
	}

	public void setAuthorisedDoctors(List<Doctor> authorisedDoctors) {
		this.authorisedDoctors = authorisedDoctors;
	}

	public List<Pharmcist> getAuthorisedPharmcists() {
		if (authorisedPharmcists == null) {
			authorisedPharmcists = new ArrayList<Pharmcist>();
		}
		return authorisedPharmcists;
	}

	public void setAuthorisedPharmcists(List<Pharmcist> authorisedPharmcists) {
		this.authorisedPharmcists = authorisedPharmcists;
	}

	public List<Doctor> getRequestPendingDoctors() {
		if (requestPendingDoctors == null) {
			requestPendingDoctors = new ArrayList<Doctor>();
		}
		return requestPendingDoctors;
	}

	public void setRequestPendingDoctors(List<Doctor> requestPendingDoctors) {
		this.requestPendingDoctors = requestPendingDoctors;
	}

	public List<Pharmcist> getRequestPendingPharmcists() {
		if (requestPendingPharmcists == null) {
			requestPendingPharmcists = new ArrayList<Pharmcist>();
		}
		return requestPendingPharmcists;
	}

	public void setRequestPendingPharmcists(List<Pharmcist> requestPendingPharmcists) {
		this.requestPendingPharmcists = requestPendingPharmcists;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		if (this.getId().equals(((PatientEntity) o).getId())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = getPatient().getAddress().hashCode() + getPatient().getAge().hashCode();
		result = 31 * result + getPatient().getPrescription().hashCode();
		return result;
	}
}
