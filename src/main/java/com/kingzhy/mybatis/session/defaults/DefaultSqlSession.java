package com.kingzhy.mybatis.session.defaults;

import com.kingzhy.mybatis.binding.MapperRegistry;
import com.kingzhy.mybatis.session.SqlSession;

/**
 * 默认的sqlSession实现
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 17:52
 */
public class DefaultSqlSession implements SqlSession {

    /**
     * mapper注册器
     */
    private final MapperRegistry mapperRegistry;

    public DefaultSqlSession(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return (T) ("你被代理了！" + "方法：" + statement + " 入参：" + parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }
}
