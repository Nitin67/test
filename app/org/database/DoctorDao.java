package org.database;

import java.util.Optional;

import org.models.DoctorEntity;
import org.models.Patient;

public interface DoctorDao{
	
	String create(DoctorEntity doctor);
	
	Optional<DoctorEntity> getByUserId(String userId);

	Optional<String> addAuthorisedUser(DoctorEntity doctorEntity, Patient patient);
	
	Optional<String> addUnauthorisedUser(DoctorEntity doctorEntity, Patient patient);
	
	Optional<String> addPendingActionUser(DoctorEntity doctorEntity, Patient patient);
}
