package nettydemo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * @author vova
 * @version Create in 上午12:24 2017/12/21
 */


public class NettyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    StringBuilder sb = new StringBuilder();
    JSONArray jsonarray = new JSONArray();

    public NettyHandler() {
        jsonarray.add(getJsonObj("name", "ar.sear.ocalplay"));
        jsonarray.add(getJsonObj("name", "ar.sear.ticket"));
        jsonarray.add(getJsonObj("name", "ar.sear.tel"));
        jsonarray.add(getJsonObj("name", "ar.sear.surehotel"));
    }

    public JSONObject getJsonObj(String name, String value) {
        JSONObject jsonobj = new JSONObject();
        jsonobj.put(name, value);
        return jsonobj;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) //  
            throws Exception {//函数执行次数？  
        //解析get请求参数  

        System.out.println(msg.uri());
                Gson gson = new Gson();
        QueryStringDecoder decoder = new QueryStringDecoder(msg.uri());
        Map<String, List<String>> parame = decoder.parameters();
        for (Map.Entry<String, List<String>> entry : parame.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
//            JSONObject ob = 
            String jsonstr = gson .toJson(new User("vova",123));
            System.out.println(jsonstr);
            
        }
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK); // 响应  
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        ByteBuf responseContentByteBuf = Unpooled.copiedBuffer(
                JSON.toJSONString(jsonarray, SerializerFeature.DisableCircularReferenceDetect)
                        .getBytes(Charset.forName("utf-8")));
        response.headers().set("Access-Control-Allow-Origin", "*"); // 跨域  
        response.headers().set(CONTENT_LENGTH, responseContentByteBuf.readableBytes());
        response.content().writeBytes(responseContentByteBuf);
        responseContentByteBuf.release();//zuoyong?  
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);//  
    }
}
