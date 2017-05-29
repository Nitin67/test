package org.manager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.Optional;
import org.database.MongoPharmcistDao;
import org.database.PharmcistDao;
import org.models.PharmacistEntity;
import com.google.inject.Inject;
import akka.actor.ActorSystem;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;
import scala.concurrent.ExecutionContextExecutor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class PharmcistAccessImpl implements PharmcistAccessInterface{
	
	private final PharmcistDao pharmcistDao;
	private final ActorSystem actorSystem;
	private final ExecutionContextExecutor exec;
	private final Config config;
	
	@Inject
	public PharmcistAccessImpl(MongoPharmcistDao pharmcistDao, ActorSystem actorSystem, ExecutionContextExecutor exec, Config config) {
		this.pharmcistDao = pharmcistDao;
		this.actorSystem = actorSystem;
		this.exec = exec;
		this.config = config;
	}
	@Override
	public CompletionStage<String> createPharmcist(PharmacistEntity pharmcist) {
		CompletableFuture<String> future = new CompletableFuture<>();
		actorSystem.scheduler().scheduleOnce(Duration.create(config.getLong("api.timeout"), TimeUnit.MILLISECONDS), () -> future.complete(pharmcistDao.create(pharmcist)), exec);
		return future;
	}

	@Override
	public CompletionStage<Optional<PharmacistEntity>> getPharmcist(String userId){
		CompletableFuture<Optional<PharmacistEntity>> future = new CompletableFuture<>();
		actorSystem.scheduler().scheduleOnce(Duration.create(config.getLong("api.timeout"), TimeUnit.MILLISECONDS), () -> future.complete(pharmcistDao.getByUserId(userId)), exec);
		return future;
	}
}
