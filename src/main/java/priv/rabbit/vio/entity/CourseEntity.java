package priv.rabbit.vio.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import java.util.List;

/**
 * @Author administered
 * @Description
 * @Date 2018/10/21 20:12
 **/
@ExcelTarget("courseEntity")
public class CourseEntity implements java.io.Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 课程名称
     */
    @Excel(name = "课程名称", orderNum = "1", needMerge = true)
    private String name;
    /**
     * 老师主键
     */
    @ExcelEntity(id = "yuwen")
    private TeacherEntity teacher;
    /**
     * 老师主键
     */
    @ExcelEntity(id = "shuxue")
    private TeacherEntity shuxueteacher;

    @ExcelCollection(name = "选课学生", orderNum = "4")
    private List<StudentEntity> students;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public TeacherEntity getShuxueteacher() {
        return shuxueteacher;
    }

    public void setShuxueteacher(TeacherEntity shuxueteacher) {
        this.shuxueteacher = shuxueteacher;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }
}