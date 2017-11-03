package leo.sunrise.netty;

import leo.sunrise.netty.annotation.AbstractController;
import leo.sunrise.netty.annotation.RequestMapping;

import java.util.Map;

/**
 * @author xuzichao 2017/11/3
 */
@RequestMapping("/test/1")
public class Test1 implements AbstractController{
    public Object doService(Object... args) {
        Map<String, Object> param = (Map<String, Object>)args[0];
        return "Hello, I am test 1" + param.values();
    }
}
