package com.example.pracredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class Main_3 {

    public static void main(String[] args) {
        System.out.println("Data Types Hash");

        try(var jedisPool =  new JedisPool("127.0.0.1",6379)) {
            try(Jedis jedis = jedisPool.getResource()) {

                //hash
                // 1. key / field / value
                jedis.hset("users:2:info","name","kwongyumin");

                var map = new HashMap<String, String>();
                map.put("email","email@email.com");
                map.put("phone","010-xxxx-xxxx");
                // 2. key / map (key,value)
                jedis.hset("users:2:info",map);

                // hdel
                jedis.hdel("users:2:info","phone");

                // hget , getall
                System.out.println(jedis.hget("users:2:info","email")); // field 추출

                Map<String,String> user2Info = jedis.hgetAll("users:2:info");
                user2Info.forEach((k,v) -> System.out.printf("%s %s%n",k,v));


                // hincrby -> 증감
                jedis.hincrBy("users:2:info","visit",1);

            }
        }

    }
}
