package com.yyd.semantic.services;

import com.yyd.semantic.common.SemanticResult;
import com.yyd.service.domain.SemanticPostEntity;

public interface SemanticService {

	SemanticResult handleSemantic(String text, String userIdentify) throws Exception;

	SemanticResult handleSemantic(SemanticPostEntity postEntity) throws Exception;

}
