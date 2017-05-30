package org.database;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.models.PatientEntity;

import com.google.code.morphia.Datastore;
import com.google.inject.Inject;

import play.Logger;

public class MongoPatientDao implements PatientDao {

	private final MongoDao mongoDao;
	private final Datastore datastore;
	

	@Inject
	public MongoPatientDao(MongoDao dao) throws Exception {
		this.mongoDao = dao;
		this.datastore = mongoDao.getDataStore(PatientEntity.class);
	}

	@Override
	public String create(PatientEntity patient) {
		Logger.info("Creating Patient Entity for request: "+ patient.toString());
		return datastore.save(patient).getId().toString();
	}

	@Override
	public Optional<PatientEntity> getByUserId(String userId) {
		Logger.info("Getting Patient Entity for user id: "+ userId);
		return Optional.ofNullable(datastore.get(PatientEntity.class, new ObjectId(userId)));
	}

	@Override
	public Optional<String> update(PatientEntity patient){
		Logger.info("Updating Patient Entity for user id: "+ patient.getId());
		return Optional.ofNullable(datastore.save(patient).getId().toString());
	}

}
