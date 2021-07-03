package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    public Boolean regis(User user);

    public Boolean active(String code);

    public User login(User user);
}
