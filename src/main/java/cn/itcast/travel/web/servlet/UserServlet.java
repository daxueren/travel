package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet{
    private UserService userService= new UserServiceImpl();

    public void regis(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String check = req.getParameter("check");
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            ResultInfo resltInfo = new ResultInfo();
            resltInfo.setFlag(false);
            resltInfo.setErrorMsg("Check Code Error");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resltInfo);
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(json);
            return;
        }


        Map<String, String[]> parameterMap = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        UserService userService = new UserServiceImpl();
        Boolean flag = userService.regis(user);
        ResultInfo resltInfo = new ResultInfo();
        if (flag){
            resltInfo.setFlag(true);
        }else {
            resltInfo.setFlag(false);
            resltInfo.setErrorMsg("REGIS ERROR!!!");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resltInfo);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        UserService userService = new UserServiceImpl();
        User resultUser = userService.login(user);
        ResultInfo resultInfo = new ResultInfo();
        if (resultUser==null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Username Error Or Password Error");
        }
        if (resultUser!=null&&!"Y".equals(resultUser.getStatus())){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("User Need Active");
        }
        if (resultUser!=null&&"Y".equals(resultUser.getStatus())){
            req.getSession().setAttribute("user",resultUser);
            resultInfo.setFlag(true);
        }
        ObjectMapper objectMapper =new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(resp.getOutputStream(),resultInfo);
    }
    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute("user");
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(resp.getOutputStream(),user);
    }
    public void exit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
    public void active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        UserService userService = new UserServiceImpl();
        String code = req.getParameter("code");
        String msg = null;
        if (code!=null&&!code.isEmpty()){
            Boolean flag= userService.active(code);
            if (Boolean.TRUE.equals(flag)){
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
}
