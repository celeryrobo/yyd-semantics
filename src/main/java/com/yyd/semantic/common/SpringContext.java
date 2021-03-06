package com.yyd.semantic.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		applicationContext = appContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public <T> T getBean(Class<T> clazz) {
		try {
			return applicationContext.getBean(clazz);
		} catch (Exception e) {
			return null;
		}
	}

	public <T> T getBean(String name, Class<T> clazz) {
		try {
			return applicationContext.getBean(name, clazz);
		} catch (Exception e) {
			return null;
		}
	}
}
