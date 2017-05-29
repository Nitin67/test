package org.manager;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.models.DoctorEntity;

public interface DoctorAccessInterface {

	CompletionStage<String> createDoctor(DoctorEntity doctor);
	
	CompletionStage<Optional<DoctorEntity>> getDoc(String userId);
	
	
}
