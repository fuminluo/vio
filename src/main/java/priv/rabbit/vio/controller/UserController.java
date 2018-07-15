package priv.rabbit.vio.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.dto.user.LoginRequest;
import priv.rabbit.vio.service.UserService;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/app/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @ApiOperation(value = "获取用户列表", notes = "")
    @GetMapping("v1/find/list")
    public Object findUserList() {
        stringRedisTemplate.opsForValue().set("sprinboot-redis-messaage", "message", 10, TimeUnit.SECONDS);
        System.out.println("》》》8080");
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, "123456");
    }


    @ApiOperation(value = "注册", notes = "")
    @GetMapping("v1/create")
    public Object save() {
        return userService.save();
    }

    @PostMapping("v1/register")
    public Object register(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.register(request);
    }

    /**
     * 登录
     *
     * @param request（用户名和密码）
     * @param bindingResult
     * @return
     */
    @PostMapping("v1//login")
    public Object login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.login(request);
    }



}
