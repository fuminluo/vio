package priv.rabbit.vio.config.websocket;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by baiguantao on 2017/8/4.
 * 用户session记录类
 */
public class SocketSessionRegistry{
    //this map save every session
    //这个集合存储session
    public final ConcurrentMap<String, Set<String>> userSessionIds = new ConcurrentHashMap();
    private final Object lock = new Object();

    public SocketSessionRegistry() {
    }

    /**
     *
     * 获取sessionId
     * @param userId
     * @return
     */
    public Set<String> getSessionIds(String userId) {
        Set set = (Set)this.userSessionIds.get(userId);
        return set != null?set: Collections.emptySet();
    }

    /**
     * 获取所有session
     * @return
     */
    public ConcurrentMap<String, Set<String>> getAllSessionIds() {
        return this.userSessionIds;
    }

    /**
     * register session
     * @param userId
     * @param sessionId
     */
    public void registerSessionId(String userId, String sessionId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(sessionId, "Session ID must not be null");
        Object var3 = this.lock;
        synchronized(this.lock) {
            Object set = (Set)this.userSessionIds.get(userId);
            if(set == null) {
                set = new CopyOnWriteArraySet();
                this.userSessionIds.put(userId, (Set<String>) set);
            }
            if (!((Set)set).isEmpty()) {
                ((Set) set).clear();
            }
            ((Set)set).add(sessionId);
        }
    }

    public void unregisterSessionId(String userId, String sessionId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(sessionId, "Session ID must not be null");
        Object var3 = this.lock;
        synchronized(this.lock) {
            Set set = (Set)this.userSessionIds.get(userId);
            if(set != null && set.remove(sessionId) && set.isEmpty()) {
                this.userSessionIds.remove(userId);
            }

        }
    }
}
