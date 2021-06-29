package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.impl.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        String code = req.getParameter("code");
        String msg = null;
        if (code!=null&&!code.isEmpty()){
            Boolean flag= userService.active(code);
            if (flag==true){
                msg = "Active Success <a href='login.html'>Login</a>";
            }else if (flag==null){
                msg = "Active Code Be Used,Please Login <a href='login.html'>Login</a>";
            }else {
                msg = "Active Error, Please Call Help...May Be Regis Error,Please Check";
            }
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(msg);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
