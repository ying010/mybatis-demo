package com.kingzhy.mybatis.test;

import com.kingzhy.mybatis.binding.MapperProxyFactory;
import com.kingzhy.mybatis.test.dao.IUserDao;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 15:57
 */
public class ApiTest {

    @Test
    public void test_MapperProxyFactory() {
        MapperProxyFactory<IUserDao> factory = new MapperProxyFactory<>(IUserDao.class);
        Map<String, String> sqlSession = new HashMap<>();

        sqlSession.put("com.kingzhy.mybatis.test.dao.IUserDao.queryUserName", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户姓名");
        sqlSession.put("com.kingzhy.mybatis.test.dao.IUserDao.queryUserAge", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户年龄");
        IUserDao userDao = factory.newInterface(sqlSession);

        String res = userDao.queryUserName("10001");
        System.err.println(res);
    }
}
