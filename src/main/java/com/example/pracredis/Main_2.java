package com.example.pracredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

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

                // set

                jedis.sadd("users:500:follow","100","200","300"); // key / value 입력
                jedis.srem("users:500:follow","100"); // 삭제

                Set<String> smembers = jedis.smembers("users:500:follow");
                smembers.forEach(System.out::println);

                jedis.sismember("users:500:follow", "200"); // true
                jedis.sismember("users:500:follow", "120"); // false

                jedis.scard("users:500:follow"); // 갯수 파악

                // 중복되는 value 추출
                Set<String> sinter = jedis.sinter("users:500:follow","users:100:follow");

            };
        }

    }

}
