package com.yyd.semantic.common.impl;

import org.springframework.stereotype.Component;

import com.ybnf.compiler.ICompiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.MITIECompiler;
import com.yyd.semantic.common.SemanticMatching;

@Component("SemanticScene")
public class SemanticScene implements SemanticMatching<String> {
	private static ICompiler compiler = null;

	public SemanticScene() throws Exception {
		if (compiler == null) {
			compiler = new MITIECompiler("", "", "");
		}
	}

	@Override
	public String matching(String text) {
		YbnfCompileResult result = null;
		try {
			long startTs = System.currentTimeMillis();
			System.out.println("Text :" + text);
			result = compiler.compile(text);
			System.out.println("Semantic Scene Run Time :" + (System.currentTimeMillis() - startTs) + " Service :"
					+ result.getService());
			if (result.getSlots().containsKey("service")) {
				result.setService(result.getSlots().get("service"));
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return result == null ? null : result.getService();
	}

}
