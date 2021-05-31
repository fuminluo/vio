package priv.rabbit.vio.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.config.annotation.CustomAnnotation;
import priv.rabbit.vio.config.annotation.Decrypt;
import priv.rabbit.vio.config.annotation.Encrypt;
import priv.rabbit.vio.dto.user.LoginRequest;
import priv.rabbit.vio.entity.User;
import priv.rabbit.vio.mapper.UserMapper;
import priv.rabbit.vio.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Api(value = "UserController", description = "用户管理")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;


    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @GetMapping("/v1/app/users")
    public ResultInfo findUserList() {
        long start = System.currentTimeMillis();
        PageInfo<User> list = userService.findList();
        System.out.println("===== 耗时 : " + (System.currentTimeMillis() - start) + " ms");
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list.getList());
    }

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @GetMapping("/v1/app/user")
    public ResultInfo findUser(Long userId) {
        //userMapper.findNames();
        //userMapper.findCount();
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userMapper.selectByPrimaryKey(userId));
    }

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @GetMapping("/v1/app/user/name")
    public ResultInfo findUser(String name) {
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userMapper.findByName(name,"menuId123"));
    }

    @CustomAnnotation
    @ApiOperation(value = "保存", notes = "保存")
    @PostMapping("/v1/app/user/create")
    public ResultInfo save(@RequestParam String username, @RequestParam String password, @RequestParam Integer size) {
        return userService.save(username, password, size);
    }

    @Decrypt
    @Encrypt
    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping("/v1/app/user/register")
    public ResultInfo register(@Validated @RequestBody LoginRequest request) {
        return userService.register(request);
    }

    @Encrypt
    @PostMapping("/v1/app/user/test")
    public ResultInfo test(@Valid @RequestBody List<LoginRequest> list) {
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 登录
     *
     * @param request（用户名和密码）
     * @return
     */
    @CustomAnnotation
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("/v1/app/user/login")
    public ResultInfo login(@Valid @RequestBody LoginRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        User findUser = userMapper.findOneByParam(user);
        if (findUser == null) {
            return new ResultInfo(ResultInfo.FAILURE, "用户名或密码错误");
        }
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, userService.login(findUser));
    }


}
