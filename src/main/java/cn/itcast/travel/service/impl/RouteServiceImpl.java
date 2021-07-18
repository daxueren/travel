package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDapImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private SellerDao sellerDao = new SellerDapImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int current, int pageSize, String rname) {
        PageBean<Route> pageBean = new PageBean<>();
        int totalPage = 0;
        int totalCount = routeDao.findTotalCount(cid, rname);
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
        pageBean.setList(routeDao.findByPage(cid, beginNum, pageSize, rname));
        return pageBean;
    }

    @Override
    public Route findOne(int rid) {
        Route route = routeDao.findOne(rid);

        int sid = route.getSid();
        Seller seller = sellerDao.findSellerBySid(sid);

        List<RouteImg> routeImgList = routeImgDao.findRouteImgListByRid(rid);

        int favoriteCount = favoriteDao.favoriteCount(rid);

        route.setRouteImgList(routeImgList);
        route.setSeller(seller);
        route.setCount(favoriteCount);

        return route;

    }
}
