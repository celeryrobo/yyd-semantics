package com.yyd.semantic.common.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.ICompiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.MITIECompiler;
import com.yyd.semantic.common.SemanticMatching;

@Component("SemanticScene")
public class SemanticScene implements SemanticMatching<String> {
	private static final Logger LOG = Logger.getLogger(SemanticScene.class.getSimpleName());
	private static ICompiler compiler = null;

	public SemanticScene(@Value("${mitie.feature.filename}") String feature,
			@Value("${mitie.category.filename}") String category) throws Exception {
		if (compiler == null) {
			compiler = new MITIECompiler(category, feature);
		}
	}

	@Override
	public String matching(String text) {
		YbnfCompileResult result = null;
		try {
			long startTs = System.currentTimeMillis();
			LOG.info("Text :" + text);
			result = compiler.compile(text);
			LOG.info("Semantic Scene Run Time :" + (System.currentTimeMillis() - startTs) + " Service :"
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
