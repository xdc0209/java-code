package com.xdc.basic.tools.sqlplus;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

public class SQLPlus
{
    public static void excute(Map<String, String> arguments) throws SQLException, IOException
    {
        String host = arguments.get(Option.HOST.getOption());
        String port = arguments.get(Option.PORT.getOption());
        String sid = arguments.get(Option.SID.getOption());
        String user = arguments.get(Option.USER.getOption());
        String password = arguments.get(Option.PASSWORD.getOption());

        String url = String.format("jdbc:oracle:thin:@%s:%s/%s", host, port, sid);

        Properties conProps = new Properties();
        conProps.put("user", user);
        conProps.put("password", password);
        conProps.put("defaultRowPrefetch", "15");
        if (arguments.containsKey(Option.DBA.getOption()))
        {
            conProps.put("internal_logon", "sysdba");
        }

        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

        Connection conn = DriverManager.getConnection(url, conProps);
        conn.setAutoCommit(false);

        DbmsOutput dbmsOutput = new DbmsOutput(conn);
        dbmsOutput.enable(1000000);

        Reader reader = null;
        if (arguments.containsKey(Option.FILE.getOption()))
        {
            reader = new FileReader(arguments.get(Option.FILE.getOption()));
        }
        else if (arguments.containsKey(Option.SQL.getOption()))
        {
            reader = new StringReader(arguments.get(Option.SQL.getOption()));
        }

        ScriptRunner runner = new ScriptRunner(conn);
        runner.setDelimiter("##");

        runner.runScript(reader);
        conn.commit();
        dbmsOutput.show();

        dbmsOutput.close();
        conn.close();
        reader.close();
    }
}