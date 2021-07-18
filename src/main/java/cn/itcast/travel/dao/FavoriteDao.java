package cn.itcast.travel.dao;

public interface FavoriteDao {
    public boolean isFavorite(int rid, int uid);

    public int favoriteCount(int rid);

    public void addFavorite(int rid, int uid);
}
