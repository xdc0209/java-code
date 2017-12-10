package com.xdc.basic.api.args.args4j.subcommand;

/**
 * 实现简单sql客户端，用java执行sql语句
 * 
 * @author xdc
 *
 */
public class SqlMain
{
    public static void main(String[] args)
    {
        // args = new String[] { "mysql", "-H", "127.0.0.1", "-P", "8086", "-u", "root", "-p", "123456", "-s", "select * from dual" };

        try
        {
            SqlCommand sqlCommand = new SqlCommand();
            sqlCommand.parseAndExec(args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
