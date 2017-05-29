package org.manager;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.models.PharmacistEntity;

public interface PharmcistAccessInterface {

	CompletionStage<String> createPharmcist(PharmacistEntity pharmcist);
	
	CompletionStage<Optional<PharmacistEntity>> getPharmcist(String userId);
}
