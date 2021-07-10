package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao= new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        List<Category> categoryList = null;
        Jedis jedis = JedisUtil.getJedis();
//        Set<String> categorySet = jedis.zrange("category", 0, -1);
        Set<Tuple> categorySet = jedis.zrangeWithScores("category", 0, -1);
        if (categorySet == null || categorySet.size() == 0){
            System.out.println("Cache Not Found Data,Query For DataSource...");
            categoryList =  categoryDao.findAll();
            for (Category category : categoryList) {
                jedis.zadd("category", category.getCid(), category.getCname());
            }
        }else {
            System.out.println("Cache Found Success,Query For Redis...");
            categoryList = new ArrayList<>();
            for (Tuple tuple : categorySet){
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
