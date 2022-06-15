package com.kingzhy.mybatis.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * 包扫描工具类
 *
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 18:13
 */
public class ClassScanner {

    /**
     * 根据包名加载类
     *
     * @param packageName
     * @return java.util.Set<java.lang.Class < ?>>
     * @author wzy
     * @date 2022/6/15 14:29
     */
    public static Set<Class<?>> scanPackage(String packageName) {
        // 1.将.分割的包名转为/分割的包名
        String sourceName = getSourceName(packageName);
        Set<Class<?>> classSet = new HashSet<>();
        try {
            // 2.获取符合包名的所有包路径
            List<URL> source = getSource(sourceName);
            for (URL url : source) {
                // 3.将包路径下的类加载到系统中
                classSet.addAll(getMapper(url, sourceName));
            }
        } catch (Exception e) {
            throw new RuntimeException("解析mapper路径：" + packageName + " 失败!", e);
        }
        return classSet;
    }

    /**
     * 加载包路径下的类
     *
     * @param url
     * @param path
     * @return java.util.Set<java.lang.Class < ?>>
     * @author wzy
     * @date 2022/6/15 14:29
     */
    private static Set<Class<?>> getMapper(URL url, String path) throws ClassNotFoundException {
        File file = new File(url.getFile());
        Set<Class<?>> clazz = new HashSet<>();
        if (!path.endsWith("/")) {
            path += "/";
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles((f, name) -> name.endsWith(".class"));
            if (files != null && files.length > 0) {
                for (File file1 : files) {
                    String className = path + file1.getName();
                    Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(className.replace("/", ".").replace(".class", ""));
                    clazz.add(loadClass);
                }
            }
        }
        return clazz;
    }

    /**
     * 转换包名格式
     * el:将com.kingzhy.dao转换为com/kingzhy/dao
     *
     * @param packageName
     * @return java.lang.String
     * @author wzy
     * @date 2022/6/14 19:23
     */
    protected static String getSourceName(String packageName) {
        return packageName == null ? null : packageName.replace(".", "/");
    }

    /**
     * 获取符合包名的包路径
     * el:包名：com/kingzhy/mybatis/test/dao
     *  包路径：file:/D:/work/project/mybatis-demo/target/test-classes/com/kingzhy/mybatis/test
     *
     * @param packageName
     * @return java.util.List<java.net.URL>
     * @author wzy
     * @date 2022/6/15 14:27
     */
    public static List<URL> getSource(String packageName) throws IOException {
        return getSource(packageName, Thread.currentThread().getContextClassLoader());
    }

    public static List<URL> getSource(String packageName, ClassLoader classLoader) throws IOException {
        return Collections.list(classLoader.getResources(packageName));
    }
}
