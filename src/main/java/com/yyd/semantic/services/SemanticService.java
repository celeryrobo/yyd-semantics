package com.yyd.semantic.services;

import com.yyd.semantic.common.SemanticResult;

public interface SemanticService {

	SemanticResult handleSemantic(String text, String userIdentify) throws Exception;

}
