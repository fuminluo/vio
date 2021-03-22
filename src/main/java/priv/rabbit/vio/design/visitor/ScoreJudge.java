package priv.rabbit.vio.design.visitor;

/**
 * @Author LuoFuMin
 * @DATE 2021/2/18 12:26
 */
public class ScoreJudge implements Visitor {
    private String awardWords = "%s的分数是%d，荣获了成绩优秀奖。";

    @Override
    public void visit(Student student) {

        if (student.getScore() >= 90) {
            System.out.println(String.format(awardWords, student.getName(), student.getScore()));
        }
    }

    @Override
    public void visit(Teacher teacher) {
        if (teacher.getScore() >= 85) {
            System.out.println(String.format(awardWords, teacher.getName(), teacher.getScore()));
        }
    }

    @Override
    public void visit(Engineer engineer) {

    }

    @Override
    public void visit(Manager manager) {

    }
}
