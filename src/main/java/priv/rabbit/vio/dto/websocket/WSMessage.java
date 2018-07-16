package priv.rabbit.vio.dto.websocket;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * websocke 消息类
 *
 * @author LuoFuMin
 * @data 2018/7/16
 */
public class WSMessage implements Serializable {

    private static final long serialVersionUID = 845478049601016136L;

    /**
     * 来源ID
     */
    private String from;

    /**
     * 目标ID
     */
    @NotBlank
    private String to;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 消息类型int类型(0:text、1:image、2:voice、3:vedio、4:music、5:news)
     */
    private Integer msgType;

    /**
     * 聊天类型int类型(0:未知,1:公聊,2:私聊)
     */
    private Integer chatType;

    /**
     * 群组id仅在chatType为(1)时需要,String类型
     */
    private String groupId;

    /**
     *  内容
     */
    private String content;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
