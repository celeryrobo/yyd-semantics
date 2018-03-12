package com.yyd.semantic.common.impl;

import java.util.HashMap;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.yyd.semantic.common.SemanticMatching;

public class ExternalSemanticIntention implements SemanticMatching<YbnfCompileResult> {
	private String service;

	public ExternalSemanticIntention(String service) {
		this.service = service;
	}

	@Override
	public YbnfCompileResult matching(String text) {
		return new YbnfCompileResult(text, "0.1", "UTF8", service, new HashMap<>(), new HashMap<>());
	}
}
