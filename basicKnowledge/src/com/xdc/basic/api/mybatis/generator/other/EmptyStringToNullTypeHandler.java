package com.xdc.basic.api.mybatis.generator.other;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class EmptyStringToNullTypeHandler implements TypeHandler<Object>
{
    @Override
    public void setParameter(PreparedStatement ps, int columnIndex, Object parameter, JdbcType jdbcType)
            throws SQLException
    {
        if (parameter == null || ((String) parameter).equals(""))
        {
            ps.setString(columnIndex, null);
        }
        else
        {
            ps.setString(columnIndex, (String) parameter);
        }
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException
    {
        return rs.getString(columnName);
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException
    {
        return rs.getString(columnIndex);
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException
    {
        return cs.getString(columnIndex);
    }
}
