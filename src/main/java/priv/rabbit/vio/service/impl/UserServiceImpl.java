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
import priv.rabbit.vio.utils.IDUtil;

import java.util.Date;
import java.util.UUID;

import static priv.rabbit.vio.common.Constant.JWT_SECRET;
import static priv.rabbit.vio.common.Constant.TOKEN_EXP_SECENDS;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public ResultInfo save(String username, String password) {

        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    @Override
    @Transactional
    public User login(User user) {
        String token = Jwts.builder().setSubject(user.getUserNo()).setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP_SECENDS * 1000)).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
        User u = new User();
        u.setId(user.getId());
        u.setToken(token);
        userMapper.updateByPrimaryKeySelective(u);
        user.setToken(token);
        return user;
    }

    @Override
    @Transactional
    public ResultInfo register(LoginRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        User userCheck = userMapper.findOneByParam(user);
        if (userCheck != null) {
            return new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "用户名已存在");
        }
        String userNo = IDUtil.createID(1, 7);
        user.setUserNo(userNo);
        user.setPassword(request.getPassword());
        userMapper.insertSelective(user);
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, user);
    }
}
