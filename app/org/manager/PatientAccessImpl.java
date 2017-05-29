package org.manager;

import org.models.Role;
import org.models.Doctor;
import org.models.DoctorEntity;
import org.models.Pharmcist;
import org.models.PharmacistEntity;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.database.DoctorDao;
import org.database.MongoDoctorDao;
import org.database.PharmcistDao;
import org.database.MongoPharmcistDao;
import org.models.PatientEntity;
import org.request.AccessPermissionRequest;
import org.request.RequestValue;
import org.database.MongoPatientDao;
import org.database.PatientDao;
import com.google.inject.Inject;
import akka.actor.ActorSystem;
import java.io.IOException;
import java.util.Optional;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;
import scala.concurrent.ExecutionContextExecutor;
import java.lang.IllegalArgumentException;

public class PatientAccessImpl implements PatientAccess {

	private final PatientDao patientDao;
	private final DoctorDao doctorDao;
	private final PharmcistDao pharmcistDao;
	private final ActorSystem actorSystem;
	private final ExecutionContextExecutor exec;
	private final Config config;

	@Inject
	public PatientAccessImpl(MongoPatientDao patientDao, ActorSystem actorSystem, MongoDoctorDao doctorDao,
			MongoPharmcistDao pharmcistDao, ExecutionContextExecutor exec, Config config) {
		this.patientDao = patientDao;
		this.doctorDao = doctorDao;
		this.pharmcistDao = pharmcistDao;
		this.actorSystem = actorSystem;
		this.exec = exec;
		this.config = config;
	}

	@Override
	public CompletionStage<String> createPatient(PatientEntity patient) {
		
		CompletableFuture<String> future = new CompletableFuture<>();
		actorSystem.scheduler().scheduleOnce(Duration.create(config.getLong("api.timeout"), TimeUnit.MILLISECONDS), () -> future.complete(patientDao.create(patient)), exec);
		return future;
	}

	@Override
	public CompletionStage<Optional<PatientEntity>> getPatient(String userId){
		CompletableFuture<Optional<PatientEntity>> future = new CompletableFuture<>();
		actorSystem.scheduler().scheduleOnce(Duration.create(config.getLong("api.timeout"), TimeUnit.MILLISECONDS), ()
				-> future.complete(patientDao.getByUserId(userId)), exec);
		return future;
	}
	@Override
	public CompletionStage<Optional<String>> requestPatientPrescriptionAccess(AccessPermissionRequest accessPermissionRequest) throws IOException{
		CompletableFuture<Optional<String>> future = new CompletableFuture<>();		
		PatientEntity patient = patientDao.getByUserId(accessPermissionRequest.getPatientId()).get();
		if(accessPermissionRequest.getRole().equals(Role.Doctor)){
			DoctorEntity doc = doctorDao.getByUserId(accessPermissionRequest.getRequestedUserId()).get();
			doctorDao.addPendingActionUser(doc, patient.getPatient());
			patient.getRequestPendingDoctors().add(doc.getDoctor());
		}else if(accessPermissionRequest.getRole().equals(Role.Pharmiest)){
			PharmacistEntity pharmacistEntity = pharmcistDao.getByUserId(accessPermissionRequest.getRequestedUserId()).get();
			pharmcistDao.addPendingActionUser(pharmacistEntity,patient.getPatient());
			patient.getRequestPendingPharmcists().add(pharmacistEntity.getPharmcist());
		}else{
			throw new IllegalArgumentException("Illegal role");
		}
		actorSystem.scheduler().scheduleOnce(Duration.create(config.getLong("api.timeout"), TimeUnit.MILLISECONDS), ()
				-> future.complete(patientDao.update(patient)), exec);
		return future;
	}
	 
	@Override
	public CompletionStage<Optional<String>> updatePrescriptionStatus(String patientId, RequestValue authorisedUser,
			RequestValue unauthorisedUser) throws IOException{
		
		CompletableFuture<Optional<String>> future = new CompletableFuture<>();
		PatientEntity patient = patientDao.getByUserId(patientId).get();
		for (String pdr : authorisedUser.getDoctorRequests()) {
			DoctorEntity doctorEntity = doctorDao.getByUserId(pdr).get();
			Doctor doctor = doctorEntity.getDoctor();
			patient.getAuthorisedDoctors().add(doctor);
			patient.getRequestPendingDoctors().remove(doctor);
			doctorDao.addAuthorisedUser(doctorEntity, patient.getPatient());
		}	
		for (String pdr : unauthorisedUser.getDoctorRequests()) {
			DoctorEntity doctorEntity = doctorDao.getByUserId(pdr).get();
			Doctor doctor = doctorEntity.getDoctor();
			patient.getRequestPendingDoctors().remove(doctor);
			doctorDao.addUnauthorisedUser(doctorEntity, patient.getPatient());
		}
		for (String pdr : authorisedUser.getPharmcistRequests()) {
			PharmacistEntity pharmacistEntity= pharmcistDao.getByUserId(pdr).get();
			Pharmcist pharmcist = pharmacistEntity.getPharmcist();
			patient.getAuthorisedPharmcists().add(pharmcist);
			patient.getRequestPendingPharmcists().remove(pharmcist);
			pharmcistDao.addAuthorisedUser(pharmacistEntity, patient.getPatient());
		}	
		for (String pdr : unauthorisedUser.getPharmcistRequests()) {
			PharmacistEntity pharmacistEntity= pharmcistDao.getByUserId(pdr).get();
			Pharmcist pharmcist = pharmacistEntity.getPharmcist();
			patient.getRequestPendingPharmcists().remove(pharmcist);
			pharmcistDao.addUnauthorisedUser(pharmacistEntity, patient.getPatient());
		}
		actorSystem.scheduler().scheduleOnce(Duration.create(config.getLong("api.timeout"), TimeUnit.MILLISECONDS), ()
				-> future.complete(patientDao.update(patient)), exec);
		return future;
	}
	
	
}
