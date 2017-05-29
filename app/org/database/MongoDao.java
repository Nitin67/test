package org.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.typesafe.config.Config;

@Configuration
@Singleton
@PropertySource("classpath:${pharm}.mongodb.properties")
public class MongoDao implements AbstractMongoConfiguration{

	@Inject
	Config config;
	@Inject
	Mongo mongo;
	@Inject
	Morphia morphia;
	
	@Override
	public <T> Datastore getDataStore(Class<T> cls) throws Exception {
		String dbName = config.getString("mongo.config.db_name");
		Datastore datastore= morphia.createDatastore(mongo(), dbName);
		morphia.map(cls);
		return datastore;
	}

	@Override
	public Mongo mongo() throws Exception {
		Mongo mongo = new MongoClient();
		play.Logger.info("Initialized mongo bean");
		return mongo;
	}	
}
