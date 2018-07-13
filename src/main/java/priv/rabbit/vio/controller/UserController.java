package priv.rabbit.vio.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.dto.user.LoginRequest;
import priv.rabbit.vio.service.UserService;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @ApiOperation(value = "获取用户列表", notes = "")
    @GetMapping("api/app/v1/user/find/list")
    public Object findUserList() {
        //stringRedisTemplate.opsForValue().set("sprinboot-redis-messaage", "message", 10, TimeUnit.SECONDS);
        System.out.println("》》》8080");
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, "123456");
    }


    @RequestMapping(value = "/notLogin", method = RequestMethod.GET)
    public ResultInfo notLogin() {
        return new ResultInfo(ResultInfo.SUCCESS, "您尚未登陆！");
    }

    @RequestMapping(value = "/notRole", method = RequestMethod.GET)
    public ResultInfo notRole() {
        return new ResultInfo(ResultInfo.SUCCESS, "您没有权限！");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultInfo logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return new ResultInfo(ResultInfo.SUCCESS, "成功注销！");
    }


    @GetMapping(value = "/user/getMessage")
    public ResultInfo getMessage() {
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, "您拥有用户权限，可以获得该接口的信息！");
    }


    @GetMapping(value = "/admin/getMessage")
    public ResultInfo getAdminMessage() {
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, "您拥有用户权限，可以获得该接口的信息！");
    }


    @ApiOperation(value = "注册", notes = "")
    @GetMapping("api/app/v1/user/create")
    public Object save() {
        return userService.save();
    }

    @PostMapping("api/app/v1/user/register")
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
    @PostMapping("api/app/v1/user/login")
    public Object login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.login(request);
    }


    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @PostMapping("/login")
    public ResultInfo login(@RequestParam String username, @RequestParam String password) {

        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.getUsername();  //获得用户名 String
        token.getPrincipal(); //获得用户名 Object
        token.getPassword();  //获得密码 char[]
        token.getCredentials(); //获得密码 Object
        // 执行认证登陆
        subject.login(token);
        //根据权限，指定返回数据

        return new ResultInfo(ResultInfo.SUCCESS, "登陆成功！");
    }
}
