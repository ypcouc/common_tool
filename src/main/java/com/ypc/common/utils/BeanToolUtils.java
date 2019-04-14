package com.ypc.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BeanToolUtils extends BeanUtils {
    /**
     * .
     * 拷贝时，忽略属性
     */
    private static final String[] IGNORE_PROPS = new String[]{"", ""};

    /**
     * 复制某个对象为目标对象类型的对象.
     *
     * @param <T>      目标对象类型参数
     * @param source   源对象
     * @param destType 目标对象类型
     * @return
     */
    public static <T> T copyAs(Object source, Class<T> destType) {
        if (source == null) {
            return null;
        }
        try {
            T dest = destType.newInstance();
            BeanUtils.copyProperties(source, dest);
            return dest;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * .
     * 复制源对象集合到目标对象列表
     *
     * @param <T>
     * @param source
     * @param destType
     * @return
     */
    public static <T> List<T> copyAs(Collection<?> source, Class<T> destType) {
        if (source == null) {
            return null;
        }
        ArrayList<T> result = new ArrayList<T>();
        for (Object object : source) {
            result.add(copyAs(object, destType));
        }
        return result;
    }


    /**
     * 拷贝属性
     *
     * @param source 源对象
     * @param target 目标对象类型
     * @param ignore 是否忽略BeanUtils.IGNORE_PROPS的字段
     * @throws BeansException
     */
    public static void copyPropertiesIgnore(Object source, Object target,
                                            String[] ignore) throws BeansException {
        if (ignore != null && ignore.length > 0) {
            BeanUtils.copyProperties(source, target, ignore);
        } else {
            BeanUtils.copyProperties(source, target);
        }
    }

    /**
     * @param source
     * @param target
     * @param ignore 是否忽略BeanUtils.IGNORE_PROPS的字段
     * @throws BeansException
     * @author 拷贝属性，忽略元数据空值复制
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target,
                                                String[] ignore) throws BeansException {
        _copyProperties(source, target, ignore, true);
    }

    /**
     * @param source
     * @param target
     * @param ignore     是否忽略属性IGNORE_PROPS复制
     * @param ignoreNull 是否忽略空值复制
     * @throws BeansException
     * @author
     */
    private static void _copyProperties(Object source, Object target,
                                        String[] ignore, boolean ignoreNull) throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        PropertyDescriptor[] targetPds = getPropertyDescriptors(target
                .getClass());
        List ignoreList = (ignore != null && ignore.length > 0) ? Arrays.asList(ignore) : null;

        for (int i = 0; i < targetPds.length; i++) {
            PropertyDescriptor targetPd = targetPds[i];
            if (targetPd.getWriteMethod() != null
                    && (ignoreList == null || ignoreList.size() == 0
                    || !ignoreList.contains(targetPd.getName()))) {

                PropertyDescriptor sourcePd = getPropertyDescriptor(source
                        .getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method getter = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(getter.getDeclaringClass()
                                .getModifiers())) {
                            getter.setAccessible(true);
                        }
                        Object value = getter.invoke(source);

                        if (ignoreNull && value == null) {
                            continue;
                        }

                        Method setter = targetPd.getWriteMethod();
                        if (!Modifier.isPublic(setter.getDeclaringClass()
                                .getModifiers())) {
                            setter.setAccessible(true);
                        }
                        setter.invoke(target, value);
                    } catch (Exception ex) {
                        throw new FatalBeanException(
                                "Could not copy properties from source to target",
                                ex);
                    }
                }
            }
        }
    }

}
