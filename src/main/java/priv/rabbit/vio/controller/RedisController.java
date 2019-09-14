package priv.rabbit.vio.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import priv.rabbit.vio.config.annotation.Encrypt;
import priv.rabbit.vio.config.redis.DistributedLocker;
import priv.rabbit.vio.entity.User;
import priv.rabbit.vio.service.RedisLockService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author LuoFuMin
 * @data 2018/7/17
 */
@Api(value = "RedisController", description = "redis案例")
@RestController
public class RedisController {

    private static Logger LOG = LoggerFactory.getLogger(RedisController.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    RedisLockService redisLockService;


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
        redisTemplate.opsForValue().set("key-a", "aaa", 3, TimeUnit.SECONDS);
        System.out.println("》》》》》》" + redisTemplate.opsForValue().get("key-a"));
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

        System.out.println("====" + redisTemplate.opsForList().leftPop("list"));


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


    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public Map<String, Object> firstResp(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    @Encrypt
    public Object sessions(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("map"));
        return map;
    }


    /**
     *  分布式锁
     * @param waitTime 获取锁等待时间
     * @param lesaeTime 锁默认超时时间
     * @param sleepTime 县城睡眠时间
     * @return
     */
    @GetMapping("/redisson")
    public String  redissonTest(@RequestParam(required = false,defaultValue = "3") Long waitTime, @RequestParam(required = false,defaultValue = "10") Long lesaeTime, @RequestParam(required = false,defaultValue = "15") Long sleepTime) {
        redisLockService.tryLock(waitTime, lesaeTime, sleepTime);
        return  new Date()+"成功";
    }

}