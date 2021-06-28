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
            String sql = "select * from User Where username = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean saveUser(User user) {
        String sql = "insert into User(username,password,name,birthday,sex,telephone,email) value(?,?,?,?,?,?,?)";
        int updateFlag = jdbcTemplate.update(sql, user.getUsername(),user.getPassword(),user.getName(),
                user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail());
        return false;
    }
}