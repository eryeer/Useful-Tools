package com.sp2.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * A dynamic proxy template. Only field constructor is provided with target and
 * aspect instances as parameters. Be aware that T should be a interface.
 * 
 * @author eryeer
 * 
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class DynamicProxyFactory<T> implements InvocationHandler {

	private T target;
	private IAspect iAspect;

	/**
	 * @param target
	 * @param iAspect
	 */
	public DynamicProxyFactory(T target, IAspect iAspect) {
		super();
		this.target = target;
		this.iAspect = iAspect;
	}

	public T getProxy() {
		T proxyTarget = (T) Proxy.newProxyInstance(DynamicProxyFactory.class
				.getClassLoader(), target.getClass().getInterfaces(), this);
		return proxyTarget;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		iAspect.before();
		Object obj = method.invoke(target, args);
		iAspect.after();
		return obj;
	}
}
