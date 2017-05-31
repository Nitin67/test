package org.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.PrePersist;

@Entity("doctor")
public class DoctorEntity {

	@Id
	@Indexed(unique=true, dropDups=true)
	private ObjectId id;
	private Doctor doctor;
	private List<Patient> accessiblePatients;
	private List<Patient> declinedPrescriptionPatients;
	private List<Patient> pendingPatients;
	private Date updated;
	private Date created;
	public DoctorEntity() {
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

	public Doctor getDoctor() {
		return doctor;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public List<Patient> getAccessiblePatients() {
		if(accessiblePatients == null){
			accessiblePatients= new ArrayList<Patient>();
		}
		return accessiblePatients;
	}
	public void setAccessiblePatients(List<Patient> accessiblePatients) {
		this.accessiblePatients = accessiblePatients;
	}
	public List<Patient> getDeclinedPrescriptionPatients() {
		if(declinedPrescriptionPatients == null){
			declinedPrescriptionPatients = new ArrayList<Patient>();
		}
		return declinedPrescriptionPatients;
	}
	public void setDeclinedPrescriptionPatients(List<Patient> declinedPrescriptionPatients) {
		this.declinedPrescriptionPatients = declinedPrescriptionPatients;
	}
	public List<Patient> getPendingPatients() {
		if(pendingPatients == null){
			pendingPatients = new ArrayList<Patient>();
		}
		return pendingPatients;
	}
	public void setPendingPatients(List<Patient> pendingPatients) {
		this.pendingPatients = pendingPatients;
	}
	
	 @Override
	    public boolean equals(final Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }

	       if(this.getId().equals(((DoctorEntity)o).getId())){
	        return true;
	       }
	       return false;
	    }

	    @Override
	    public int hashCode() {
	        int result = getDoctor().getAddress().hashCode();
	        result = 31 * result + getDoctor().getRegistrationId().hashCode();
	        return result;
	    }
}
