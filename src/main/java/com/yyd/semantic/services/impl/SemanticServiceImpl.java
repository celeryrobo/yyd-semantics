package com.yyd.semantic.services.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.AbstractSemanticResult;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.SemanticFactory;
import com.yyd.semantic.common.SemanticResult;
import com.yyd.semantic.common.impl.SemanticIntention;
import com.yyd.semantic.common.impl.SemanticScene;
import com.yyd.semantic.common.impl.WaringSemanticResult;
import com.yyd.semantic.services.SemanticService;
import com.yyd.service.domain.SemanticPostEntity;

@Service
@Scope("prototype")
public class SemanticServiceImpl implements SemanticService {
	@Autowired
	private SemanticFactory semanticFactory;
	@Autowired
	private SemanticContext semanticContext;
	@Resource(name = "SemanticScene")
	private SemanticScene semanticScene;
	@Resource(name = "SemanticIntention")
	private SemanticIntention semanticIntention;

	@Override
	public SemanticResult handleSemantic(SemanticPostEntity postEntity) throws Exception {
		String lang = postEntity.getLang();
		String userIdentify = postEntity.getUserIdentify();
		semanticContext.setLocalVar(postEntity.getSemantic());
		return handleSemantic(lang, userIdentify);
	}

	@Override
	public SemanticResult handleSemantic(String text, String userIdentify) throws Exception {
		semanticContext.loadByUserIdentify(userIdentify);
		SemanticResult sr;
		if (text == null || text.isEmpty()) {
			sr = new SemanticResult(404, "Match Fail!", null, new WaringSemanticResult("我听不懂你想说什么！"));
		} else {
			YbnfCompileResult result = parseSemantic(text, semanticContext.getService(), 0);
			if (result == null) {
				result = parseSemantic(text, "common", 0);
			}
			// 切换场景则清空参数
			if (!result.getService().equals(semanticContext.getService())) {
				semanticContext.setService(result.getService()); // 重置场景
				if (!semanticContext.getParams().isEmpty()) {
					semanticContext.getParams().clear();
				}
			}
			Semantic<?> semantic = semanticFactory.build(result.getService());
			AbstractSemanticResult rs = semantic.handle(result, semanticContext);
			sr = new SemanticResult(rs.getErrCode(), rs.getErrMsg(), result, rs);
		}
		sr.setText(text);
		return sr;
	}

	private YbnfCompileResult parseSemantic(String text, String service, int level) throws Exception {
		if (level > 1) {
			return null;
		}
		YbnfCompileResult result;
		String serviceName = service;
		if (serviceName == null || "".equals(serviceName)) {
			// 场景为空时表示场景匹配，场景不为空表示意图匹配
			serviceName = semanticScene.matching(text);
		}
		// 根据场景名进行意图匹配
		result = semanticIntention.service(serviceName).matching(text);
		// 意图匹配失败后，则进入场景匹配
		if (result == null) {
			result = parseSemantic(text, null, level + 1);
		}
		return result;
	}
}
