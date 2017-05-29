package org.database;

import java.util.Optional;

import org.models.PatientEntity;

public interface PatientDao {
	
	String create(PatientEntity patient);
	
	Optional<PatientEntity> getByUserId(String userId);
	
	Optional<String> update(PatientEntity patient);
	
	
}
