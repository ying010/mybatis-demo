package com.kingzhy.mybatis.test.dao;

/**
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 15:57
 */
public interface IUserDao {
    String queryUserName(String uId);

    Integer queryUserAge(String uId);
}
