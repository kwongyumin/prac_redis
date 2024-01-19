package com.example.pracredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class Main_2 {
    public static void main(String[] args) {
        System.out.println("Data Types List And Set");

        try (var jedisPool = new JedisPool("127.0.0.1",6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // list
                // 1.stack 선입후출
                jedis.rpush("stack1","aaaa");
                jedis.rpush("stack1","bbbb");
                jedis.rpush("stack1","cccc");

                List<String> stack1 = jedis.lrange("stack1",0,-1);
                stack1.forEach(System.out::println);

                System.out.println(jedis.rpop("stack1"));
                System.out.println(jedis.rpop("stack1"));
                System.out.println(jedis.rpop("stack1"));

                // 2.queue 선입선출
                jedis.rpush("queue2","zzzz");
                jedis.rpush("queue2","aaaa");
                jedis.rpush("queue2","cccc");

                System.out.println(jedis.lpop("queue2"));
                System.out.println(jedis.lpop("queue2"));
                System.out.println(jedis.lpop("queue2"));

                // 3.block brpop blpop
                // key 에 담긴 value 없음 , -> 타임아웃 10초
                List<String> blpop = jedis.blpop(10,"queue:blocking");
                if (blpop != null) {
                    blpop.forEach(System.out::println);
                }

            };
        }

    }

}
