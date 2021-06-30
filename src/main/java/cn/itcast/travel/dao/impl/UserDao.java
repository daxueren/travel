package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.User;

public interface UserDao {
    public User findUserByUsername(String username);

    public int saveUser(User user);

    public User findUserByCode(String code);

    public void updateStatus(String code);

    public User findUserByUsernameAndPassword(User user);
}
