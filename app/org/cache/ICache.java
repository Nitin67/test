package org.cache;

import java.io.IOException;

public interface ICache {

	public void set(String key, Object value) throws IOException;
	public void set(String key, Object value, Integer ttl) throws IOException;
	public Object get(String key) throws IOException;
	public Object delete(String key) throws IOException;
}
