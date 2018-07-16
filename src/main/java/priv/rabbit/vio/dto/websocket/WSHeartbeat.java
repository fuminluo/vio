package priv.rabbit.vio.dto.websocket;

import java.io.Serializable;

/**
 * websocke 心跳
 * @author LuoFuMin
 * @data 2018/7/16
 */
public class WSHeartbeat implements Serializable{

    /**
     * 来源ID
     */
    private String from;

    /**
     * 时间戳
     */
    private Long timestamp;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
