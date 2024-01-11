package com.example.pracredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        try (var jedisPool = new JedisPool("127.0.0.1",6379)) {
            try (Jedis jedis = jedisPool.getResource()) {

                jedis.set("users:300:email","email.com");
                jedis.set("users:300:name","kwon");
                jedis.set("users:300:age","29");

                var userEmail = jedis.get("users:300:email");
                System.out.println(userEmail);

                List<String> userInfo = jedis.mget("users:300:email","users:300:name","users:300:age");
                userInfo.forEach(System.out::println);
            }
        }
    }
}
