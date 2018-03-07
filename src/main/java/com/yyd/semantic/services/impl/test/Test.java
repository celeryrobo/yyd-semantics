package com.yyd.semantic.services.impl.test;

import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;

@Service
public class Test implements Semantic<TestBean> {
	@Override
	public TestBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		String text = ybnfCompileResult.toString();
		semanticContext.getParams().putAll(ybnfCompileResult.getSlots());
		semanticContext.getParams().putAll(ybnfCompileResult.getObjects());
		TestBean rs = new TestBean();
		rs.setId(1);
		rs.setName(text);
		rs.setErrCode(0);
		return rs;
	}
}
