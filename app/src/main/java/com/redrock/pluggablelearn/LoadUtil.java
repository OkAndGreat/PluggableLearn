package com.redrock.pluggablelearn;

import android.content.Context;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

/**
 * Author by OkAndGreat
 * Date on 2021/11/24 16:01.
 */
public class LoadUtil {

    private final static String apkPath = "/data/data/com.redrock.pluggablelearn/plugin-debug.apk";

    public static void loadClass(Context context){
        try {
            //创建模板
            Class<?> clazz = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = clazz.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            // 宿主的 类加载器
            ClassLoader pathClassLoader = context.getClassLoader();
            // DexPathList类的对象
            Object hostPathList = pathListField.get(pathClassLoader);
            // 宿主的 dexElements
            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);

            // 插件的 类加载器
            ClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(),
                    null, pathClassLoader);
            // DexPathList类的对象
            Object pluginPathList = pathListField.get(dexClassLoader);
            // 插件的 dexElements
            Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);


            // 创建一个新数组
            Object[] newDexElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(),
                    hostDexElements.length + pluginDexElements.length);

            System.arraycopy(hostDexElements, 0, newDexElements,
                    0, hostDexElements.length);
            System.arraycopy(pluginDexElements, 0, newDexElements,
                    hostDexElements.length, pluginDexElements.length);

            // 赋值
            // hostDexElements = newDexElements
            dexElementsField.set(hostPathList, newDexElements);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
