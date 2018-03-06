package com.yyd.semantic.common.impl;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class WaringSemanticResult extends AbstractSemanticResult {
	private String text = "";

	public WaringSemanticResult(String text) {
		setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
