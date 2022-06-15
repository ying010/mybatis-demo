package com.kingzhy.mybatis.binding;

import com.kingzhy.mybatis.io.ClassScanner;
import com.kingzhy.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * mapper注册
 *
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 17:31
 */
public class MapperRegistry {

    /**
     * 已被注册的mapper接口列表
     */
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    /**
     * 获取mapper的代理实现类
     *
     * @param type       mapper接口类型
     * @param sqlSession sqlSession
     * @return mapper接口的代理实现类
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException(type + " 尚未被注册!");
        }
        try {
            return mapperProxyFactory.newInterface(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("获取mapper代理失败", e);
        }
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                throw new RuntimeException(type + "重复注册!");
            }
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    /**
     * 将包下的所有mapper加载到系统中
     *
     * @param packageName 包路径
     * @author wzy
     * @date 2022/6/15 11:55
     */
    public void addMappers(String packageName) {
        Set<Class<?>> mapperSet = ClassScanner.scanPackage(packageName);
        for (Class<?> aClass : mapperSet) {
            addMapper(aClass);
        }
    }

    /**
     * 判断mapper是否被加载
     *
     * @param type
     * @return boolean
     * @author wzy
     * @date 2022/6/15 11:55
     */
    private <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }
}
