package com.xdc.basic.apidemo.mybatis.generator;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.xdc.basic.apidemo.mybatis.generator.data.StudentMapper;
import com.xdc.basic.apidemo.mybatis.generator.model.Student;

public class MyBatisTest
{
    public static void main(String[] args) throws IOException
    {
        String resource = "com/xdc/basic/example/mybatis/generator/data/mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        /*
         * http://mybatis.github.io/mybatis-3/zh/java-api.html#sqlSessions
         * 默认的 openSession()方法没有参数,它会创建有如下特性的 SqlSession:
         * 1.会开启一个事务(也就是不自动提交)。
         * 2.连接对象会从由活动环境配置的数据源实例中得到。
         * 3.事务隔离级别将会使用驱动或数据源的默认设置。
         * 4.预处理语句不会被复用,也不会批量处理更新。
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try
        {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

            Student student = studentMapper.selectByPrimaryKey(4);
            System.out.println("插入前：" + student);

            studentMapper.insert(new Student(4, "耿雪", "女", "计算机科学与技术", "2008"));

            student = studentMapper.selectByPrimaryKey(4);
            System.out.println("提交前：" + student);

            // 提交的意义在于：在其他的sqlSession中能够看见语句的结果，而在本身的sqlSession中，无论提交与否，都是可见的。
            sqlSession.commit();

            student = studentMapper.selectByPrimaryKey(4);
            System.out.println("提交后：" + student);

            studentMapper.deleteByPrimaryKey(4);

            sqlSession.commit();

            student = studentMapper.selectByPrimaryKey(4);
            System.out.println("删除后：" + student);
        }
        finally
        {
            // 如果在关闭前都没有提交，那么前面的内容将自动回滚。
            sqlSession.close();
        }
    }
}
