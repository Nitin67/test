package org.manager;

import java.util.concurrent.CompletionStage;

public interface ErrorInterface {
	CompletionStage<String> getError(String errorType, String errorMsg);
}
