package com.yyd.semantic.services;

import com.yyd.semantic.common.SemanticResult;

public interface SemanticService {

	SemanticResult handleSemantic(String service, String userIdentify) throws Exception;

}
