package priv.rabbit.vio.design.rule;

import com.alibaba.fastjson.JSON;
import priv.rabbit.vio.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {

        new EqualRule("username", () -> {
            return "";
        });
        ISpecification spec = new EqualRule("username", "小明");
        spec = spec.and(new EqualRule("nickname", "nickname"));
        spec = spec.and(new NumberBetweenRule("age", 1, 20));
        spec = spec.or(new GreaterThanRule("age", 9));
        test(spec);
    }

    public static void test(final ISpecification spec) {
        User user1 = new User();
        user1.setAge(0);
        user1.setNickname("nickname");
        user1.setUsername("小明");

        User user2 = new User();
        user2.setAge(30);
        //user2.setNickname("nickname");
        user2.setUsername("李文0斌");

        List<User> list = Arrays.asList(user1, user2);

        Consumer consumer = (s) -> {
            System.out.println("==匹配==");
        };

        list.stream().forEach(var -> {
            if (spec.isSatisfiedBy(var)) {
                consumer.accept(var);
            }
        });

        list = list.stream().filter(var -> {
            boolean isSatisfiedBy = false;
            isSatisfiedBy = spec.isSatisfiedBy(var);
            if (isSatisfiedBy) {
            }
            return isSatisfiedBy;
        }).collect(Collectors.toList());

        System.out.println(JSON.toJSON(list));
    }
}
