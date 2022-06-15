package com.kingzhy.mybatis.session;

/**
 * sqlSession工厂
 *
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 17:52
 */
public interface SqlSessionFactory {

    /**
     * 开启一个sqlSession
     *
     * @return com.kingzhy.mybatis.session.SqlSession
     * @author wzy
     * @date 2022/6/14 18:25
     */
    SqlSession openSession();
}
