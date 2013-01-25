package skt.tmall.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectUtil {
	
	/**
	 * targetObj data put to setObj
	 * @param setObj
	 * @param targetObj
	 */
	public static void convertBean(Object setObj, Object targetObj){
		try {
			Class<?> setter = Class.forName(setObj.getClass().getName());
			Class<?> target = Class.forName(targetObj.getClass().getName());

			Method setterMethodList[] = setter.getDeclaredMethods();
			Method targetMethodList[] = target.getDeclaredMethods();

			String methodName1 = "";
			String methodName2 = "";

			Object[] callParameter  = null;

			for (Method method1 : setterMethodList) {
				for (Method method2 : targetMethodList) {
					methodName1 = method1.getName();
					methodName2 = method2.getName();

					if (methodName1.indexOf("set") > -1 && methodName2.indexOf("get") > -1) {
						if ((methodName1.substring(3)).toUpperCase().equals((methodName2.substring(3)).toUpperCase())){

							if (method1.getParameterTypes()[0].getName().equals(method2.getReturnType().getName())){
								callParameter = new Object[]{method2.invoke(targetObj)};
							}
							else if(method1.getParameterTypes()[0] == int.class && method2.getReturnType() == String.class){
								callParameter = new Object[]{(Integer)method2.invoke(targetObj)};
							}
							else if(method1.getParameterTypes()[0] == long.class && method2.getReturnType() == String.class){
								callParameter = new Object[]{(Long)method2.invoke(targetObj)};
							}
							else if(method1.getParameterTypes()[0] == float.class && method2.getReturnType() == String.class){
								callParameter = new Object[]{(Float)method2.invoke(targetObj)};
							}
							else if(method1.getParameterTypes()[0] == double.class && method2.getReturnType() == String.class){
								callParameter = new Object[]{(Double)method2.invoke(targetObj)};
							}
							else if(method1.getParameterTypes()[0] == String.class &&
									(method2.getReturnType() == int.class || method2.getReturnType() == long.class || method2.getReturnType() == float.class) || method2.getReturnType() == double.class){
								callParameter = new Object[]{String.valueOf(method2.invoke(targetObj))};
							}
							else{
								callParameter = null;
								continue;
							}

							method1.invoke(setObj, callParameter);
						}
					}
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * mandatory setter variables check method
	 * @param targetObj
	 * @param setters
	 * @return boolean
	 */
	public static boolean checkMandatorySetter(Object targetObj, String[] setters) {
		
		boolean checkable = false;
		
		try {
			
			Class<?> target = Class.forName(targetObj.getClass().getName());

			Method getterMethod = null;
			
			for (String setter : setters) {
				
				String methodNm = "get" + setter.substring(0, 1).toUpperCase() + setter.substring(1);
				
				getterMethod = target.getMethod(methodNm);
				
				if (getterMethod == null)	{
					checkable = false;
					return checkable;
				}
				
				if(getterMethod.getReturnType() == int.class ){
					int temp = (Integer)getterMethod.invoke(targetObj);
					checkable = (temp == 0) ? false : true;
				}
				else if(getterMethod.getReturnType() == long.class) {
					long temp = (Long)getterMethod.invoke(targetObj);
					checkable = (temp == 0) ? false : true;
				}
				else if(getterMethod.getReturnType() == float.class) {
					float temp = (Float)getterMethod.invoke(targetObj);
					checkable = (temp == 0.0) ? false : true;
				}
				else if(getterMethod.getReturnType() == double.class) {
					double temp = (Double)getterMethod.invoke(targetObj);
					checkable = (temp == 0.0) ? false : true;
				}
				else if(getterMethod.getReturnType() == String.class) {
					String temp = String.valueOf(getterMethod.invoke(targetObj));
					checkable = (temp == null || temp.equals("")) ? false : true;
				}
				else {
					checkable = false;
				}
				
				if (!checkable) return checkable;
				
			}
			
			checkable = true;

		} catch (ClassNotFoundException e) {
			checkable = false;
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			checkable = false;
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			checkable = false;
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			checkable = false;
			e.printStackTrace();
		} catch (SecurityException e) {
			checkable = false;
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			checkable = false;
			e.printStackTrace();
		} finally {
			return checkable;
		}
		
	}
	
}
