package com.example.pracredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

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

//                long counter = jedis.incr("counter");
//                System.out.println(counter);
//                jedis.incrBy("counter",10L);
                long counter = jedis.decr("counter");
                counter = jedis.decrBy("counter",10L);
                System.out.println(counter);

                // NOTE : redis 파이프라인
                // SET * n -> SET * 1
                Pipeline pipeline  = jedis.pipelined();
                pipeline.set("users:400:email","email.com");
                pipeline.set("users:400:name","kwon");
                pipeline.set("users:400:age","15");
                List<Object> objects = pipeline.syncAndReturnAll();
                objects.forEach(i -> System.out.println(i.toString()));


            }
        }
    }
}
