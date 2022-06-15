package com.kingzhy.mybatis.session.defaults;

import com.kingzhy.mybatis.binding.MapperRegistry;
import com.kingzhy.mybatis.session.SqlSession;
import com.kingzhy.mybatis.session.SqlSessionFactory;

/**
 * 默认的sqlSession工厂
 *
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 17:52
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    /**
     * 默认获取sqlSession的方式是获取一个DefaultSqlSession
     *
     * @return com.kingzhy.mybatis.session.SqlSession
     * @author wzy
     * @date 2022/6/15 14:43
     */
    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }
}
