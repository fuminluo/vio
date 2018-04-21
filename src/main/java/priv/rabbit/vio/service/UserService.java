package priv.rabbit.vio.service;

import priv.rabbit.vio.dto.user.LoginRequest;

public interface UserService {

     Object save();

     Object login(LoginRequest request);

     Object register(LoginRequest request);
}
