package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao{
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try{
            String sql = "select * from tab_user Where username = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public int saveUser(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,code,status) value(?,?,?,?,?,?,?,?,?)";
        int updateFlag = jdbcTemplate.update(sql, user.getUsername(),user.getPassword(),user.getName(),
                user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getCode(),user.getStatus());
        return updateFlag;
    }

    @Override
    public User findUserByCode(String code) {
        User user = null;
        try{
            String sql = "select * from tab_user Where code = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateStatus(String code) {
        String sql = "update tab_user set Status = 'Y' where code = ?";
        jdbcTemplate.update(sql, code);
    }

    @Override
    public User findUserByUsernameAndPassword(User user) {
        User resultUser = null;
        String sql = "select * from tab_user where username=? and password=?";
        resultUser = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class), user.getUsername(), user.getPassword());
        return resultUser;
    }
}
