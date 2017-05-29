package org.database;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.models.Doctor;
import org.models.DoctorEntity;
import org.models.Patient;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoClient;

public class DoctorDaoTest {
	static Datastore datastore;
	static DoctorDao dao;
	static MongoDao mongoDao;
	
	@BeforeClass
	public static void initDb() throws Exception {
		mongoDao = Mockito.mock(MongoDao.class);
		Morphia morphia = new Morphia();
		Datastore datastore= morphia.createDatastore(new MongoClient(), "morphia");
		Mockito.when(mongoDao.getDataStore(Mockito.any())).thenReturn(datastore);
		dao= new MongoDoctorDao(mongoDao);
	}
	
	@Test
	public void testCreate() throws Exception{
		DoctorEntity entity = createDoctorEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(DoctorEntity.class);
		Assert.assertNotNull(id);
	}
	@Test
	public void testGetUserById() throws Exception{
		DoctorEntity entity = createDoctorEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(DoctorEntity.class);
		Doctor doc2 = dao.getByUserId(id).get().getDoctor();
		Assert.assertThat(doc2.getName(), CoreMatchers.is("Doc 1"));
		Assert.assertThat(doc2.getRegistrationId(), CoreMatchers.is(243234));
		Assert.assertThat(doc2.getAddress(), CoreMatchers.is("address"));
	}
	
	@Test
	public void testAddAuthorisedUser() throws Exception{
		DoctorEntity entity = createDoctorEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(DoctorEntity.class);
		DoctorEntity doctorEntity2 = dao.getByUserId(id).get();
		Doctor doc2 = doctorEntity2.getDoctor();
		Assert.assertThat(doc2.getName(), CoreMatchers.is("Doc 1"));
		Patient patient = createPatient();
		dao.addAuthorisedUser(entity, patient);
		Assert.assertThat(dao.getByUserId(id).get().getAccessiblePatients().get(0).getPatientId(), CoreMatchers.is(patient.getPatientId()));
	}
	@Test
	public void testAddUnauthorisedUser() throws Exception{
		DoctorEntity entity = createDoctorEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(DoctorEntity.class);
		DoctorEntity doctorEntity2 = dao.getByUserId(id).get();
		Doctor doc2 = doctorEntity2.getDoctor();
		Assert.assertThat(doc2.getName(), CoreMatchers.is("Doc 1"));
		Patient patient = createPatient();
		dao.addUnauthorisedUser(entity, patient);
		Assert.assertThat(dao.getByUserId(id).get().getDeclinedPrescriptionPatients().get(0).getPatientId(), CoreMatchers.is(patient.getPatientId()));
	}
	@Test
	public void testAddPendingActionUser() throws Exception{
		DoctorEntity entity = createDoctorEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(DoctorEntity.class);
		DoctorEntity doctorEntity2 = dao.getByUserId(id).get();
		Doctor doc2 = doctorEntity2.getDoctor();
		Assert.assertThat(doc2.getName(), CoreMatchers.is("Doc 1"));
		Patient patient = createPatient();
		dao.addPendingActionUser(entity, patient);
		Assert.assertThat(dao.getByUserId(id).get().getPendingPatients().get(0).getPatientId(), CoreMatchers.is(patient.getPatientId()));
	}

	public static Patient createPatient(){
		Patient patient = new Patient();
		patient.setAge(12);
		patient.setMedicalRecord("med rec");
		patient.setName("Patient 1");
		patient.setPrescription("presc");
		patient.setAddress("address");
		
		return patient;
	}
	
	public static DoctorEntity createDoctorEntity(){
		Doctor doc = new Doctor();
		doc.setAge(12);
		doc.setName("Doc 1");
		doc.setRegistrationId(243234);
		doc.setAddress("address");
		DoctorEntity doctorEntity = new DoctorEntity();
		doctorEntity.setDoctor(doc);
		return doctorEntity;
	}

}
