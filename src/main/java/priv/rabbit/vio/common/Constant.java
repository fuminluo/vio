package priv.rabbit.vio.common;

public class Constant {

    //webSocket相关配置
    //链接地址
    public static String WEBSOCKET_PATH_PERFIX = "/ws-push";

    //Stomp的注册节点
    public static String STOMP_PATH = "/endpointWisely";

    //消息代理路径
    public static String TOPIC_BASE_PATH = "/topic";

    //点对点消息推送地址前缀
    public static final String P2P_PUSH_BASE_PATH = "/user";

    //点对点消息推送地址后缀,最后的地址为/user/用户识别码/msg
    public static final String P2P_PUSH_PATH = "/msg";


    /**
     * websocket 在线key
     */
    public static final String WEB_SOCKET_USER_NO = "websocket:online:userNo:";


    /**
     * JWT密码
     */
    public static final String JWT_SECRET = "7D02504806779FFBCE20A6F564F5FB75";

    /**
     * Token
     */
    public static final String USER_TOKEN = "UC:USER:TOKEN:";

    /**
     * Token有效期(毫秒)
     */
    public static final long TOKEN_EXP_SECENDS = 60 * 60 * 24L;// 1d
}
