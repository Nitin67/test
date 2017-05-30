package org.database;


import java.util.List;
import java.util.Optional;
import play.Logger;
import org.bson.types.ObjectId;
import org.models.DoctorEntity;
import org.models.Patient;

import com.google.code.morphia.Datastore;
import com.google.inject.Inject;

public class MongoDoctorDao implements DoctorDao {

	private final MongoDao mongoDao;
	private final Datastore datastore;

	@Inject
	public MongoDoctorDao(MongoDao dao) throws Exception {
		Logger.info("Creating Mongo Doctor Dao");
		this.mongoDao = dao;
		this.datastore = mongoDao.getDataStore(DoctorEntity.class);
	}

	@Override
	public String create(DoctorEntity doctor) {
		Logger.info("Creating Doc Entity for request: "+ doctor.toString());
		return datastore.save(doctor).getId().toString();
	}

	@Override
	public Optional<DoctorEntity> getByUserId(String userId) {
		Logger.info("Getting Doc Entity for user id: "+ userId);
		return Optional.ofNullable(datastore.get(DoctorEntity.class, new ObjectId(userId)));
	}

	@Override
	public Optional<String> addAuthorisedUser(DoctorEntity doctorEntity, Patient patient) {
		Logger.info("Adding patient to Doc Entity's AuthorisedUser for user id: "+ doctorEntity.getId() + "Patient: " + patient);
		List<Patient> accessiblePatients = doctorEntity.getAccessiblePatients();
		List<Patient> pendingPatients = doctorEntity.getPendingPatients();
		pendingPatients.remove(patient);
		accessiblePatients.add(patient);
		return Optional.ofNullable(datastore.save(doctorEntity).getId().toString());
	}

	@Override
	public Optional<String> addUnauthorisedUser(DoctorEntity doctorEntity, Patient patient) {
		Logger.info("Adding patient to Doc Entity's UnauthorisedUser for user id: "+ doctorEntity.getId() + "Patient: " + patient);
		List<Patient> declinedPrescriptionPatients = doctorEntity.getDeclinedPrescriptionPatients();
		declinedPrescriptionPatients.add(patient);
		List<Patient> pendingPatients = doctorEntity.getPendingPatients();
		pendingPatients.remove(patient);
		return Optional.ofNullable(datastore.save(doctorEntity).getId().toString());
	}

	@Override
	public Optional<String> addPendingActionUser(DoctorEntity doctorEntity, Patient patient) {
		Logger.info("Adding patient to Doc Entity's PendingActionUser for user id: "+ doctorEntity.getId() + "Patient: " + patient);
		List<Patient> pendingPatients = doctorEntity.getPendingPatients();
		pendingPatients.add(patient);
		return Optional.ofNullable(datastore.save(doctorEntity).getId().toString());

	}

}
