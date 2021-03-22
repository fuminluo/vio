package priv.rabbit.vio.design.visitor;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author LuoFuMin
 * @DATE 2021/2/18 12:29
 */
public class ObjectStructure {
    /**
     * 用于存放所有元素
     */
    private List<Element> elements = new LinkedList<>();

    /**
     * 访问者访问元素的入口
     */
    public void accept(Visitor visitor) {
        for (Element element : elements) {
            element.accept(visitor);
        }
    }

    public void attach(Element e) {
        elements.add(e);
    }

    public void detach(Element e) {
        elements.remove(e);
    }

    public static void main(String[] args) {
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.attach(new Student("Jack(student)", 95, 4));
        objectStructure.attach(new Student("Maria(student)", 85, 6));
        objectStructure.attach(new Teacher("Mike(teacher)", 80, 9));
        objectStructure.attach(new Teacher("Anna(teacher)", 85, 10));
        objectStructure.accept(new ScoreJudge());
        System.out.println("------------------------");
        objectStructure.accept(new ResearchJudge());
    }
}
