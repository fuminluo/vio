package priv.rabbit.vio.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import priv.rabbit.vio.common.ResultInfo;

@Controller
@RequestMapping("")
public class ShiroController {

    @RequestMapping(value = "/api/web/shiro/login.html", method = RequestMethod.GET)
    public String notLogin() {
        //new ResultInfo(ResultInfo.SUCCESS, "您尚未登陆！");
        return "login";
    }

    @RequestMapping(value = "/api/web/shiro/notRole", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo notRole() {
        return new ResultInfo(ResultInfo.SUCCESS, "您没有权限！");
    }

    @RequestMapping(value = "/api/web/shiro/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return new ResultInfo(ResultInfo.SUCCESS, "成功注销！");
    }

    @GetMapping(value = "/api/web/shiro/user/getMessage")
    @ResponseBody
    public ResultInfo getMessage() {
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, "您拥有用户权限，可以获得该接口的信息！");
    }

    @GetMapping(value = "/api/web/shiro/admin/getMessage")
    @ResponseBody
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
    @PostMapping("/api/web/shiro/login")
    public String login(@RequestParam String username, @RequestParam String password) {

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

        return "index";
    }

}
