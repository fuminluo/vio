package priv.rabbit.vio.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;
import org.mapstruct.Mapper;
import priv.rabbit.vio.config.annotation.SQLPermission;
import priv.rabbit.vio.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    int insertBatch(@Param("list") List<User> list);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findOneByParam(User user);

    String getPassword(String username);

    @SQLPermission(menuId = "menuId")
    List<User> findList();

    @Select("select * from user where username = #{name}")
    @SQLPermission(menuId = "menuId")
    List<User> findByName(@Param("name") String name, @Param("menuId") String menuId);

    List<String> findNames();

    int findCount();
}