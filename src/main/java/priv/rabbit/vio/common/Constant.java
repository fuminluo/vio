package priv.rabbit.vio.common;

public class Constant {

    //webSocket相关配置
    //链接地址
    public static String WEBSOCKETPATHPERFIX = "/ws-push";

    public static String WEBSOCKETPATH = "/endpointWisely";

    //消息代理路径
    public static String WEBSOCKETBROADCASTPATH = "/topic";

    //前端发送给服务端请求地址
    public static final String FORETOSERVERPATH = "/welcome";

    //服务端生产地址,客户端订阅此地址以接收服务端生产的消息
    public static final String PRODUCERPATH = "/topic/getResponse";

    //点对点消息推送地址前缀
    public static final String P2PPUSHBASEPATH = "/user";

    //点对点消息推送地址后缀,最后的地址为/user/用户识别码/msg
    public static final String P2PPUSHPATH = "/msg";



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
