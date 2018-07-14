package com.roadrantz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class UserDaoImpl extends JdbcDaoSupport implements UserDetailsService
{
    public UserDaoImpl()
    {
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException
    {
        List users = getJdbcTemplate().query("select email, password from driver where email=?",
                new Object[] { userName }, new RowMapper()
                {
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException
                    {
                        String userName = rs.getString(1);
                        String password = rs.getString(2);

                        return new User(userName, password, true, true, true, true,
                                new GrantedAuthority[] { new GrantedAuthorityImpl("ROLE_USER") });
                    };
                });

        if (users.size() > 0)
        {
            return (UserDetails) users.get(0);
        }

        throw new UsernameNotFoundException(userName);
    }
}
