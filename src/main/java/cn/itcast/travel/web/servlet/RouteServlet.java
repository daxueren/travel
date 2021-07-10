package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
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

    protected void pageQuery(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String cidStr = req.getParameter("cid");
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
        if(cidStr != null && cidStr.length() > 0){
            cid = Integer.valueOf(cidStr);
        }

        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize);
        writeValue(routePageBean, resp);
    }

}
