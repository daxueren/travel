package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int current, int pageSize) {
        PageBean<Route> pageBean = new PageBean<>();
        int totalPage = 0;
        int totalCount = routeDao.findTotalCount(cid);
        pageBean.setTotalCount(totalCount);
        pageBean.setCurrentPage(current);
        pageBean.setPageSize(pageSize);
        if (totalCount%pageSize == 0){
            totalPage = totalCount/pageSize;
        }else {
            totalPage = totalCount/pageSize + 1;
        }
        pageBean.setTotalPage(totalPage);
        int beginNum = (current - 1) * pageSize;
        pageBean.setList(routeDao.findByPage(cid, beginNum, pageSize));
        return pageBean;
    }
}
