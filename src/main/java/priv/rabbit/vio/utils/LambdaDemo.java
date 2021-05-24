package priv.rabbit.vio.utils;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import priv.rabbit.vio.entity.Department;
import priv.rabbit.vio.entity.Employee;
import priv.rabbit.vio.entity.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author administered
 * @Description
 * @Date 2019/1/19 20:11
 **/
public class LambdaDemo {
    public static void func1() {
        System.out.println("not use Lambda");
        List<String> strs = Arrays.asList("hello", "word", "apple", "people", "sea", "book", "school", "computer");

        //old way
        Collections.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });

        System.out.println(strs);
    }

    public static void func2() {
        System.out.println("use Lambda");
        List<String> strs = Arrays.asList("hello", "word", "apple", "people", "sea", "book", "school", "computer");

        //way 1
        Collections.sort(strs, (String a, String b) -> {
            return a.compareTo(b);
        });

        //way 2 : only one statement
        Collections.sort(strs, (String a, String b) -> a.compareTo(b));

        //way 3 : omit class
        Collections.sort(strs, (a, b) -> a.compareTo(b));

        System.out.println(strs);
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println("---- test reduce ----");
        Optional<Integer> sum = list.stream().reduce((a, b) -> a + b);

        sum.ifPresent(System.out::println);

        List<String> strs = Arrays.asList("a", "a", "a", "a", "b");
        boolean aa = strs.stream().anyMatch(str -> str.equals("a"));
        boolean bb = strs.stream().allMatch(str -> str.equals("a"));
        boolean cc = strs.stream().noneMatch(str -> str.equals("a"));
        long count = strs.stream().filter(str -> str.equals("a")).count();
        System.out.println(aa);// TRUE
        System.out.println(bb);// FALSE
        System.out.println(cc);// FALSE
        System.out.println(count);// 4

        List<User> userList = new ArrayList<>();
        Map<Long, String> map = userList.stream().collect(Collectors.toMap(User::getUserId, User::getNickname));
        Map<Long, User> Usermap = userList.stream().collect(Collectors.toMap(User::getUserId, v -> v, (k1, k2) -> k1));
        Map<Long, List<User>> UserList = userList.stream().collect(Collectors.groupingBy(User::getUserId));



    }
}
