package org.springframework.aop.interceptor;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.beans.testfixture.beans.TestBean;

abstract class ExposedInvocationTestBean extends TestBean {

	@Override
	public String getName() {
		MethodInvocation invocation = ExposeInvocationInterceptor.currentInvocation();
		assertions(invocation);
		return super.getName();
	}

	@Override
	public void absquatulate() {
		MethodInvocation invocation = ExposeInvocationInterceptor.currentInvocation();
		assertions(invocation);
		super.absquatulate();
	}

	protected abstract void assertions(MethodInvocation invocation);
}
