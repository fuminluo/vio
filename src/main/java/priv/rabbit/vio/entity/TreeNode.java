package priv.rabbit.vio.entity;

import java.util.List;

/**
 * @Author administered
 * @Description
 * @Date 2018/12/2 15:27
 **/
public class TreeNode {
    private String id;  //编号（不一定是主键）

    private String parentId;  //父级编号

    private String text;  //显示名称

    private String state;//combotree 设置为closed 则默认此节点不展开

    private List<TreeNode> children;

    public TreeNode(String id, String text, String parentId) {
        this.id = id;
        this.parentId = parentId;
        this.text = text;
    }

    public TreeNode(String id, String text, TreeNode parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.text = text;
    }

    public TreeNode(String id, String text, String parentId, String state) {
        this.id = id;
        this.parentId = parentId;
        this.text = text;
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                ", children=" + children +
                '}';
    }


}
