package org.models;

import java.util.UUID;

public class Doctor extends User {

	final private String docId;
	private String speciality;
	private String degree;
	private Integer consultantFee;
	private Integer registrationId;

	public Doctor() {
		docId = UUID.randomUUID().toString();
	}

	public String getDocId() {
		return docId;
	}

	public Integer getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Integer registrationId) {
		this.registrationId = registrationId;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Integer getConsultantFee() {
		return consultantFee;
	}

	public void setConsultantFee(Integer consultantFee) {
		this.consultantFee = consultantFee;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		if (this.getDocId().equals(((Doctor) o).getDocId())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = getAddress().hashCode();
		result = 31 * result + getRegistrationId().hashCode();
		return result;
	}
}
