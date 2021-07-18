package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String cidStr = req.getParameter("cid");
        String rname = req.getParameter("rname");

        int currentPage = 1;
        int pageSize = 5;
        int cid = 0;
        if(currentPageStr != null && currentPageStr.length() > 0){
//            valueOf底层调用了parseInt，返回一个Integer，而parseInt返回了一个int
//            currentPage = Integer.valueOf(currentPageStr);
            currentPage = Integer.parseInt(currentPageStr);
        }
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.valueOf(pageSizeStr);
        }
        if(cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)){
            cid = Integer.valueOf(cidStr);
        }

        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);
        writeValue(routePageBean, resp);
    }

    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String ridStr = req.getParameter("rid");
        int rid = 0;

        if(ridStr != null && ridStr.length() > 0 && !"null".equals(ridStr)){
            rid = Integer.valueOf(ridStr);
        }
        Route route = routeService.findOne(rid);
        writeValue(route, resp);
    }

    public void isFavorite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String ridStr = req.getParameter("rid");
        int rid = 0;
        if(ridStr != null && ridStr.length() > 0 && !"null".equals(ridStr)){
            rid = Integer.valueOf(ridStr);
        }
        User user = (User) req.getSession().getAttribute("user");
        int uid;
        if (user == null){
            uid = 0;
        }else {
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag, resp);

    }

    public void addFavorite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String ridStr = req.getParameter("rid");
        int rid = 0;
        if(ridStr != null && ridStr.length() > 0 && !"null".equals(ridStr)){
            rid = Integer.valueOf(ridStr);
        }
        User user = (User) req.getSession().getAttribute("user");
        int uid;
        if (user == null){
            uid = 0;
        }else {
            uid = user.getUid();
        }
        boolean flag = favoriteService.addFavorite(rid, uid);
    }
}
