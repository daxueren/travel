package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    public List<Route> findByPage(int cid, int beginNum, int pageSize, String rname);

    public int findTotalCount(int cid, String rname);

    public Route findOne(int rid);
}
