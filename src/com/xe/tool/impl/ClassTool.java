package com.xe.tool.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ClassTool {
	/**
	 * 获得数组基础类型
	 * 
	 * @param typeClass
	 *            传入数组类型
	 * @return 返回数组的基础类型
	 * @throws Exception
	 */
	public static Class<?> getArrayType(Class<?> typeClass) throws Exception {
		Class<?> claz = typeClass.getComponentType();
		if (!claz.isArray())
			return claz;
		else {
			return getArrayType(claz);
		}
	}

	/**
	 * 返回所有字段包含父类字段
	 * 
	 * @param claz
	 *            类
	 * @return Field[] 返回所有字段包含父类字段
	 * @throws
	 */
	public static Field[] getFieldsContainSuper(Class<?> claz) {
		Field[] fields = claz.getDeclaredFields();
		Class<?> superClass = claz.getSuperclass();

		Field[] fieldsAll = null;
		if (superClass != null) {
			Field[] superField = getFieldsContainSuper(superClass);
			fieldsAll = ArrayUtils.addAll(superField, fields);
		}

		return fieldsAll;
	}

	/**
	 * 返回指定字段包含父类字段
	 * 
	 * @param claz
	 *            类
	 * @return Field[] 返回指定字段包含父类字段
	 * @throws
	 */
	public static Field getFieldAboutSuper(Class<?> claz, String name) {
		Field field = null;
		try {
			field = claz.getDeclaredField(name);
		} catch (NoSuchFieldException | SecurityException e) {

		}

		if (field == null) {
			Class<?> superClass = claz.getSuperclass();
			if (superClass != null) {
				field = getFieldAboutSuper(superClass, name);
			}
		}

		return field;
	}

	/**
	 * 获得数据维数
	 * 
	 * @param typeClass
	 *            传入数组类型
	 * @return 返回数组维数
	 */
	public static int getArrayNum(Class<?> typeClass) {
		int num = 1;
		Class<?> claz = typeClass.getComponentType();
		if (claz == null)
			return 0;

		if (claz.isPrimitive())
			return num;

		return num + getArrayNum(claz);
	}

	/**
	 * 返回二维数组列的最大长度
	 * 
	 * @param arr
	 *            传入需要分割为二维数组
	 * @return
	 */
	public static int getMaxArrLen(String[] arr) {
		int max = 0;
		for (String len : arr) {
			int length = len.split(",").length;
			if (max < length) {
				max = length;
			}
		}

		return max;
	}

	/**
	 * 字符串的转换为字符串数组的维数处理[只处理一维与二维的数组]
	 * 
	 * @param context
	 *            字符串内容
	 * @param arrNum
	 *            维数
	 * @return
	 */
	public static Object parseString2Array(String context, int arrNum) {
		if (arrNum == 1) {
			if (StringUtils.isEmpty(context)) {
				return new String[0];
			}
			String strs[] = context.trim().split(",");
			return strs;
		}

		if (arrNum == 2) {
			if (StringUtils.isEmpty(context)) {
				return new String[0][0];
			}
			String strs[] = context.trim().split(";");
			String array[][] = new String[strs.length][getMaxArrLen(strs)];
			for (int i = 0; i < strs.length; i++) {
				String ione[] = strs[i].split(",");
				for (int j = 0; j < ione.length; j++) {
					array[i][j] = ione[j];
				}
			}
			return array;
		}

		return null;
	}

	/**
	 * 根据字段名找到对应的setter方法
	 * 
	 * @param fieldName
	 *            字段名
	 * @return 返回setter方法名
	 */
	public static String getSetterMethod(String fieldName, Class<?> clazz) {
		if ((clazz.getName().equals("boolean") || clazz.getName().equals("java.lang.Boolean"))
				&& fieldName.startsWith("is")) {
			fieldName = fieldName.replaceFirst("is", "");
		}
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
	}

	/**
	 * 
	 * @param clsName
	 *            类名
	 * @param name
	 *            方法名
	 * @return Method 返回对应方法
	 */
	public static Method getMethodByClassAndName(Class<?> baseDefClass, String name) {
		Method methods[] = getAllMethodContainSuper(baseDefClass);
		Method m = null;
		for (Method method : methods) {
			String methodName = method.getName();
			if (name.trim().equals(methodName)) {
				m = method;
				break;
			}
		}
		return m;
	}

	/**
	 * 获得所有的方法包含父类方法
	 * 
	 * @param @param clazz
	 * @param @return
	 * @return Method[] 返回类型
	 * @throws
	 */
	public static Method[] getAllMethodContainSuper(Class<?> clazz) {
		Method methods[] = clazz.getMethods();

		Method methodsAll[] = null;
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			Method[] superMethos = getAllMethodContainSuper(superClass);
			methodsAll = ArrayUtils.addAll(superMethos, methods);
		}
		return methodsAll;
	}

}
