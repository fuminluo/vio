package priv.rabbit.vio.service;

import priv.rabbit.vio.dto.user.LoginRequest;
import priv.rabbit.vio.entity.User;

public interface UserService {

     Object save();

     User login(User user);

     Object register(LoginRequest request);
}
