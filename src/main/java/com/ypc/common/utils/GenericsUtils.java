package com.ypc.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generics的util类
 * 
 * @author: huym
 * @date: 2018年4月25日
 */
public final class GenericsUtils {
	private static final Logger LOGGER = LogManager
			.getLogger(GenericsUtils.class);
	private GenericsUtils() {
		
	}

	/**
	 * 通过反射，获取定义Class时声明的父类的泛型参数的类型，如：<br>
	 * public BookManager extends GenricManager<Book><br>
	 * 
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射，获取定义Class时声明的父类的泛型参数的类型，如：<br>
	 * public BookManager extends GenricManager<Book><br>
	 * 
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or <code>Object.class</code> if cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz, int index) {
		Class superClass = clazz.getSuperclass();
		if (Object.class.equals(superClass) || superClass == null) {
			Class[] interfaces = clazz.getInterfaces();
			if (interfaces != null && interfaces.length > 0) {
				for (Class inf : interfaces) {
					return getSuperClassGenericType(clazz, inf, index);
				}
			}
			throw new IllegalArgumentException(clazz.getName() + " never parameterized from any generic type");
		}else {
			return getSuperClassGenericType(clazz, clazz.getSuperclass(), index);
		}
	}

	/**
	 * 递归获取某个类（接口）属于某个泛型父类（接口）某个位置的泛型实参，如：<br>
	 * class A<T> {...}<br>
	 * class B extends A<String> {...}<br>
	 * class C extends B {...}<br>
	 * getSuperClassGenericType(B.class, A.class, 0)==>String.class<br>
	 * getSuperClassGenericType(C.class, A.class, 0)==>String.class<br>
	 * 
	 * @param targetType
	 *            目标类型，待确定泛型实参的类型
	 * @param superGenericType
	 *            泛型父类，目标类型的父类，且为泛型
	 * @param paramIndex
	 *            泛型参数位置，从0开始
	 * @return 泛型实参
	 */
	public static Class<?> getSuperClassGenericType(Class<?> targetType, Class<?> superGenericType, int paramIndex) {
		if (targetType == null) {
			throw new IllegalArgumentException("Target type should not be null");
		}
		if (superGenericType == null) {
			throw new IllegalArgumentException("Super generic type should not be null");
		}
		if (paramIndex < 0) {
			throw new IllegalArgumentException("Parameter index should not be less than 0");
		}

		TypeVariable<?>[] typeVariables = superGenericType.getTypeParameters();
		if (typeVariables.length == 0) {
			throw new IllegalArgumentException(superGenericType.getName() + " isn't a generic type");
		}

		if (typeVariables.length <= paramIndex) {
			throw new IllegalArgumentException(superGenericType.getName() + " has " + typeVariables.length + " type parameter(s), but request paramIndex " + paramIndex);
		}

		if (!superGenericType.isAssignableFrom(targetType)) {
			throw new IllegalArgumentException("Target type " + targetType.getName() + " is not subclass of generic type " + superGenericType.getName());
		}

		if (superGenericType.equals(targetType)) {
			throw new IllegalArgumentException("Target type cann't same as super generic type");
		}

		Map<Class<?>, GenericInfo> map = getGenericInfos(targetType);
		GenericInfo genericInfo = map.get(superGenericType);
		if (genericInfo == null) {
			throw new IllegalArgumentException(superGenericType.getName() + " isn't super generic type of " + targetType.getName());
		}

		if (genericInfo.genericTypes[paramIndex] instanceof TypeVariable) {
			throw new IllegalArgumentException(superGenericType.getName() + " is super generic type of " + targetType.getName() + ",but never parameterized!");
		}

		return (Class<?>) genericInfo.genericTypes[paramIndex];
	}

	/**
	 * 泛型信息
	 * 
	 * @author: huym
	 * @date: 2018年4月26日
	 */
	public static class GenericInfo {
		// 父类型或接口类型
		public Type type;
		// 泛参数组
		public TypeVariable<?>[] typeVariables;

		// 自身类型
		public Type ownerType;
		// 实参数组
		public Type[] genericTypes;

		// 参数化
		public boolean parameterized = true;
		// 解析
		public boolean parsed = false;
	}

