package org.database;

import java.util.Optional;

import org.models.Patient;
import org.models.PharmacistEntity;

public interface PharmcistDao {

	String create(PharmacistEntity pharmiest);
	
	Optional<PharmacistEntity> getByUserId(String userId);
	
	Optional<String> addAuthorisedUser(PharmacistEntity pharmacistEntity, Patient patient);
	
	Optional<String> addUnauthorisedUser(PharmacistEntity pharmacistEntity, Patient patient);
	
	Optional<String> addPendingActionUser(PharmacistEntity pharmacistEntity, Patient patient);
}
