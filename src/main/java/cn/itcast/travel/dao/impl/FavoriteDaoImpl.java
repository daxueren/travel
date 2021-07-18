package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public boolean isFavorite(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        List<Favorite> favoriteList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        if (favoriteList != null && favoriteList.size() > 0){
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    @Override
    public int favoriteCount(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, rid);
        return count;
    }

    @Override
    public void addFavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?, ?, ?)";
        jdbcTemplate.update(sql, rid, new Date(), uid);
    }
}
