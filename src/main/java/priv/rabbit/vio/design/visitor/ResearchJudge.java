package priv.rabbit.vio.design.visitor;

/**
 * @Author LuoFuMin
 * @DATE 2021/2/18 12:28
 */
public class ResearchJudge implements Visitor {
    private String awardWords = "%s的论文数是%d，荣获了科研优秀奖。";

    @Override
    public void visit(Student student) {
        if(student.getPaperCount() >= 5){
            System.out.println(String.format(awardWords, student.getName(), student.getPaperCount()));
        }
    }

    @Override
    public void visit(Teacher teacher) {
        if(teacher.getPaperCount() >= 10){
            System.out.println(String.format(awardWords, teacher.getName(), teacher.getPaperCount()));
        }
    }

    @Override
    public void visit(Engineer engineer) {

    }

    @Override
    public void visit(Manager manager) {

    }
}
