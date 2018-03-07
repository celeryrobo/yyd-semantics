package com.yyd.semantic;

import com.yyd.semantic.common.impl.SemanticIntention;
import com.yyd.semantic.common.impl.SemanticScene;

public class Test {
	public static void main(String[] args) throws Exception {
		String feature = "C:/Users/hongxinzhao/Desktop/owl/total_word_feature_extractor_wenda.dat";
		String category = "C:/Users/hongxinzhao/Desktop/owl/all1_model.dat";

		String lang = "床前明月光上句";
		String service = new SemanticScene(feature, category).matching(lang);
		System.out.println(new SemanticIntention().service(service).matching(lang));
	}
}
