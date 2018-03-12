package com.yyd.semantic.common.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ho.yaml.Yaml;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.LuceneCompiler;
import com.yyd.semantic.common.SemanticMatching;
import com.yyd.service.utils.FileUtils;

@Component("SemanticIntention")
public class SemanticIntention implements SemanticMatching<YbnfCompileResult> {
	private final static Map<String, SemanticMatching<YbnfCompileResult>> INTENTIONS = new HashMap<>();
	private static LuceneCompiler luceneCompiler = null;
	private LuceneCompiler compiler = null;

	@SuppressWarnings("unchecked")
	public static void init() throws Exception {
		Map<String, Map<String, List<String>>> sceneIntentTemplates = new HashMap<>();
		String path = FileUtils.getResourcePath() + "semantics/scenes/";
		List<File> files = FileUtils.listFiles(path, ".yml");
		for (File file : files) {
			String name = file.getName().replace(".yml", "");
			Map<String, List<String>> intents = Yaml.loadType(file, HashMap.class);
			sceneIntentTemplates.put(name, intents);
		}
		luceneCompiler = new LuceneCompiler(sceneIntentTemplates);
	}

	public SemanticIntention() throws Exception {
		if (luceneCompiler == null) {
			synchronized (INTENTIONS) {
				if (luceneCompiler == null) {
					init();
				}
			}
		}
	}

	public SemanticIntention(String service) throws Exception {
		this();
		compiler = new LuceneCompiler(service);
	}

	public SemanticMatching<YbnfCompileResult> service(String service) throws Exception {
		SemanticMatching<YbnfCompileResult> matching = INTENTIONS.get(service);
		if (matching == null) {
			synchronized (INTENTIONS) {
				matching = INTENTIONS.get(service);
				if (matching == null) {
					try {
						matching = new SemanticIntention(service);
					} catch (Exception e) {
						matching = new ExternalSemanticIntention(service);
					}
					INTENTIONS.put(service, matching);
				}
			}
		}
		return matching;
	}

	@Override
	public YbnfCompileResult matching(String text) {
		YbnfCompileResult result = null;
		try {
			long startTs = System.currentTimeMillis();
			System.out.println("Text :" + text);
			result = compiler.compile(text);
			System.out.println("Semantic Intention Run Time :" + (System.currentTimeMillis() - startTs) + " Service :"
					+ result.getService());
		} catch (Exception e) {
			System.err.println("语义处理失败！");
		}
		return result;
	}

}
