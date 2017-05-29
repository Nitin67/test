package org.database;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.models.Patient;
import org.models.PharmacistEntity;
import org.models.Pharmcist;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoClient;

public class PharmacistDaoTest {
	static Datastore datastore;
	static PharmcistDao dao;
	static MongoDao mongoDao;
	
	@BeforeClass
	public static void initDb() throws Exception {
		mongoDao = Mockito.mock(MongoDao.class);
		Morphia morphia = new Morphia();
		Datastore datastore= morphia.createDatastore(new MongoClient(), "morphia");
		Mockito.when(mongoDao.getDataStore(Mockito.any())).thenReturn(datastore);
		dao= new MongoPharmcistDao(mongoDao);
	}
	
	@Test
	public void testCreate() throws Exception{
		PharmacistEntity entity = createPharmacistEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(PharmacistEntity.class);
		Assert.assertNotNull(id);
	}
	@Test
	public void testGetUserById() throws Exception{
		PharmacistEntity entity = createPharmacistEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(PharmacistEntity.class);
		Pharmcist pharmacist2 = dao.getByUserId(id).get().getPharmcist();
		Assert.assertThat(pharmacist2.getName(), CoreMatchers.is("Pharmacist 1"));
		Assert.assertThat(pharmacist2.getLicenceNumber(), CoreMatchers.is("3roq8hw"));
		Assert.assertThat(pharmacist2.getAddress(), CoreMatchers.is("address"));
	}
	
	@Test
	public void testAddAuthorisedUser() throws Exception{
		PharmacistEntity entity = createPharmacistEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(PharmacistEntity.class);
		PharmacistEntity pharmacistEntity2 = dao.getByUserId(id).get();
		Pharmcist pharmacist2 = pharmacistEntity2.getPharmcist();
		Assert.assertThat(pharmacist2.getName(), CoreMatchers.is("Pharmacist 1"));
		Patient patient = createPatient();
		dao.addAuthorisedUser(entity, patient);
		Assert.assertThat(dao.getByUserId(id).get().getAccessiblePatients().get(0).getPatientId(), CoreMatchers.is(patient.getPatientId()));
	}
	@Test
	public void testAddUnauthorisedUser() throws Exception{
		PharmacistEntity entity = createPharmacistEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(PharmacistEntity.class);
		PharmacistEntity pharmacistEntity2 = dao.getByUserId(id).get();
		Pharmcist pharmacist2 = pharmacistEntity2.getPharmcist();
		Assert.assertThat(pharmacist2.getName(), CoreMatchers.is("Pharmacist 1"));
		Patient patient = createPatient();
		dao.addUnauthorisedUser(entity, patient);
		Assert.assertThat(dao.getByUserId(id).get().getDeclinedPrescriptionPatients().get(0).getPatientId(), CoreMatchers.is(patient.getPatientId()));
	}
	@Test
	public void testAddPendingActionUser() throws Exception{
		PharmacistEntity entity = createPharmacistEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(PharmacistEntity.class);
		PharmacistEntity pharmacistEntity2 = dao.getByUserId(id).get();
		Pharmcist pharmacist2 = pharmacistEntity2.getPharmcist();
		Assert.assertThat(pharmacist2.getName(), CoreMatchers.is("Pharmacist 1"));
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
	
	public static PharmacistEntity createPharmacistEntity(){
		Pharmcist pharmcist = new Pharmcist();
		pharmcist.setAge(12);
		pharmcist.setName("Pharmacist 1");
		pharmcist.setLicenceNumber("3roq8hw");
		pharmcist.setAddress("address");
		PharmacistEntity pharmacistEntity = new PharmacistEntity();
		pharmacistEntity.setPharmcist(pharmcist);
		return pharmacistEntity;
	}

}
