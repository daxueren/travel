package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDapImpl implements SellerDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findSellerBySid(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        Seller seller = null;
        seller = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        return seller;
    }
}
