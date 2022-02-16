package com.love.sports.utils;

import java.lang.reflect.Field;

public class ObjectUtils {


    /***
     * 判断对象所有属性是否为空
     *
     * @param obj 对象
     * @return boolean
     * @throws Exception
     */
    public static boolean isAllFieldNull(Object obj) {
        // 得到类对象
        Class clazz =  obj.getClass();
        //得到属性集合
        Field[] fs = clazz.getDeclaredFields();
        boolean flag = true;
        //遍历属性
        for (Field f : fs) {
            try {
                // 设置属性是可以访问的(私有的也可以)
                f.setAccessible(true);
                // 得到此属性的值
                Object val = f.get(obj);
                // 只要有1个属性不为空,那么就不是所有的属性值都为空
                if (val != null && !"delFlag".equals(f.getName())&&!"serialVersionUID".equals(f.getName())) {
                    flag = false;
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
