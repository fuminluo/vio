package priv.rabbit.vio.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.config.annotation.BussAnnotation;
import priv.rabbit.vio.dto.user.LoginRequest;
import priv.rabbit.vio.entity.User;
import priv.rabbit.vio.mapper.UserMapper;
import priv.rabbit.vio.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static priv.rabbit.vio.common.Constant.JWT_SECRET;
import static priv.rabbit.vio.common.Constant.TOKEN_EXP_SECENDS;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @BussAnnotation(moduleName = "人员管理", option = "添加用户")
    @Override
    public ResultInfo save(String username, String password, Integer size) {
        List list = new ArrayList();
        for (int i = 0; i < size; i++) {
            User u = new User();
            u.setUsername(username);
            u.setPassword(password);
            u.setState(1);
            list.add(u);
            if ((i % 10000) == 0) {
                userMapper.insertBatch(list);
                list.clear();
            }
        }
        if (!list.isEmpty()) {
            userMapper.insertBatch(list);
        }
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    @Transactional
    @Override
    public User login(User user) {
        String token = Jwts.builder().setSubject(user.getUserId().toString()).setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP_SECENDS * 1000)).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
        User u = new User();
        u.setUserId(user.getUserId());
        u.setToken(token);
        userMapper.updateByPrimaryKeySelective(u);
        user.setToken(token);
        return user;
    }


    @Transactional
    @Override
    public ResultInfo register(LoginRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        User userCheck = userMapper.findOneByParam(user);
        if (userCheck != null) {
            return new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "用户名已存在");
        }
        user.setPassword(request.getPassword());
        userMapper.insertSelective(user);
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, user);
    }


    @Override
    public PageInfo<User> findList() {
        PageHelper.startPage(1, 5000);
        List<User> list = userMapper.findList();
        PageInfo pageInfo = new PageInfo<User>(list);
        return pageInfo;
    }
}
