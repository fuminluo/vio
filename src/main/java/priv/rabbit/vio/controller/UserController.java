package priv.rabbit.vio.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.config.target.CustomAnnotation;
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
    private UserMapper userMapper;



    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @GetMapping("/v1/app/users")
    public ResultInfo findUserList() {
        stringRedisTemplate.opsForValue().set("sprinboot-redis-messaage", "message", 10, TimeUnit.SECONDS);
        System.out.println("》》》8080");
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS,userService.findList());
    }


    @CustomAnnotation
    @ApiOperation(value = "保存", notes = "保存")
    @PostMapping("/v1/app/user/create")
    public ResultInfo save(@RequestParam String username, @RequestParam String password) {
        return userService.save(username,password);
    }

    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping("/v1/app/user/register")
    public ResultInfo register(@Validated @RequestBody LoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.register(request);
    }


    @PostMapping("/v1/app/user/test")
    public ResultInfo test(@Valid @RequestBody List<LoginRequest> list, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, bindingResult.getFieldError().getDefaultMessage());
        }
        return new ResultInfo(ResultInfo.SUCCESS,ResultInfo.MSG_SUCCESS,list);
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
