package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.User;

public interface UserDao {
    public User findUserByUsername(String username);

    public boolean saveUser(User user);
}
