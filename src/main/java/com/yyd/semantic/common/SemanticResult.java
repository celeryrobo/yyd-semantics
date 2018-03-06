package com.yyd.semantic.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ybnf.compiler.beans.AbstractSemanticResult;
import com.ybnf.compiler.beans.AbstractSemanticResult.Operation;
import com.ybnf.compiler.beans.AbstractSemanticResult.ParamType;
import com.ybnf.compiler.beans.YbnfCompileResult;

class KV {
	private String key;
	private String value;

	public KV(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

public class SemanticResult {
	private static Map<String, Object> emptyObject = new HashMap<>();
	private Integer ret;
	private String msg;
	private String service;
	private String text;
	private Long time;
	private Map<String, Object> semantic;
	private Object data = emptyObject;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object resource = null;

	public SemanticResult(Integer ret, String msg, YbnfCompileResult result, AbstractSemanticResult semanticResult) {
		setRet(ret);
		setMsg(msg);
		setSemantic(new HashMap<String, Object>());
		if (result != null) {
			setService(result.getService());
			setText(result.getText());
			getSemantic().putAll(result.getSlots());
			Map<String, String> objects = result.getObjects();
			List<KV> slots = new LinkedList<>();
			if (objects != null) {
				for (Map.Entry<String, String> entry : objects.entrySet()) {
					slots.add(new KV(entry.getKey(), entry.getValue()));
				}
			}
			getSemantic().put("slots", slots);
		}
		if (semanticResult == null) {
			getSemantic().put("operation", Operation.SPEAK);
			getSemantic().put("paramType", ParamType.T);
		} else {
			getSemantic().put("operation", semanticResult.getOperation());
			getSemantic().put("paramType", semanticResult.getParamType());
			setResource(semanticResult.getResource());
		}
		setData(semanticResult);
	}

	public Integer getRet() {
		return ret;
	}

	public void setRet(Integer ret) {
		this.ret = ret;
	}

	public String getMsg() {
		if (msg == null) {
			return "";
		}
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getService() {
		if (service == null) {
			return "";
		}
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, Object> getSemantic() {
		if (semantic == null) {
			return emptyObject;
		}
		return semantic;
	}

	public void setSemantic(Map<String, Object> semantic) {
		this.semantic = semantic;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}

}
