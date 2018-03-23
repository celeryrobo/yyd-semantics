package com.yyd.semantic;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.DicAnalysis;

public class Test {
	public static void main(String[] args) throws Exception {
		Result r = DicAnalysis.parse("我想听刘德华的冰雨这首歌");
		System.out.println(r);
	}
}
