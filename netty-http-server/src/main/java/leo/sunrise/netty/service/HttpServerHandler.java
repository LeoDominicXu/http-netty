package leo.sunrise.netty.service;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import leo.sunrise.netty.annotation.AbstractController;
import leo.sunrise.netty.annotation.RequestMappingHandler;
import leo.sunrise.netty.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuzichao 2017/11/3
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);
    private Gson gson = new Gson();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String url = request.uri();

            logger.info("接收到请求:{}",url);
            Map<String, Object> param = new HashMap<>();
            if (request.getMethod().equals(HttpMethod.GET) && url.contains("?")) {
                url = request.uri().substring(0, request.uri().indexOf("?"));
                param = RequestUtil.getUrlParams(request.uri().substring(request.uri().indexOf("?")+1));
            } else {

            }
            String responJson;
            AbstractController action = RequestMappingHandler.getMapping(url);
            if (action != null){
                Object res = action.doService(param);
                responJson = gson.toJson(res);
                //设置返回内容

            } else {
                responJson = "404";
            }

            ByteBuf content = Unpooled.copiedBuffer(responJson, CharsetUtil.UTF_8);
            //创建响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            ctx.writeAndFlush(response);

        }
    }
}
