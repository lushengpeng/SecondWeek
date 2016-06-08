package Server;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LogProxy implements InvocationHandler{
	
	
	private Object delegate;
	
	/**
	 * @param delegate  委托对象
	 *
	 */
	public Object bind(Object delegate){
		this.delegate = delegate;
		return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(), this);	
	}
	
	
	
	/**
	 * 实现invoke接口
	 * @param  object     委托对象
	 * @param	method		委托方法
	 * @param	args		方法参数
	 *
	 */
	public Object invoke(Object object,Method method,Object args[]) throws Throwable{
			Object result = null;
		
		try{
			result = method.invoke(delegate, args);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	

}
