package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;

public class UserServiceImpl implements UserService{
    private UserDao userDao = new UserDaoImpl();
    @Override
    public Boolean regis(User user) {
        User resultUser = userDao.findUserByUsername(user.getUsername());
        if (user==null){
            userDao.saveUser(user);
            return true;
        }
        return false;
    }
}
