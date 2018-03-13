package com.yyd.semantic.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yyd.semantic.common.SemanticResult;
import com.yyd.semantic.common.impl.WaringSemanticResult;
import com.yyd.semantic.services.SemanticService;
import com.yyd.service.domain.SemanticPostEntity;

@RestController
@Scope("prototype")
@RequestMapping("/semantic")
public class SemanticController {
	@Autowired
	private SemanticService semanticService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public SemanticResult get(@RequestParam String userIdentify, @RequestParam String lang) {
		return handler(() -> {
			return semanticService.handleSemantic(lang, userIdentify);
		});
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public SemanticResult post(@RequestBody SemanticPostEntity postEntity) {
		return handler(() -> {
			return semanticService.handleSemantic(postEntity);
		});
	}

	private SemanticResult handler(Callable<SemanticResult> callable) {
		SemanticResult sr = null;
		long start = System.currentTimeMillis();
		try {
			sr = callable.call();
		} catch (Exception e) {
			sr = new SemanticResult(500, e.getMessage(), null, new WaringSemanticResult("哎呀，服务异常了！"));
			e.printStackTrace();
		}
		sr.setTime(System.currentTimeMillis() - start);
		return sr;
	}
}
