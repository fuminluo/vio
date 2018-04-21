package priv.rabbit.vio.service.impl;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.dto.user.LoginRequest;
import priv.rabbit.vio.entity.User;
import priv.rabbit.vio.mapper.UserMapper;
import priv.rabbit.vio.service.UserService;

import java.util.Date;
import java.util.UUID;

import static priv.rabbit.vio.common.Constant.JWT_SECRET;
import static priv.rabbit.vio.common.Constant.TOKEN_EXP_SECENDS;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    public Object save() {
        User user = new User((long) 1, "账上", "123");

        userMapper.insertSelective(user);
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    @Override
    @Transactional
    public Object login(LoginRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user = userMapper.findOneByParam(user);

        String token = Jwts.builder().setSubject(user.getUserId()).setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP_SECENDS * 1000)).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
        user.setToken(token);

        userMapper.updateByPrimaryKeySelective(user);

        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, user);
    }

    @Override
    @Transactional
    public Object register(LoginRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        User userCheck = userMapper.findOneByParam(user);
        if (userCheck != null) {
            return new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "用户名已存在");
        }
        String userId = "";
        for (int i = 0; i < 10; i++) {
            userId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
            User userCheckUserId = userMapper.selectByPrimaryKey(userId);
            if (userCheckUserId==null){
                break;
            }
        }
        user.setUserId(userId);
        user.setPassword(request.getPassword());
        userMapper.insertSelective(user);
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, user);
    }
}
