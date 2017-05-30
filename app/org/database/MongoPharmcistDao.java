package org.database;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.models.Patient;
import org.models.PharmacistEntity;

import com.google.code.morphia.Datastore;
import com.google.inject.Inject;

import play.Logger;

public class MongoPharmcistDao implements PharmcistDao {

	private final MongoDao mongoDao;
	private final Datastore datastore;

	@Inject
	public MongoPharmcistDao(MongoDao dao) throws Exception {
		this.mongoDao = dao;
		this.datastore = mongoDao.getDataStore(PharmacistEntity.class);
	}

	@Override
	public String create(PharmacistEntity pharmiest) {
		Logger.info("Creating Pharmacist Entity for request: "+ pharmiest.toString());
		return datastore.save(pharmiest).getId().toString();
	}

	@Override
	public Optional<PharmacistEntity> getByUserId(String userId) {
		Logger.info("Getting Pharmacist Entity for user id: "+ userId);
		return Optional.ofNullable(datastore.get(PharmacistEntity.class, new ObjectId(userId)));
	}

	@Override
	public Optional<String> addAuthorisedUser(PharmacistEntity pharmacistEntity, Patient patient) {
		Logger.info("Adding patient to Pharmacist Entity's AuthorisedUser for user id: "+ pharmacistEntity.getId() + "Patient: " + patient);
		List<Patient> accessiblePatients = pharmacistEntity.getAccessiblePatients();
		accessiblePatients.add(patient);
		List<Patient> pendingPatients = pharmacistEntity.getPendingPatients();
		pendingPatients.remove(patient);
		return Optional.ofNullable(datastore.save(pharmacistEntity).getId().toString());

	}

	@Override
	public Optional<String> addUnauthorisedUser(PharmacistEntity pharmacistEntity, Patient patient) {
		Logger.info("Adding patient to Pharmacist Entity's UnauthorisedUser for user id: "+ pharmacistEntity.getId() + "Patient: " + patient);
		List<Patient> declinedPrescriptionPatients = pharmacistEntity.getDeclinedPrescriptionPatients();
		declinedPrescriptionPatients.add(patient);
		List<Patient> pendingPatients = pharmacistEntity.getPendingPatients();
		pendingPatients.remove(patient);
		return Optional.ofNullable(datastore.save(pharmacistEntity).getId().toString());
	}

	@Override
	public Optional<String> addPendingActionUser(PharmacistEntity pharmacistEntity, Patient patient) {
		Logger.info("Adding patient to Pharmacist Entity's PendingActionUser for user id: "+ pharmacistEntity.getId() + "Patient: " + patient);
		List<Patient> pendingPatients = pharmacistEntity.getPendingPatients();
		pendingPatients.add(patient);
		return Optional.ofNullable(datastore.save(pharmacistEntity).getId().toString());

	}

}
