package com.kingzhy.mybatis.test;

import com.kingzhy.mybatis.binding.MapperRegistry;
import com.kingzhy.mybatis.session.SqlSession;
import com.kingzhy.mybatis.session.SqlSessionFactory;
import com.kingzhy.mybatis.session.defaults.DefaultSqlSessionFactory;
import com.kingzhy.mybatis.test.dao.IUserDao;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.util.*;

/**
 * @author wzy
 * @version v1.0
 * @date 2022/6/14 15:57
 */
public class ApiTest {

    @Test
    public void test_MapperProxyFactory() throws IOException {
        MapperRegistry registry = new MapperRegistry();
        registry.addMappers("com.kingzhy.mybatis.test.dao");

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        String res = userDao.queryUserName("10001");
        System.err.println(res);
    }

    @Test
    public void test_1() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = "com/kingzhy/mybatis/test/dao";
        Enumeration<URL> resources = classLoader.getResources(path);
        ArrayList<URL> list = Collections.list(resources);
        for (URL url : list) {
            File file = new File(url.getFile());
            File[] files = file.listFiles((f, name) -> name.endsWith(".class"));
            for (File file1 : files) {
                if (!path.endsWith("/")) {
                    path += "/";
                }
                String fileName = path + file1.getName();
                Class<?> aClass = classLoader.loadClass(fileName.replace("/", ".").replace(".class", ""));
                System.err.println(aClass);
            }
        }
    }

    @Test
    public void test_2() throws Exception {
        ArrayList<URL> urls = Collections.list(Thread.currentThread().getContextClassLoader().getResources("com/kingzhy/mybatis/test/dao"));
        System.err.println(urls);
    }

    public List<String> list(URL url, String path) throws IOException {
        InputStream is = null;
        try {
            List<String> resources = new ArrayList<>();

            {
                List<String> children = new ArrayList<>();
                try {
                    {
                        is = url.openStream();
                        List<String> lines = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                            for (String line; (line = reader.readLine()) != null;) {
                                lines.add(line);
                                if (getResources(path + "/" + line).isEmpty()) {
                                    lines.clear();
                                    break;
                                }
                            }
                        } catch (InvalidPathException e) {
                            // #1974
                            lines.clear();
                        }
                        if (!lines.isEmpty()) {
                            children.addAll(lines);
                        }
                    }
                } catch (FileNotFoundException e) {
                    if ("file".equals(url.getProtocol())) {
                        File file = new File(url.getFile());
                        if (file.isDirectory()) {
                            children = Arrays.asList(file.list());
                        }
                    } else {
                        throw e;
                    }
                }

                String prefix = url.toExternalForm();
                if (!prefix.endsWith("/")) {
                    prefix = prefix + "/";
                }

                for (String child : children) {
                    String resourcePath = path + "/" + child;
                    resources.add(resourcePath);
                    URL childUrl = new URL(prefix + child);
                    resources.addAll(list(childUrl, resourcePath));
                }
            }

            return resources;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    // Ignore
                }
            }
        }
    }

    protected static List<URL> getResources(String path) throws IOException {
        return Collections.list(Thread.currentThread().getContextClassLoader().getResources(path));
    }
}
