package com.yyd.semantic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yyd.semantic.common.SemanticResult;
import com.yyd.semantic.common.impl.WaringSemanticResult;
import com.yyd.semantic.services.SemanticService;

@RestController
@Scope("prototype")
@RequestMapping("/semantic")
public class SemanticController {
	@Autowired
	private SemanticService semanticService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public SemanticResult get(@RequestParam String userIdentify, @RequestParam String lang) {
		SemanticResult sr = null;
		long start = System.currentTimeMillis();
		try {
			sr = semanticService.handleSemantic(lang, userIdentify);
		} catch (Exception e) {
			sr = new SemanticResult(500, e.getMessage(), null, new WaringSemanticResult("哎呀，服务异常，看来有人要扣奖金了！"));
			e.printStackTrace();
		}
		sr.setTime(System.currentTimeMillis() - start);
		return sr;
	}
}
