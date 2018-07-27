package priv.rabbit.vio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.rabbit.vio.entity.User;

import java.util.*;

/**
 * @author LuoFuMin
 * @data 2018/7/17
 */
@RestController
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    // redisTemplate.opsForValue();//操作字符串
    // redisTemplate.opsForHash();//操作hash
    // redisTemplate.opsForList();//操作list
    // redisTemplate.opsForSet();//操作set
    // redisTemplate.opsForZSet();//操作有序set

    /**
     * 字符串
     */
    @GetMapping(value = "/redis/string")
    public void stringOperation() {
        // key、value
        redisTemplate.opsForValue().set("name", "aaa");

        System.out.println("》》》》》》" + redisTemplate.opsForValue().get("name"));
    }

    /**
     * 集合
     */
    @GetMapping(value = "/redis/set")
    public void setOperation() {
        Set<String> set1 = new HashSet<String>();
        set1.add("set1");
        set1.add("set2");
        set1.add("set3");
        // 插入
        redisTemplate.opsForSet().add("set1", set1);
        // 获取
        Set<String> resultSet = redisTemplate.opsForSet().members("set1");
        System.out.println("resultSet:" + resultSet);
    }

    /**
     * Map
     */
    @GetMapping(value = "/redis/map")
    public void mapOperation() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.put("key5", "value5");
        // 插入
        redisTemplate.opsForHash().putAll("map1", map);

        Map<String, String> resultMap = redisTemplate.opsForHash().entries("map1");
        System.out.println("resultMap:" + resultMap);

        List<String> reslutMapList = redisTemplate.opsForHash().values("map1");
        System.out.println("reslutMapList:" + reslutMapList);

        Set<String> resultMapSet = redisTemplate.opsForHash().keys("map1");
        System.out.println("resultMapSet:" + resultMapSet);

        String value = (String) redisTemplate.opsForHash().get("map1", "key1");
        System.out.println("value:" + value);
    }

    /**
     * List
     */
    @GetMapping(value = "/redis/list")
    public void listOperation() {
        List<String> list1 = new ArrayList<String>();
        list1.add("a1");
        list1.add("a2");
        list1.add("a3");

        List<String> list2 = new ArrayList<String>();
        list2.add("b1");
        list2.add("b2");
        list2.add("b3");
        // 插入
        redisTemplate.opsForList().leftPush("listkey1", list1);
        redisTemplate.opsForList().rightPush("listkey2", list2);

        redisTemplate.opsForList().leftPush("list", "a");
        redisTemplate.opsForList().leftPush("list", "b");

        // 取数据
        List<String> resultList1 = (List<String>) redisTemplate.opsForList().leftPop("listkey1");
        List<String> resultList2 = (List<String>) redisTemplate.opsForList().rightPop("listkey2");

        System.out.println("resultList1:" + resultList1);
        System.out.println("resultList2:" + resultList2);

        System.out.println("===="+redisTemplate.opsForList().leftPop("list"));


    }

    /**
     * Student
     */
    @GetMapping(value = "/redis/student")
    public void studentOperation() {
        List<User> userArrayList = new ArrayList<User>();

        List<User> userList = new ArrayList<User>();

        User user = new User();
        user.setId((long) 1);
        user.setUsername("xiao");

        User user2 = new User();
        user2.setId((long) 2);
        user2.setUsername("daopao");

        userArrayList.add(user);
        userArrayList.add(user2);

        redisTemplate.opsForValue().set("userArrayList", userArrayList);
        // Object object = redisTemplate.opsForValue().get("studentList");

        User user3 = new User();
        user3.setId((long) 3);
        user3.setUsername("ccc");
        userList.add(user3);

        // 强转
        List<User> resultList = (List<User>) redisTemplate.opsForValue().get("userArrayList");

        if (resultList != null && resultList.size() > 0) {
            for (User u : resultList) {
                System.out.println(">>>>>>>" + u.getUsername());
            }
        }

    }
}