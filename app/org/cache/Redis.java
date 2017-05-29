package org.cache;

import java.io.IOException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis implements ICache {

	private JedisPool redisConnectionPool;
	
	public Redis(String hostAddress) {
//		String[] host = hostAddress.split(":");
		redisConnectionPool = new JedisPool("localhost");
	}

	protected Jedis getConnetion() {
		return redisConnectionPool.getResource();
	}

	@Override
	public void set(String key, Object value) throws IOException {
		Jedis jedis = getConnetion();
		try {
			byte[] rawData = Serializer.serialize(value);
			jedis.set(key.getBytes(), rawData);
		} finally {
			jedis.close();
		}

	}

	@Override
	public void set(String key, Object value, Integer TTL) throws IOException {
		Jedis jedis = getConnetion();
		try {
			byte[] rawData = Serializer.serialize(value);
			jedis.setex(key.getBytes(), TTL, rawData);
		} finally {
			jedis.close();
		}

	}

	@Override
	public Object get(String key) throws IOException {
		Jedis jedis = getConnetion();
		Object obj = null;
		try {
			String val = jedis.get(key);
			if (val != null) {
				obj = Serializer.deserialize(val.getBytes("ISO-8859-1"));
				/*
				 * if (obj == null) obj = val;
				 */ // Case of String. TODO: better way of handling this.
			}
		} finally {
			jedis.close();
		}
		return obj;
	}

	@Override
	public Object delete(String key) throws IOException {
		Object val = null;
		Jedis jedis = getConnetion();
		try {
			val = jedis.get(key);
			jedis.del(key);
			
		} finally {
			jedis.close();
		}
		return val;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		if (redisConnectionPool != null) {
			redisConnectionPool.destroy();
		}
	}

}
