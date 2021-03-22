package priv.rabbit.vio.design.visitor;

/**
 * @Author LuoFuMin
 * @DATE 2021/2/18 14:52
 */
public class CTOVisitor implements Visitor {
    @Override
    public void visit(Student student) {

    }

    @Override
    public void visit(Teacher teacher) {

    }

    @Override
    public void visit(Engineer engineer) {
        System.out.println("工程师: " + engineer.name + ", 代码行数: " + engineer.getCodeLines());
    }

    @Override
    public void visit(Manager manager) {
        System.out.println("经理: " + manager.name + ", 产品数量: " + manager.getProducts());
    }
}