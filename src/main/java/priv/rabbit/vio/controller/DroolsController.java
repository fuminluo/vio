package priv.rabbit.vio.controller;

import com.ytooo.bean.People;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.rabbit.vio.common.ResultInfo;

/**
 * @Author administered
 * @Description
 * @Date 2020/2/29 19:38
 **/
@RestController
public class DroolsController {

    @Autowired
    private KieSession session;

    /**
     * 字符串
     */
    @GetMapping(value = "/rules")
    public ResultInfo stringOperation(String key) {
        People people = new People();
        people.setName("达");
        people.setSex(1);
        people.setDrlType("people");
        session.insert(people);//插入
        session.fireAllRules();//执行规则
        return new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS,people);
    }
}
