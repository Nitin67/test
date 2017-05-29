package org.manager;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import org.database.DoctorDao;
import org.database.MongoDoctorDao;
import org.models.DoctorEntity;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;

public class DoctorAccessImpl implements DoctorAccessInterface{

	private final DoctorDao doctorDao;
	private final ActorSystem actorSystem;
	private final ExecutionContextExecutor exec;
	private final Config config;

	@Inject
	public DoctorAccessImpl(MongoDoctorDao doctorDao, ActorSystem actorSystem, ExecutionContextExecutor exec, Config config) {
		this.doctorDao = doctorDao;
		this.actorSystem = actorSystem;
		this.exec = exec;
		this.config = config;
	}
	
	@Override
	public CompletionStage<String> createDoctor(DoctorEntity doctor){
		CompletableFuture<String> future = new CompletableFuture<>();
		actorSystem.scheduler().scheduleOnce(Duration.create(config.getLong("api.timeout"), TimeUnit.MILLISECONDS), 
				() -> future.complete(doctorDao.create(doctor)), exec);
		return future;
	}
	
	@Override
	public CompletionStage<Optional<DoctorEntity>> getDoc(String userId){
		CompletableFuture<Optional<DoctorEntity>> future = new CompletableFuture<>();
		actorSystem.scheduler().scheduleOnce(Duration.create(config.getLong("api.timeout"), TimeUnit.MILLISECONDS), 
				() -> future.complete(doctorDao.getByUserId(userId)), exec);
		return future;
	}

}
