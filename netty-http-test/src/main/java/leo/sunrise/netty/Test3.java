package leo.sunrise.netty;

import leo.sunrise.netty.annotation.AbstractController;
import leo.sunrise.netty.annotation.RequestMapping;

/**
 * @author xuzichao 2017/11/3
 */
@RequestMapping("/test/3")
public class Test3 implements AbstractController{
    public Object doService(Object... args) {
        return "Hello, I am test 3";
    }
}
