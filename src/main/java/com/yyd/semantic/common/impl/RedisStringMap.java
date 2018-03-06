package com.yyd.semantic.common.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisStringMap implements Map<Object, Object> {
	private StringRedisTemplate redisTemplate;
	private String redisKeyname;

	public RedisStringMap(StringRedisTemplate redisTemplate, String redisKeyname) {
		this.redisTemplate = redisTemplate;
		this.redisKeyname = redisKeyname;
	}

	@Override
	public int size() {
		Long len = redisTemplate.opsForHash().size(redisKeyname);
		return len.intValue();
	}

	@Override
	public boolean isEmpty() {
		return 0 == size();
	}

	@Override
	public boolean containsKey(Object key) {
		return redisTemplate.opsForHash().hasKey(redisKeyname, key);
	}

	@Override
	public boolean containsValue(Object value) {
		Collection<Object> objs = values();
		for (Object object : objs) {
			if (object.equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object get(Object key) {
		return redisTemplate.opsForHash().get(redisKeyname, key);
	}

	@Override
	public Object put(Object key, Object value) {
		redisTemplate.opsForHash().put(redisKeyname, key, value);
		return value;
	}

	@Override
	public Object remove(Object key) {
		return redisTemplate.opsForHash().delete(redisKeyname, key);
	}

	@Override
	public void putAll(Map<? extends Object, ? extends Object> m) {
		redisTemplate.opsForHash().putAll(redisKeyname, m);
	}

	@Override
	public void clear() {
		Set<Object> keys = keySet();
		if (keys == null || keys.isEmpty()) {
			return;
		}
		redisTemplate.opsForHash().delete(redisKeyname, keys.toArray());
	}

	@Override
	public Set<Object> keySet() {
		return redisTemplate.opsForHash().keys(redisKeyname);
	}

	@Override
	public Collection<Object> values() {
		return redisTemplate.opsForHash().values(redisKeyname);
	}

	@Override
	public Set<Entry<Object, Object>> entrySet() {
		Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKeyname);
		return entries.entrySet();
	}

	@Override
	public String toString() {
		Iterator<Entry<Object, Object>> i = entrySet().iterator();
		if (!i.hasNext())
			return "{}";
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (;;) {
			Entry<Object, Object> e = i.next();
			Object key = e.getKey();
			Object value = e.getValue();
			sb.append(key == this ? "(this Map)" : key);
			sb.append('=');
			sb.append(value == this ? "(this Map)" : value);
			if (!i.hasNext())
				return sb.append('}').toString();
			sb.append(',').append(' ');
		}
	}

}
