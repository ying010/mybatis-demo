package com.kingzhy.mybatis.binding;

import com.kingzhy.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * mapper代理工厂
 *
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 15:36
 */
public class MapperProxyFactory<T> {

    /**
     * 需要代理的mapper接口
     */
    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * 获取mapper接口的代理实现类
     *
     * @param sqlSession sqlSession
     * @return T
     * @author wzy
     * @date 2022/6/15 11:46
     */
    public T newInterface(SqlSession sqlSession) {
        // mapperProxy是代理的具体实现逻辑
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface);
        // 获取接口的代理实现类
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
