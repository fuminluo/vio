package priv.rabbit.vio.controller;

import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.entity.User;
import priv.rabbit.vio.mapper.UserMapper;

@Api(value = "ShiroController", description = "权限管理")
@Controller
public class ShiroController {

    private static Logger LOG = LoggerFactory.getLogger(ShiroController.class);
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/api/v1/web/shiro/login-html", method = RequestMethod.GET)
    public String notLogin() {
        //new ResultInfo(ResultInfo.SUCCESS, "您尚未登陆！");
        return "login";
    }

    @RequestMapping(value = "/api/v1/web/shiro/notRole", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo notRole() {
        return new ResultInfo(ResultInfo.SUCCESS, "您没有权限！");
    }

    @RequestMapping(value = "/api/v1/web/shiro/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return new ResultInfo(ResultInfo.SUCCESS, "成功注销！");
    }

    @GetMapping(value = "/api/v1/web/shiro/user")
    @ResponseBody
    @RequiresPermissions("getMessage")
    public ResultInfo getMessage() {
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, "您拥有用户权限，可以获得该接口的信息！");
    }

    @GetMapping(value = "/api/v1/web/shiro/admin")
    @ResponseBody
    @RequiresRoles({"admin", "user"})
    public ResultInfo getAdminMessage() {
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, "您拥有用户权限，可以获得该接口的信息！");
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @PostMapping("/api/v1/web/shiro/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        LOG.info("》》》login");
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
        //return new ResultInfo(ResultInfo.SUCCESS, "登陆成功！");

        User user = new User();
        user.setUsername(username);
        user = userMapper.findOneByParam(user);

        model.addAttribute("user_no", user.getUserId());
        model.addAttribute("user", user);
        return "index";
    }

    /**
     * 检查用户名和登录密码
     *
     * @param username:用户名, password:密码
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/8/6
     */
    @PostMapping("/api/v1/web/shiro/login-check")
    @ResponseBody
    public ResultInfo<?> loginCheck(@RequestParam String username, @RequestParam String password) {
        LOG.info("》》》login-check");
        String pwd = userMapper.getPassword(username);

        if (pwd == null || !pwd.equals(password)) {
            return new ResultInfo(ResultInfo.FAILURE, "用户名或密码错误");
        }
        return new ResultInfo(ResultInfo.SUCCESS);
    }

}
