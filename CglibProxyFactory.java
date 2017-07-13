package com.sp2.cglibproxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.sp2.dynamicproxy.IAspect;

/**
 * A Cglib proxy factory template. The type of target can be whatever a
 * interface or a class. This kind of proxy use the extending method, getting
 * rid of the necessity of interface which target have to implement.
 * 
 * Cglib needs two extender jar supports, which are cglib-1.2.jar and
 * asm-3.3.jar.
 * 
 * @author eryeer
 * 
 * @param <T>
 */

@SuppressWarnings("unchecked")
public class CglibProxyFactory<T> implements MethodInterceptor {
	private T target;
	private IAspect iAspect;

	/**
	 * @param target
	 * @param iAspect
	 */
	public CglibProxyFactory(T target, IAspect iAspect) {
		super();
		this.target = target;
		this.iAspect = iAspect;
	}

	public T getProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(this);
		T proxy = (T) enhancer.create();
		return proxy;
	}

	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		if ("sleep".equals(method.getName())) {
			iAspect.before();
		}
		Object invoke = method.invoke(target, args);
		if ("sleep".equals(method.getName())) {
			iAspect.after();
		}

		return invoke;
	}

}
