package priv.rabbit.vio.mapper;


import org.mapstruct.Mapper;
import priv.rabbit.vio.entity.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findOneByParam(User user);
}