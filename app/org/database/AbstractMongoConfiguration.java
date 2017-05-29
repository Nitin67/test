package org.database;

import com.google.code.morphia.Datastore;
import com.mongodb.Mongo;

public interface AbstractMongoConfiguration {

	public  Mongo mongo() throws Exception;
	<T> Datastore getDataStore(Class<T> cls) throws Exception;
}
