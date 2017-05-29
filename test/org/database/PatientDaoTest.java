

import org.database.MongoDao;
import org.database.MongoPatientDao;
import org.database.PatientDao;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.models.Patient;
import org.models.PatientEntity;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoClient;


public class PatientDaoTest {

	static Datastore datastore;
	static PatientDao dao;
	static MongoDao mongoDao;
	@BeforeClass
	public static void initDb() throws Exception {
		mongoDao = Mockito.mock(MongoDao.class);
		Morphia morphia = new Morphia();
		Datastore datastore= morphia.createDatastore(new MongoClient(), "morphia");
		Mockito.when(mongoDao.getDataStore(Mockito.any())).thenReturn(datastore);
		dao= new MongoPatientDao(mongoDao);
	}
	
	@Test
	public void testCreate() throws Exception{
		PatientEntity entity = createPatientEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(PatientEntity.class);
		Assert.assertNotNull(id);
	}
	@Test
	public void testGetUserById() throws Exception{
		PatientEntity entity = createPatientEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(PatientEntity.class);
		Patient pat2 = dao.getByUserId(id).get().getPatient();
		Assert.assertThat(pat2.getName(), CoreMatchers.is("Patient 1"));
		Assert.assertThat(pat2.getMedicalRecord(), CoreMatchers.is("med rec"));
		Assert.assertThat(pat2.getAddress(), CoreMatchers.is("address"));
//		Assert.assertNotNull(object);
	}
	
	@Test
	public void testUpdate() throws Exception{
		PatientEntity entity = createPatientEntity();
		String id = dao.create(entity);
		Mockito.verify(mongoDao).getDataStore(PatientEntity.class);
		PatientEntity patientEntity2 = dao.getByUserId(id).get();
		Patient pat2 = patientEntity2.getPatient();
		Assert.assertThat(pat2.getName(), CoreMatchers.is("Patient 1"));
		pat2.setName("Patient 2");
		dao.update(patientEntity2);
		Assert.assertThat(dao.getByUserId(id).get().getPatient().getName(), CoreMatchers.is("Patient 2"));
	}

	public static PatientEntity createPatientEntity(){
		Patient patient = new Patient();
		patient.setAge(12);
		patient.setMedicalRecord("med rec");
		patient.setName("Patient 1");
		patient.setPrescription("presc");
		patient.setAddress("address");
		PatientEntity entity = new PatientEntity();
		entity.setPatient(patient);
		return entity;
	}
}
