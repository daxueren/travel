package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    public PageBean<Route> pageQuery(int cid, int current, int pageSize, String rname);

    public Route findOne(int rid);
}
