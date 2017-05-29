package org.models;

import java.util.UUID;

public class Pharmcist extends User{

	final private String pharmcistId;
	private String degree;
	private float drugKnowledgePercentile;
	private String licenceNumber;
	
	public Pharmcist() {
		pharmcistId = UUID.randomUUID().toString();
	}
	public String getPharmcistId() {
		return pharmcistId;
	}
	public String getLicenceNumber() {
		return licenceNumber;
	}
	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public float getDrugKnowledgePercentile() {
		return drugKnowledgePercentile;
	}
	public void setDrugKnowledgePercentile(float drugKnowledgePercentile) {
		this.drugKnowledgePercentile = drugKnowledgePercentile;
	}
	
	 @Override
	    public boolean equals(final Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }

	       if(this.getPharmcistId().equals(((Pharmcist)o).getPharmcistId())){
	        return true;
	       }
	       return false;
	    }

	    @Override
	    public int hashCode() {
	        int result =getAddress().hashCode();
	        result = 31 * result + getLicenceNumber().hashCode();
	        return result;
	    }
}
