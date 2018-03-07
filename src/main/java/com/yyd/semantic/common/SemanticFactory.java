package com.yyd.semantic.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.semantic.Semantic;

@Component
public class SemanticFactory {
	@Autowired
	private SpringContext springContext;

	public Semantic<?> build(String serviceName) throws Exception {
		Semantic<?> semantic = (Semantic<?>) springContext.getBean(serviceName, Semantic.class);
		if (semantic == null) {
			semantic = (Semantic<?>) springContext.getBean("common", Semantic.class);
		}
		return semantic;
	}
}
