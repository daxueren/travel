package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.User;

public interface UserService {
    public Boolean regis(User user);

    public Boolean active(String code);
}
