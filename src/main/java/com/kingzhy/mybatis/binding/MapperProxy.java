package com.kingzhy.mybatis.binding;

import com.kingzhy.mybatis.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * mapper代理实现
 *
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 15:35
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = 0L;

    private final SqlSession sqlSession;
    /**
     * 被代理的mapper接口
     */
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    /**
     * 代理类的实现逻辑
     * 使用sqlSession#selectOne方法执行查询语句
     *
     * @param method 被代理的方法
     * @param args 方法的参数
     * @return java.lang.Object
     * @author wzy
     * @date 2022/6/15 11:49
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            return sqlSession.selectOne(method.getName(), args);
        }
    }
}
