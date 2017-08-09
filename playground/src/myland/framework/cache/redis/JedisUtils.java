package com.myland.framework.cache.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;
import java.util.Set;

@Slf4j
public class JedisUtils {
	/**
	 * 非切片连接池
	 */
	private JedisPool jedisPool;
	/**
	 * 切片连接池，分布式集群
	 */
	private ShardedJedisPool shardedJedisPool;
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}
	/**
	 * 
	 * @Title: returnResource
	 * @Description:回收资源
	 * @param 
	 * @return
	 * @throws
	 */
	public void returnResource(JedisPool jedisPool,Jedis jedis){
		if(jedis!=null){
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 
	 * @Title: returnBrokenResource
	 * @Description: 回收资源
	 * @param 
	 * @return
	 * @throws
	 */
	public void returnBrokenResource(JedisPool jedisPool,Jedis jedis){
		if(jedis!=null){
			jedisPool.returnBrokenResource(jedis);
		}
	}
	/**
	 * 
	 * @Title: returnResource
	 * @Description: 回收资源
	 * @param 
	 * @return
	 * @throws
	 */
	public void returnResource(ShardedJedis resource){
		shardedJedisPool.returnResource(resource);
	}
	/**
	 * 
	 * @Title: returnBrokenResource
	 * @Description: 回收资源
	 * @param 
	 * @return
	 * @throws
	 */
	public void returnBrokenResource(ShardedJedis resource){
		shardedJedisPool.returnBrokenResource(resource);
	}
	/** 
     * 设置一个key的过期时间（单位：秒） 
     * @param key key值 
     * @param seconds 多少秒后过期 
     * @return 1：设置了过期时间  0：没有设置过期时间/不能设置过期时间 
     */  
    public long expire(String key, int seconds) {  
        if (key==null || key.equals("")) {  
            return 0;  
        }  
  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.expire(key, seconds);  
        } catch (Exception ex) {  
            log.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + ex.getMessage(), ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    } 
    /** 
     * 设置一个key在某个时间点过期 
     * @param key key值 
     * @param unixTimestamp unix时间戳，从1970-01-01 00:00:00开始到现在的秒数 
     * @return 1：设置了过期时间  0：没有设置过期时间/不能设置过期时间 
     */  
    public long expireAt(String key, int unixTimestamp) {  
        if (key==null || key.equals("")) {  
            return 0;  
        }  
  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.expireAt(key, unixTimestamp);  
        } catch (Exception ex) {  
            log.error("EXPIRE error[key=" + key + " unixTimestamp=" + unixTimestamp + "]" + ex.getMessage(), ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    } 
    /**
	 * 
	 * @Title: flushDB
	 * @Description: 清空缓存
	 * @param 
	 * @return
	 * @throws
	 */
	public void flushDB(){
		Jedis jedis=null;	
		try{
			jedis=jedisPool.getResource();
			jedis.flushDB();
		}catch(Exception e){
			e.printStackTrace();
			returnBrokenResource(jedisPool,jedis);
		}finally{
			returnResource(jedisPool,jedis);
		}
	}
    /******************************************LIST**********************************************/
    /** 
     * 截断一个List 
     * @param key 列表key 
     * @param start 开始位置 从0开始 
     * @param end 结束位置 
     * @return 状态码 
     */  
    public String trimList(String key, long start, long end) {  
        if (key == null || key.equals("")) {  
            return "";  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.ltrim(key, start, end);  
        } catch (Exception ex) {  
            log.error("LTRIM 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage() , ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return "";  
    } 
    /**
	 * 
	 * @Title: removeListValue
	 * @Description: 从list中删除value
	 * @param key
	 * @param count 删除个数
	 * @param value
	 * @return
	 */
	public  boolean removeListValue(String key,long count,String value){  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.lrem(key, count, value);  
            return true;  
        } catch (Exception ex) {  
            log.error("getList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }
	/**
	 * 
	 * @Title: removeListValue
	 * @Description: TODO
	 * @param key
	 * @param values
	 * @return
	 */
	public  int removeListValue(String key,List<String> values){  
        return removeListValue(key, 1, values);  
    } 
	/**
	 * 
	 * @Title: removeListValue
	 * @Description: TODO
	 * @param key
	 * @param count
	 * @param values
	 * @return
	 */
	public  int removeListValue(String key,long count,List<String> values){  
        int result = 0;  
        if(values != null && values.size()>0){  
            for(String value : values){  
                if(removeListValue(key, count, value)){  
                    result++;  
                }  
            }  
        }  
        return result;  
    } 
	/**
	 * 
	 * @Title: rangeList
	 * @Description: 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> rangeList(String key, long start, long end) {  
        if (key == null || key.equals("")) {  
            return null;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.lrange(key, start, end);  
        } catch (Exception ex) {  
            log.error("rangeList 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage() , ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    } 
	/**
	 * 
	 * @Title: countList
	 * @Description: 返回列表 key 的长度
	 * @param key
	 * @return
	 */
	public long countList(String key){  
        if(key == null ){  
            return 0;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.llen(key);  
        } catch (Exception ex) {  
            log.error("countList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    } 
	/**
	 * 
	 * @Title: getList
	 * @Description: 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
	 * @param key
	 * @return
	 */
	public  List<String> getList(String key){  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.lrange(key, 0, -1);  
        } catch (Exception ex) {  
            log.error("getList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }
    /******************************************SET**********************************************/
    
    /** 
     * 检查Set长度 
     * @param key 
     * @return 
     */  
    public long countSet(String key){  
        if(key == null ){  
            return 0;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.scard(key);  
        } catch (Exception ex) {  
            log.error("countSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    }
    /** 
     * 添加到Set中（同时设置过期时间） 
     * @param key key值 
     * @param seconds 过期时间 单位s 
     * @param value 
     * @return 
     */  
    public boolean addSet(String key,int seconds, String value) {  
        boolean result = addSet(key, value);  
        if(result){  
            long i = expire(key, seconds);  
            return i==1;  
        }  
        return false;  
    }
	/**
	 * 
	 * @Title: sadd
	 * @Description: 在set集合添加key和对应value值
	 * @param 
	 * @return
	 * @throws
	 */
	public void sadd(String key,String... value){
		Jedis jedis=null;		
		try{
			jedis=jedisPool.getResource();
			jedis.sadd(key, value);
		}catch(Exception e){
			e.printStackTrace();
			returnBrokenResource(jedisPool,jedis);
		}finally{
			returnResource(jedisPool,jedis);
		}
	}
	
	/**
	 * 
	 * @Title: sinter
	 * @Description: Set集合交集
	 * @param 
	 * @return
	 * @throws
	 */
	public Set sinter(String... keys){
		Jedis jedis=null;	
		Set set=null;
		try{
			jedis=jedisPool.getResource();
			set=jedis.sinter(keys);
		}catch(Exception e){
			e.printStackTrace();
			returnBrokenResource(jedisPool,jedis);
		}finally{
			returnResource(jedisPool,jedis);
		}
		return set;
	}
	/**
	 * 
	 * @Title: addSet
	 * @Description: Set集合添加数据
	 * @param 
	 * @return
	 * @throws
	 */
	public boolean addSet(String key, String... value) {  
        if(key == null || value == null){  
            return false;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.sadd(key, value);  
            return true;  
        } catch (Exception ex) {    
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
	
	/**
	 * 
	 * @Title: containsInSet
	 * @Description: 判断值是否包含在set中
	 * @param 
	 * @return
	 * @throws
	 */
	public boolean containsInSet(String key, String value) {  
        if(key == null || value == null){  
            return false;  
        }  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.sismember(key, value);  
        } catch (Exception ex) {  
            log.error("setList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }  
	/**
	 * 
	 * @Title: getSet
	 * @Description: 从set集合取得key对应的value值
	 * @param key
	 * @return
	 */
	public  Set<String> get4Set(String key){  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.smembers(key);  
        } catch (Exception ex) {  
            log.error("getList error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }
	/******************************************STRING**********************************************/
	/**
	 * 
	 * @Title: set
	 * @Description: 设置key对应value值
	 * @param 
	 * @return
	 * @throws
	 */
	public void set(String key,String value){
		Jedis jedis=null;		
		try{
			jedis=jedisPool.getResource();
			jedis.set(key, value);
		}catch(Exception e){
			e.printStackTrace();
			returnBrokenResource(jedisPool,jedis);
		}finally{
			returnResource(jedisPool,jedis);
		}
	}
	/**
	 * 
	 * @Title: get
	 * @Description: 获取key对应的value值
	 * @param 
	 * @return
	 * @throws
	 */
	public String get(String key){
		Jedis jedis=null;	
		String str=null;
		try{
			jedis=jedisPool.getResource();
			str=jedis.get(key);
		}catch(Exception e){
			e.printStackTrace();
			returnBrokenResource(jedisPool,jedis);
		}finally{
			returnResource(jedisPool,jedis);
		}
		return str;
	}
	/**
	 * 
	 * @Title: getSet
	 * @Description: 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
		当 key 存在但不是字符串类型时，返回一个错误。
	 * @param key
	 * @param value
	 * @return
	 */
	public String getSet(String key,String value){
		Jedis jedis=null;	
		String str=null;
		try{
			jedis=jedisPool.getResource();
			str=jedis.getSet(key, value);
		}catch(Exception e){
			e.printStackTrace();
			returnBrokenResource(jedisPool,jedis);
		}finally{
			returnResource(jedisPool,jedis);
		}
		return str;
	}
	/**
	 * 
	 * @Title: set
	 * @Description: 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)
	 * @param key
	 * @param value
	 * @param second
	 * @return
	 */
	public boolean set(String key, String value, int second) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.setex(key, second, value);  
            return true;  
        } catch (Exception ex) {  
            log.error("set error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }
	/**
	 * 
	 * @Title: setnx
	 * @Description: 将 key 的值设为 value ，当且仅当 key 不存在。
		若给定的 key 已经存在，则 SETNX 不做任何动作SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setnx(String key, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.setnx(key, value);
            return true;  
        } catch (Exception ex) {  
            log.error("set error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }
	/**
	 * 
	 * @Title: setString
	 * @Description: 将字符串值 value 关联到 key 。
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setString(String key, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.set(key, value);  
            return true;  
        } catch (Exception ex) {  
            log.error("set error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    } 
	/**
	 * 
	 * @Title: get
	 * @Description: 返回 key 所关联的字符串值。
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String get(String key, String defaultValue) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.get(key) == null?defaultValue:shardedJedis.get(key);  
        } catch (Exception ex) {  
            log.error("get error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return defaultValue;  
    } 
	/**
	 * 
	 * @Title: incr
	 * @Description: 将 key 中储存的数字值增一。
	 * @param key
	 * @return
	 */
	public long incr(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.incr(key);  
        } catch (Exception ex) {  
            log.error("incr error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    }
	/**
	 * 
	 * @Title: decr
	 * @Description: 将 key 中储存的数字值减一。
	 * @param key
	 * @return
	 */
	public long decr(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.decr(key);  
        } catch (Exception ex) {  
            log.error("incr error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0;  
    } 
	/******************************************KEY**********************************************/
	/**
	 * 
	 * @Title: del
	 * @Description: 删除给定的一个或多个 key 。
	 * @param key
	 * @return
	 */
	public boolean del(String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.del(key);  
            return true;  
        } catch (Exception ex) {  
            log.error("del error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }
	
	/******************************************HASH**********************************************/
	/**
	 * 
	 * @Title: setHSet
	 * @Description: 将哈希表 key 中的域 field 的值设为 value 
	 * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖
	 * @param domain
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setHSet(String domain, String key, String value) {  
        if (value == null) return false;  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.hset(domain, key, value);  
            return true;  
        } catch (Exception ex) {  
            log.error("setHSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    } 
	/**
	 * 
	 * @Title: getHSet
	 * @Description: 返回哈希表 key 中给定域 field 的值。
	 * @param domain
	 * @param key
	 * @return
	 */
	public String getHSet(String domain, String key) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.hget(domain, key);  
        } catch (Exception ex) {  
            log.error("getHSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    } 
	/**
	 * 
	 * @Title: delHSet
	 * @Description: 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * @param domain
	 * @param key
	 * @return
	 */
	public long delHSet(String domain, String key) {  
        ShardedJedis shardedJedis = null;  
        long count = 0;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            count = shardedJedis.hdel(domain, key);  
        } catch (Exception ex) {  
            log.error("delHSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return count;  
    }  
	/**
	 * 
	 * @Title: existsHSet
	 * @Description: 查看哈希表 key 中，给定域 field 是否存在
	 * @param domain
	 * @param key
	 * @return
	 */
	public boolean existsHSet(String domain, String key) {  
        ShardedJedis shardedJedis = null;  
        boolean isExist = false;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            isExist = shardedJedis.hexists(domain, key);  
        } catch (Exception ex) {  
            log.error("existsHSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return isExist;  
    }
	/**
	 * 
	 * @Title: hvals
	 * @Description: 返回哈希表 key 中所有域的值。
	 * @param domain
	 * @return
	 */
	public List<String> hvals(String domain) {  
        ShardedJedis shardedJedis = null;  
        List<String> retList = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            retList = shardedJedis.hvals(domain);  
        } catch (Exception ex) {  
            log.error("hvals error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return retList;  
    }  
	/**
	 * 
	 * @Title: hkeys
	 * @Description: 返回哈希表 key 中的所有域
	 * @param domain
	 * @return
	 */
	public Set<String> hkeys(String domain) {  
        ShardedJedis shardedJedis = null;  
        Set<String> retList = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            retList = shardedJedis.hkeys(domain);  
        } catch (Exception ex) {  
            log.error("hkeys error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return retList;  
    } 
	/**
	 * 
	 * @Title: lenHset
	 * @Description: 返回哈希表 key 中域的数量
	 * @param domain
	 * @return
	 */
	public long lenHset(String domain) {  
        ShardedJedis shardedJedis = null;  
        long retList = 0;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            retList = shardedJedis.hlen(domain);  
        } catch (Exception ex) {  
            log.error("hkeys error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return retList;  
    }
	/******************************************SortedSet**********************************************/
	/**
	 * 
	 * @Title: setSortedSet
	 * @Description: 将一个或多个 member 元素及其 score 值加入到有序集 key 当中
	 * @param key
	 * @param score
	 * @param value
	 * @return
	 */
	public boolean setSortedSet(String key, long score, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            shardedJedis.zadd(key, score, value);  
            return true;  
        } catch (Exception ex) {  
            log.error("setSortedSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }
	/**
	 * 
	 * @Title: getSoredSet
	 * @Description: 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列。或
	 * 回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列
	 * @param key
	 * @param startScore
	 * @param endScore
	 * @param orderByDesc
	 * @return
	 */
	public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            if (orderByDesc) {  
                return shardedJedis.zrevrangeByScore(key, endScore, startScore);  
            } else {  
            	//回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列
                return shardedJedis.zrangeByScore(key, startScore, endScore);  
            }  
        } catch (Exception ex) {  
            log.error("getSoredSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }
	/**
	 * 
	 * @Title: countSoredSet
	 * @Description: 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
	 * @param key
	 * @param startScore
	 * @param endScore
	 * @return
	 */
	public long countSoredSet(String key, long startScore, long endScore) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            Long count = shardedJedis.zcount(key, startScore, endScore);  
            return count == null ? 0L : count;  
        } catch (Exception ex) {  
            log.error("countSoredSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return 0L;  
    } 
	/**
	 * 
	 * @Title: delSortedSet
	 * @Description: TODO
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean delSortedSet(String key, String value) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            long count = shardedJedis.zrem(key, value);  
            return count > 0;  
        } catch (Exception ex) {  
            log.error("delSortedSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return false;  
    }
	/**
	 * 
	 * @Title: getSoredSetByRange
	 * @Description: TODO
	 * @param key
	 * @param startRange
	 * @param endRange
	 * @param orderByDesc
	 * @return
	 */
	public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            if (orderByDesc) {  
            	//返回有序集 key 中，指定区间内的成员其中成员的位置按 score 值递减(从大到小)来排列。
                return shardedJedis.zrevrange(key, startRange, endRange);  
            } else {  
            	//返回有序集 key 中，指定区间内的成员。其中成员的位置按 score 值递增(从小到大)来排序。
                return shardedJedis.zrange(key, startRange, endRange);  
            }  
        } catch (Exception ex) {  
            log.error("getSoredSetByRange error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    }
	/**
	 * 
	 * @Title: getScore
	 * @Description: 返回有序集 key 中，成员 member 的 score 值。
	 * @param key
	 * @param member
	 * @return
	 */
	public Double getScore(String key, String member) {  
        ShardedJedis shardedJedis = null;  
        try {  
            shardedJedis = shardedJedisPool.getResource();  
            return shardedJedis.zscore(key, member);  
        } catch (Exception ex) {  
            log.error("getSoredSet error.", ex);  
            returnBrokenResource(shardedJedis);  
        } finally {  
            returnResource(shardedJedis);  
        }  
        return null;  
    } 
}
