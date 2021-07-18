package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Route> findByPage(int cid, int beginNum, int pagSize, String rname) {
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder stringBuilder = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0){
            stringBuilder.append(" and cid = ?");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0){
            stringBuilder.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        stringBuilder.append(" limit ?, ?");
        params.add(beginNum);
        params.add(pagSize);
        List<Route> routeList = jdbcTemplate.query(stringBuilder.toString(), new BeanPropertyRowMapper<>(Route.class), params.toArray());
        return routeList;
    }

    @Override
    public int findTotalCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder stringBuilder = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0){
            stringBuilder.append(" and cid = ?");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0){
            stringBuilder.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        int totalCount = jdbcTemplate.queryForObject(stringBuilder.toString(), Integer.class, params.toArray());
        return totalCount;
    }

    @Override
    public Route findOne(int rid) {
        Route route = null;
        String sql = "select * from tab_route where rid = ?";
        route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        return route;
    }
}
