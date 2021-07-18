package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import com.alibaba.druid.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findRouteImgListByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        List<RouteImg> routeImgList = null;
        routeImgList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
        return routeImgList;
    }
}
