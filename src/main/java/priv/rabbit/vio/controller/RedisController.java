package priv.rabbit.vio.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.*;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.config.annotation.Encrypt;
import priv.rabbit.vio.entity.User;

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


    // redisTemplate.opsForValue();//操作字符串
    // redisTemplate.opsForHash();//操作hash
    // redisTemplate.opsForList();//操作list
    // redisTemplate.opsForSet();//操作set
    // redisTemplate.opsForZSet();//操作有序set


    /**
     * 加锁
     *
     * @param lockkey
     * @param lockValue
     * @param lockTime
     * @return
     */
    @PostMapping(value = "/redis/lock")
    public ResultInfo lock(@RequestParam String lockkey, @RequestParam String lockValue, @RequestParam(defaultValue = "10") Long lockTime) {
        //尝试枷锁
        boolean lock = redisTemplate.opsForValue().setIfAbsent(lockkey, lockValue);
        LOG.info(" >>> 加锁 : {}", lock);
        if (lock) {
            //获取锁成功
            LOG.info("start lock lockNxExJob success");
            redisTemplate.opsForValue().set(lockkey, lockValue, lockTime, TimeUnit.SECONDS);
        }
        return ResultInfo.OK(lock);
    }

    /**
     * 解锁
     */
    @PostMapping(value = "/redis/un-lock")
    public ResultInfo unLock(@RequestParam String lockkey) {
        boolean lock = redisTemplate.delete(lockkey);
        LOG.info(" >>> 解锁锁 : {}", lock);
        return ResultInfo.OK(lock);
    }


    /**
     * 加锁
     *
     * @param lockkey
     * @param lockValue
     * @param lockTime
     * @return
     */
    @PostMapping(value = "/redis/lua/lock")
    public ResultInfo luaLock(@RequestParam String lockkey, @RequestParam String lockValue, @RequestParam(defaultValue = "10") Long lockTime) {
        DefaultRedisScript<Boolean> lockScript = new DefaultRedisScript<Boolean>();
        lockScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("redis/redisLock.lua")));
        lockScript.setResultType(Boolean.class);
        // 封装参数
        List<Object> keyList = new ArrayList<Object>();
        keyList.add(lockkey);
        keyList.add(lockValue);
        keyList.add(lockTime.toString());
        Boolean result = (Boolean) redisTemplate.execute(lockScript, keyList);
        LOG.info("》》》 加锁结果 redis set result：" + result);
        return ResultInfo.OK(result);
    }


    /**
     * 字符串
     */
    @GetMapping(value = "/redis/string")
    public void stringOperation(String key) {
        // key、value
        for (int i = 0; i < 2; i++) {
            //stringRedisTemplate.opsForValue().set("lock-test" + i, "lock" + i);
            stringRedisTemplate.opsForValue().set("test" + i, "value", 1, TimeUnit.SECONDS);
        }
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


        // 取数据
        List<String> resultList1 = (List<String>) redisTemplate.opsForList().leftPop("listkey1");
        List<String> resultList2 = (List<String>) redisTemplate.opsForList().rightPop("listkey2");

        System.out.println("resultList1:" + resultList1);
        System.out.println("resultList2:" + resultList2);


        //从左边插入，即插入到列表头部
        redisTemplate.opsForList().leftPush("product:list", "iphone xs max");
        redisTemplate.opsForList().leftPush("product:list", "thinkpad x1 carbon");
        redisTemplate.opsForList().leftPush("product:list", "mackBook pro13");
        redisTemplate.opsForList().leftPush("product:list", "HuaWei Mate20 pro");


        //查询list中指定范围的内容
        List<String> list = redisTemplate.opsForList().range("product:list", 0, -1);
        System.out.println("查询list中指定范围的内容  "+list);

        //修剪列表，使其只包含指定范围内的元素
        redisTemplate.opsForList().trim("product:list", 0, 2);

        //查询列表长度
        System.out.println("查询列表长度  "+redisTemplate.opsForList().size("product:list"));

        //弹出最左边元素
        redisTemplate.opsForList().leftPop("product:list");
        //移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时。
        redisTemplate.opsForList().leftPop("k1", 10, TimeUnit.SECONDS);

        //弹出最右边元素
        redisTemplate.opsForList().rightPop("product:list");
        //弹出k1最右边元素并放入k2最左边
        redisTemplate.opsForList().rightPopAndLeftPush("product:list", "game:list");
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


}