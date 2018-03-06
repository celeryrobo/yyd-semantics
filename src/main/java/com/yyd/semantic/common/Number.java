package com.yyd.semantic.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Digital {
	public final static int ZERO = 0;
	public final static int DIGITAL = 1;
	public final static int UNIT = 2;
	private Long value;
	private int type;
	private boolean isCompositeUnit = false;

	public Digital(int type, Long value) {
		this(type, value, false);
	}

	public Digital(int type, Long value, boolean isCompositeUnit) {
		this.setValue(value);
		this.setType(type);
		this.setCompositeUnit(isCompositeUnit);
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isCompositeUnit() {
		return isCompositeUnit;
	}

	public void setCompositeUnit(boolean isCompositeUnit) {
		this.isCompositeUnit = isCompositeUnit;
	}
}

public class Number {
	private static Map<String, Digital> numbers;
	static {
		numbers = new HashMap<>();
		numbers.put("零", new Digital(Digital.ZERO, 0L));
		numbers.put("一", new Digital(Digital.DIGITAL, 1L));
		numbers.put("二", new Digital(Digital.DIGITAL, 2L));
		numbers.put("三", new Digital(Digital.DIGITAL, 3L));
		numbers.put("四", new Digital(Digital.DIGITAL, 4L));
		numbers.put("五", new Digital(Digital.DIGITAL, 5L));
		numbers.put("六", new Digital(Digital.DIGITAL, 6L));
		numbers.put("七", new Digital(Digital.DIGITAL, 7L));
		numbers.put("八", new Digital(Digital.DIGITAL, 8L));
		numbers.put("九", new Digital(Digital.DIGITAL, 9L));

		numbers.put("十", new Digital(Digital.UNIT, 10L));
		numbers.put("百", new Digital(Digital.UNIT, 100L));
		numbers.put("千", new Digital(Digital.UNIT, 1000L));
		numbers.put("万", new Digital(Digital.UNIT, 10000L, true));
		numbers.put("亿", new Digital(Digital.UNIT, 100000000L, true));
	}

	private Long number = 0L;
	private boolean isSuccess = true;

	public Number(String numberStr) {
		try {
			calc(numberStr);
		} catch (Exception e) {
			isSuccess = false;
		}
	}

	private void calc(String numberStr) {
		Stack<Long> stack = new Stack<>();
		for (int index = 0; index < numberStr.length(); index++) {
			char ch = numberStr.charAt(index);
			Digital digital = numbers.get("" + ch);
			switch (digital.getType()) {
			case Digital.DIGITAL: {
				stack.push(digital.getValue());
				break;
			}
			case Digital.UNIT: {
				Long num = 1L, n = 0L;
				if (!stack.isEmpty()) {
					num = stack.pop();
				}
				if (!stack.isEmpty()) {
					n = stack.pop();
				}
				if ((n != 0L) && digital.isCompositeUnit()) {
					stack.push((n + num) * digital.getValue());
				} else {
					stack.push(n + num * digital.getValue());
				}
				break;
			}
			default:
				break;
			}
		}
		while (!stack.isEmpty()) {
			number += stack.pop();
		}
	}

	public Long getNumber() {
		return number;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

}
