package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService{
    private UserDao userDao = new UserDaoImpl();
    @Override
    public Boolean regis(User user) {
        User resultUser = userDao.findUserByUsername(user.getUsername());
        if (resultUser==null){
            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");
            userDao.saveUser(user);
            String content = "<a href='http://localhost/travel/activeServlet?code="+user.getCode() + "'>active</a>";
            MailUtils.sendMail(user.getEmail(), content, "Active Code");
            return true;
        }
        return false;
    }

    @Override
    public Boolean active(String code) {
        Boolean flag = false;
        User resultUser = userDao.findUserByCode(code);
        if (resultUser!=null&&!"Y".equals(resultUser.getStatus())){
            flag = true;
            userDao.updateStatus(code);
        }else if ("Y".equals(resultUser.getStatus())){
            flag = null;
        }
        return flag;
    }

    @Override
    public User login(User user) {
        User resultUser = userDao.findUserByUsernameAndPassword(user);
        return resultUser;
    }
}
