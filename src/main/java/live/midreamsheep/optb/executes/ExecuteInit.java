package live.midreamsheep.optb.executes;

/**
 * 初始化api，在扫描时会执行(一般用于单个执行者类上，用于执行特殊的初始化方法)
 * */
public interface ExecuteInit {
    /**
     * 初始化方法
     * @param key 执行key
     * @param proxy 代理
     * */
    void init(String key,ExecuteHandlerInter proxy);
}