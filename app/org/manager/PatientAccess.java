package org.manager;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.models.PatientEntity;
import org.request.AccessPermissionRequest;
import org.request.RequestValue;

public interface PatientAccess {

	CompletionStage<Optional<String>> requestPatientPrescriptionAccess(AccessPermissionRequest accessPermissionRequest) throws IOException;
	
	CompletionStage<Optional<String>> updatePrescriptionStatus(String patientId, RequestValue authorisedUser,
	RequestValue unauthorisedUser) throws IOException;
	
	CompletionStage<String> createPatient(PatientEntity patient);
	CompletionStage<Optional<PatientEntity>> getPatient(String userId);
}
