package controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import play.api.Play;
import play.api.http.HttpErrorHandlerExceptions;
import play.libs.Json;
import javax.inject.Singleton;
import javax.ws.rs.core.UriInfo;

import org.apache.http.RequestLine;
import org.database.MongoDao;
import org.manager.DoctorAccessImpl;
import org.manager.DoctorAccessInterface;
import org.manager.ErrorImpl;
import org.manager.ErrorInterface;
import org.manager.PatientAccess;
import org.manager.PatientAccessImpl;
import org.manager.PharmcistAccessImpl;
import org.manager.PharmcistAccessInterface;
import org.models.Doctor;
import org.models.DoctorEntity;
import org.models.Patient;
import org.models.PatientEntity;
import org.models.PharmacistEntity;
import org.models.Pharmcist;
import org.request.AccessPermissionRequest;
import org.request.RequestList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.Logger;
import scala.concurrent.ExecutionContextExecutor;

@Singleton
public class PharmController extends Controller {

	final ObjectMapper mapper = new ObjectMapper();
	private final MongoDao mongoDao;
	private final ExecutionContextExecutor exec;
	private final PatientAccess patientAccess;
	private final ErrorInterface errorInterface;
	private final DoctorAccessInterface doctorAccess;
	private final PharmcistAccessInterface pharmcistAccess;

	@Inject
	public PharmController(ExecutionContextExecutor exec, PatientAccessImpl patientAccess, ErrorImpl errorImpl,
			DoctorAccessImpl doctorAccessImpl, PharmcistAccessImpl pharmcistAccessImpl, MongoDao dao) {
		this.exec = exec;
		this.errorInterface = errorImpl;
		this.patientAccess = patientAccess;
		this.doctorAccess = doctorAccessImpl;
		this.pharmcistAccess = pharmcistAccessImpl;
		mongoDao = dao;

	}

	public CompletionStage<Result> createPatient() {
		JsonNode node = request().body().asJson();
		Logger.info("Creating Patient Entity for json: "+ node.toString());
		PatientEntity patient = null;
		try {
			patient = mapper.treeToValue(node, PatientEntity.class);
		} catch (JsonProcessingException e) {
			return errorInterface.getError("BadRequest", e.getMessage()).thenApplyAsync(Results::badRequest, exec);
		}
		CompletionStage<String> res = patientAccess.createPatient(patient);
		return res.thenApplyAsync(resource -> created(Json.toJson(resource)), exec);
	}

	public CompletionStage<Result> createDoctor() {
		JsonNode node = request().body().asJson();
		Logger.info("Creating Doc Entity for json: "+ node.toString());
		DoctorEntity doctor = null;
		try {
			doctor = mapper.treeToValue(node, DoctorEntity.class);
		} catch (Exception e) {
			return errorInterface.getError("BadRequest", e.getMessage()).thenApplyAsync(Results::badRequest, exec);
		}
		CompletionStage<String> res = doctorAccess.createDoctor(doctor);
		return res.thenApplyAsync(resource -> created(Json.toJson(resource)), exec);
	}

	public CompletionStage<Result> createPharmcist() {
		JsonNode node = request().body().asJson();
		Logger.info("Creating Pharmcist Entity for json: "+ node.toString());
		PharmacistEntity pharmcist = null;
		try {
			pharmcist = mapper.treeToValue(node, PharmacistEntity.class);
		} catch (JsonProcessingException e) {
			Logger.error("JsonProcessingException for request: " + node.toString()+ " with error mssg: " + e.getMessage());
			return errorInterface.getError("BadRequest", e.getMessage()).thenApplyAsync(Results::badRequest, exec);
		}
		CompletionStage<String> res = pharmcistAccess.createPharmcist(pharmcist);
		return res.thenApplyAsync(resource -> created(Json.toJson(resource)), exec);
	}

	public CompletionStage<Result> findDoc(String id) {
		Logger.info("Getting Doc Entity for id: "+ id);
		CompletionStage<Optional<DoctorEntity>> res = doctorAccess.getDoc(id);
		return res.thenApplyAsync(optionalResource -> {
			return optionalResource.map(resource -> ok(Json.toJson(resource))).orElseGet(() -> notFound());
		}, exec);
	}

	public CompletionStage<Result> findPatient(String id) {
		Logger.info("Getting patient Entity for id: "+ id);
		CompletionStage<Optional<PatientEntity>> res = patientAccess.getPatient(id);
		return res.thenApplyAsync(optionalResource -> {
			return optionalResource.map(resource -> ok(Json.toJson(resource))).orElseGet(() -> notFound());
		}, exec);
	}

	public CompletionStage<Result> findPharmcist(String id) {
		Logger.info("Getting Pharmacist Entity for id: "+ id);
		CompletionStage<Optional<PharmacistEntity>> res = pharmcistAccess.getPharmcist(id);
		return res.thenApplyAsync(optionalResource -> {
			return optionalResource.map(resource -> ok(Json.toJson(resource))).orElseGet(() -> notFound());
		}, exec);
	}

	public CompletionStage<Result> requestPatientPrescriptionAccess(String id) {
		JsonNode node = request().body().asJson();
		Logger.info("Requesting User Entity for id: "+ id + "for patient access" + node.toString());
		AccessPermissionRequest perRequest = null;
		try {
			perRequest = mapper.treeToValue(node, AccessPermissionRequest.class);
			perRequest.setPatientId(id);
			CompletionStage<Optional<String>> res = patientAccess.requestPatientPrescriptionAccess(perRequest);
			return res.thenApplyAsync(optionalResource -> {
				return optionalResource.map(resource -> created(Json.toJson(resource))).orElseGet(() -> notFound());
			}, exec);
		} catch (JsonProcessingException e) {
			Logger.error("JsonProcessingException for request: " + node.toString() + " with error mssg: " + e.getMessage());
			return errorInterface.getError("BadRequest", e.getMessage()).thenApplyAsync(Results::badRequest, exec);
		} catch (IOException e) {
			Logger.error("IOException for request: " + node.toString() + " with error mssg: " + e.getMessage());
			return errorInterface.getError("Internal ServerError", e.getMessage()).thenApplyAsync(Results::internalServerError,
					exec);
		}catch (NoSuchElementException e) {
			Logger.error("NoSuchElementException for request: " + node.toString() + " with error mssg: " + e.getMessage());
			return errorInterface.getError("BadRequest", e.getMessage()).thenApplyAsync(Results::notFound,
					exec);
		}
	}

	public CompletionStage<Result> updatePrescriptionStatus(String id) {
		JsonNode node = request().body().asJson();
		Logger.info("Updatind User Entity for id: "+ id + " for data: " + node.toString());
		RequestList list = null;

		try {
			list = mapper.treeToValue(node, RequestList.class);
			CompletionStage<Optional<String>> res = patientAccess.updatePrescriptionStatus(id, list.getAuthorised(),
					list.getUnAuthorised());
			return res.thenApplyAsync(optionalResource -> {
				return optionalResource.map(resource -> ok(Json.toJson(resource))).orElseGet(() -> notFound());
			}, exec);
		} catch (JsonProcessingException e) {
			Logger.error("JsonProcessingException for request: " + node.toString() + " with error mssg: " + e.getMessage());
			return errorInterface.getError("BadRequest", e.getMessage()).thenApplyAsync(Results::badRequest, exec);
		} catch (IOException e) {
			Logger.error("IOException for request: " + node.toString() + " with error mssg: " + e.getMessage());
			return errorInterface.getError("BadRequest", e.getMessage()).thenApplyAsync(Results::internalServerError,
					exec);
		}

	}

}
