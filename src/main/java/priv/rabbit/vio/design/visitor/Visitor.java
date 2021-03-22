package priv.rabbit.vio.design.visitor;

/**
 * @Author LuoFuMin
 * @DATE 2021/2/18 11:54
 */
public interface Visitor {
    /**
     * 访问学生元素
     */
    void visit(Student student);

    /**
     * 访问老师元素
     */
    void visit(Teacher teacher);

    // 访问工程师类型
    void visit(Engineer engineer);

    // 访问经理类型
    void visit(Manager manager);
}
