package com.lei.main.comm.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository("redisUtil")
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间
     * @param unit 时间单位
     * @return
     */
    public boolean expire(String key,long time,TimeUnit unit){
        try {
            if(time>0){
                redisTemplate.expire(key, time, unit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    public void set(String key, Object value) {
        if(key == null || "".equals(key)){
            return;
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public Object get(String key) {
        if(key == null || "".equals(key)){
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    public void setList(String key, List<?> value) {
        if(key == null || "".equals(key)){
            return;
        }
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush(key, value);
    }

    public Object getList(String key) {
        if(key == null || "".equals(key)){
            return null;
        }
        return redisTemplate.opsForList().leftPop(key);
    }

    public void setSet(String key, Set<?> value) {
        if(key == null || "".equals(key)){
            return;
        }
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add(key, value);
    }

    public Object getSet(String key) {
        if(key == null || "".equals(key)){
            return null;
        }
        return redisTemplate.opsForSet().members(key);
    }

    public void setHash(String key, Map<String, ?> value) {
        if(key == null || "".equals(key)){
            return;
        }
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, value);
    }

    public Object getHash(String key) {
        if(key == null || "".equals(key)){
            return null;
        }
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置hash中一个项的值
     * @param key 键值
     * @param item 项名
     * @param value 值
     */
    public void setHashItem(String key, String item, Object value) {
        if(key == null || "".equals(key) || item == null || "".equals(item)){
            return;
        }
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, item, value);
    }
    /**
     * 获取hash中一个项
     * @param key 键值
     * @param item 项名
     * @return
     */
    public Object getHashItem(String key, String item) {
        if(key == null || "".equals(key) || item == null || "".equals(item)){
            return null;
        }
        return redisTemplate.opsForHash().get(key,item);
    }
    /**
     * hash递增 如果不存在,就会创建一个
     * @param key 键值
     * @param item 项名
     * @param num 数值
     * @return
     */
    public double incrHash(String key, String item,double num){
        return redisTemplate.opsForHash().increment(key, item, num);
    }
    /**
     * hash递减
     * @param key 键值
     * @param item 项名
     * @param num 数值
     * @return
     */
    public double decrHash(String key, String item,double num){
        return redisTemplate.opsForHash().increment(key, item,-num);
    }

    public void delete(String key) {
        if(key == null || "".equals(key)){
            return;
        }
        redisTemplate.delete(key);
    }
    /**
     * 删除hash中某个项
     * @param key 键名
     * @param item 项名
     */
    public void deleteHashItem(String key, String item) {
        if(key == null || "".equals(key)){
            return;
        }
        redisTemplate.opsForHash().delete(key, item);
    }
    /**
     * 判断是否存在为key的hash，以及对应项
     * @param key 键名
     * @param item 项名
     * @return
     */
    public boolean existHashItem(String key, String item){
        if(key == null || "".equals(key)){
            return false;
        }
        return redisTemplate.opsForHash().hasKey(key, item);
    }
    /**
     * 查询 key中对应多少条数据
     * @param key 键名
     * @return
     */
    public long hashSize(String key) {
        if(key == null || "".equals(key)){
            return 0L;
        }
        return redisTemplate.opsForHash().size(key);
    }
    /**
     * 更换当前请求的连接数据库
     * @param db 数据库编号
     */
    public void changeDb(int db) {
        JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory)redisTemplate.getConnectionFactory();
        jedisConnectionFactory.setDatabase(db);
    }
}
