package com.yyd.semantic.common;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.semantic.Semantic;

@Component
public class SemanticFactory {
	private Properties properties;
	@Autowired
	private SpringContext springContext;

	public SemanticFactory() throws Exception {
		properties = FileUtils.buildProperties("semantics/semantic.properties");
	}

	public Semantic<?> build(String serviceName) throws Exception {
		Semantic<?> semantic = null;
		if (semantic == null) {
			String className = properties.getProperty(serviceName);
			Class<?> clazz = Class.forName(className);
			semantic = (Semantic<?>) springContext.getBean(clazz);
		}
		return semantic;
	}
}
