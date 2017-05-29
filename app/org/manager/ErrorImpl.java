package org.manager;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import com.google.inject.Inject;
import akka.actor.ActorSystem;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;
import scala.concurrent.ExecutionContextExecutor;
public class ErrorImpl implements ErrorInterface{

	private final ActorSystem actorSystem;
	private final ExecutionContextExecutor exec;

	@Inject
	public ErrorImpl(ActorSystem actorSystem, ExecutionContextExecutor exec) {
		this.actorSystem = actorSystem;
		this.exec = exec;
	}
	@Override
	public CompletionStage<String> getError(String errorType, String errorMsg) {
		CompletableFuture<String> future = new CompletableFuture<>();
		actorSystem.scheduler().scheduleOnce(Duration.create(1000L, TimeUnit.MILLISECONDS), () -> future.complete(errorType + ": " + errorMsg), exec);
		return future;
	}

}
