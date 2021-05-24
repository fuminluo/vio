package priv.rabbit.vio.design.rule;

import com.alibaba.fastjson.JSON;
import priv.rabbit.vio.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        IUserSpecification spec = new UserByNameEqual("小明").and(new UserByAgeThan(20).or(new UserByAgeThan(5)));

        User user1 = new User();
        user1.setAge(10);
        user1.setUsername("小明");

        User user2 = new User();
        user2.setAge(30);
        user2.setUsername("小明");
        List<User> list = Arrays.asList(user1, user2);

        list = list.stream().filter(var -> spec.isSatisfiedBy(var)).collect(Collectors.toList());

        System.out.println(JSON.toJSON(list));

    }
}
