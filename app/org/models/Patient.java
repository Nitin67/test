package org.models;

import java.util.UUID;

public class Patient extends User {
	
	final private String patientId;
	private String prescription;
	private String medicalRecord;
	
	public Patient() {
		patientId = UUID.randomUUID().toString();
	}
	
	public String getPatientId() {
		return patientId;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getMedicalRecord() {
		return medicalRecord;
	}
	public void setMedicalRecord(String medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		if (this.getPatientId().equals(((Patient) o).getPatientId())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = getAddress().hashCode();
		result = 31 * result + getPrescription().hashCode();
		return result;
	}
}
