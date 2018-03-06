package com.yyd.semantic;

import com.yyd.semantic.common.impl.SemanticIntention;

public class Test {
	public static void main(String[] args) throws Exception {
		System.out.println(new SemanticIntention().service("music").matching("我想听刘德华的冰雨"));
		System.out.println(new SemanticIntention().service("music").matching("我想听刘德华的歌"));
	}
}
