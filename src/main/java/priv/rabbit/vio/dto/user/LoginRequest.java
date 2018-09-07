package priv.rabbit.vio.dto.user;

import org.hibernate.validator.constraints.Length;
import priv.rabbit.vio.common.IBaseRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class LoginRequest implements IBaseRequest,Serializable{

    private static final long serialVersionUID = -2810845001311965385L;

    @NotBlank(message = "用户名不能为空")
    @Pattern(message = "用户名必须是4~20位数字字母的组合", regexp = "[A-Za-z0-9]{4,20}")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(message = "密码必须是{min}~{max}位字符", min = 6, max = 20)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
