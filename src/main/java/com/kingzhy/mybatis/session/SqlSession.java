package com.kingzhy.mybatis.session;

/**
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 17:51
 */
public interface SqlSession {

    /**
     * 根据指定的sqlId获取一条记录
     *
     * @param statement sqlId
     * @param <T> 返回的结果类型
     * @return T 返回的结果
     * @author wzy
     * @date 2022/6/14 18:18
     */
    <T> T selectOne(String statement);

    /**
     * 根据条件查询
     *
     * @param statement sqlId - sql语句
     * @param parameter 查询条件
     * @param <T> 返回的结果类型
     * @return T 返回的结果
     * @author wzy
     * @date 2022/6/14 18:21
     */
    <T> T selectOne(String statement, Object parameter);

    /**
     * 得到mapper映射器
     *
     * @param type mapper接口的类型
     * @return T
     * @author wzy
     * @date 2022/6/14 18:22
     */
    <T> T getMapper(Class<T> type);
}