	/**
	 * 获取泛型信息
	 * 
	 * @param targetType
	 *            目标类型
	 * @return
	 */
	public static Map<Class<?>, GenericInfo> getGenericInfos(Class<?> targetType) {
		List<GenericInfo> result = new ArrayList<GenericInfo>();

		TypeVariable<?>[] typeVariables = targetType.getTypeParameters();
		if (typeVariables.length > 0) {
			GenericInfo genericInfo = new GenericInfo();
			genericInfo.type = targetType;
			genericInfo.typeVariables = typeVariables;
			genericInfo.genericTypes = typeVariables;
			genericInfo.parameterized = false;
			genericInfo.parsed = true;
			result.add(genericInfo);
		}

		getGenericInfos(targetType, result, targetType);

		parseGenericInfo(result, result.size() - 1);

		if (typeVariables.length > 0) {
			result.remove(0);
		}

		Map<Class<?>, GenericInfo> map = new HashMap<Class<?>, GenericInfo>();
		for (GenericInfo genericInfo : result) {
			map.put((Class<?>) genericInfo.type, genericInfo);
		}

		return map;

	}

	/**
	 * 递归获取泛型信息
	 * 
	 * @param type
	 * @param result
	 * @param ownerType
	 */
	private static void getGenericInfos(Class<?> type, List<GenericInfo> result, Class<?> ownerType) {
		Type[] genTypes = type.getGenericInterfaces();
		if (genTypes.length > 0) {
			for (Type genType : genTypes) {
				if (genType instanceof ParameterizedType) {
					ParameterizedType pType = (ParameterizedType) genType;
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Parameterized type found " + ((Class<?>) pType.getRawType()).getName());
					}
					GenericInfo genericInfo = new GenericInfo();
					genericInfo.type = pType.getRawType();
					genericInfo.genericTypes = pType.getActualTypeArguments();
					genericInfo.ownerType = ownerType;
					for (Type actualArg : pType.getActualTypeArguments()) {
						if (actualArg instanceof TypeVariable) {
							genericInfo.parameterized = false;
						}
					}
					if (genericInfo.parameterized) {
						genericInfo.parsed = true;
					}
					genericInfo.typeVariables = ((Class<?>) pType.getRawType()).getTypeParameters();
					result.add(genericInfo);
					getGenericInfos((Class<?>) pType.getRawType(), result, (Class<?>) pType.getRawType());
				} else if (genType instanceof Class) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Recursive find generic type " + ((Class<?>) genType).getName());
					}
					getGenericInfos((Class<?>) genType, result, (Class<?>) genType);
				}
			}
		}

		Type genType = type.getGenericSuperclass();
		if (genType != null && !genType.equals(Object.class)) {
			if (genType instanceof ParameterizedType) {
				ParameterizedType pType = (ParameterizedType) genType;
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Parameterized type found " + ((Class<?>) pType.getRawType()).getName());
				}
				GenericInfo genericInfo = new GenericInfo();
				genericInfo.type = pType.getRawType();
				genericInfo.genericTypes = pType.getActualTypeArguments();
				genericInfo.ownerType = ownerType;
				for (Type actualArg : pType.getActualTypeArguments()) {
					if (actualArg instanceof TypeVariable) {
						genericInfo.parameterized = false;
					}
				}
				if (genericInfo.parameterized) {
					genericInfo.parsed = true;
				}
				genericInfo.typeVariables = ((Class<?>) pType.getRawType()).getTypeParameters();
				result.add(genericInfo);
				getGenericInfos((Class<?>) pType.getRawType(), result, (Class<?>) pType.getRawType());
			} else if (genType instanceof Class) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Recursive find generic type " + ((Class<?>) genType).getName());
				}
				getGenericInfos((Class<?>) genType, result, (Class<?>) genType);
			}
		}

	}

	/**
	 * 解析泛型信息
	 * 
	 * @param result
	 * @param index
	 */
	protected static void parseGenericInfo(List<GenericInfo> result, int index) {
		for (int i = index; i >= 0; i--) {
			GenericInfo genericInfo = result.get(i);
			if (!genericInfo.parameterized && genericInfo.ownerType != null && !genericInfo.parsed) {
				if (index > 0) {
					parseGenericInfo(result, index - 1);
				}
				boolean parameterized = true;
				for (int j = 0; j < genericInfo.typeVariables.length; j++) {
					if (genericInfo.genericTypes[j] instanceof TypeVariable) {
						TypeVariable<?> typeVariable = (TypeVariable<?>) genericInfo.genericTypes[j];
						for (int k = index - 1; k >= 0; k--) {
							GenericInfo ownerInfo = result.get(k);
							if (genericInfo.ownerType.equals(ownerInfo.type)) {
								for (int l = 0; l < ownerInfo.typeVariables.length; l++) {
									if (typeVariable.getName().equals(ownerInfo.typeVariables[l].getName())) {
										if (ownerInfo.genericTypes[l] instanceof Class) {
											genericInfo.genericTypes[j] = ownerInfo.genericTypes[l];
										} else {
											parameterized = false;
										}
										break;
									}
								}
								break;
							}
						}
					}
				}
				if (parameterized) {
					genericInfo.parameterized = true;
				}
				genericInfo.parsed = true;
			}
		}
	}
	
}
