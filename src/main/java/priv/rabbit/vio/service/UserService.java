package priv.rabbit.vio.service;

import com.github.pagehelper.PageInfo;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.dto.user.LoginRequest;
import priv.rabbit.vio.entity.User;

public interface UserService {

     ResultInfo<?> save(String username,String password,Integer size);

     User login(User user);

     ResultInfo<?> register(LoginRequest request);


     PageInfo<User>  findList();
}
