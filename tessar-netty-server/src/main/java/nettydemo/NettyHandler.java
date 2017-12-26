package nettydemo;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.dbmongo.UseMyMongo;
import domain.Player;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import com.alibaba.fastjson.JSONArray;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.rtsp.RtspHeaders.Names.CONNECTION;


/**
 * @author vova
 * @version Create in 上午12:24 2017/12/21
 */


public class NettyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    StringBuilder sb = new StringBuilder();
    JSONArray jsonarray = new JSONArray();

    @Autowired
    UseMyMongo umm ;

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
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        //函数执行次数？
        //解析get请求参数
        if (!(msg instanceof FullHttpRequest)) {
            return;
        }

        String uri = msg.uri();
        System.out.println("URI:" + uri);
        System.out.println("suburi:" + uri.substring(0, 21));
        if (!(uri.substring(0, 21)).equals("/tessar/statis/statis")) {
            System.out.println("return error url");
            return;
        }

        Gson gson = new Gson();
        QueryStringDecoder decoder = new QueryStringDecoder(msg.uri());
        Map<String, List<String>> parame = decoder.parameters();

        for (Map.Entry<String, List<String>> entry : parame.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            String json = entry.getValue().toString();
            System.out.println("json:" + json);
            List<Player> players = gson.fromJson(json,  new TypeToken<List<Player>>() {
            }.getType());

            //autoware 已经好使啦
         //   UseMyMongo umm =new UseMyMongo();
            for (Player player : players) {
                umm.insertMongo(player);
            }
//            System.out.println(player);
            System.out.println("done");
        }
//        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK); // 响应
//        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
//        ByteBuf responseContentByteBuf = Unpooled.copiedBuffer(
//                JSON.toJSONString(jsonarray, SerializerFeature.DisableCircularReferenceDetect)
//                        .getBytes(Charset.forName("utf-8")));
//        response.headers().set("Access-Control-Allow-Origin", "*"); // 跨域
//        response.headers().set(CONTENT_LENGTH, responseContentByteBuf.readableBytes());
//        response.content().writeBytes(responseContentByteBuf);
//        responseContentByteBuf.release();
//        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);//
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK); // 响应
        response.headers().set(CONTENT_TYPE,"text/html; charset=UTF-8");
        ByteBuf bb = Unpooled.copiedBuffer("OK".getBytes());
        response.content().writeBytes(bb);
        bb.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    //获取请求的内容
    private String parseJosnRequest(FullHttpRequest request) {
        ByteBuf jsonBuf = request.content();
        String jsonStr = jsonBuf.toString(CharsetUtil.UTF_8);
        return jsonStr;
    }

    private void ResponseJson(ChannelHandlerContext ctx, FullHttpRequest request, String jsonStr) {
        // TODO Auto-generated method stub
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        byte[] jsonByteByte = jsonStr.getBytes();
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(jsonByteByte));
        response.headers().set(CONTENT_TYPE, "text/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

        if (keepAlive) {
            response.headers().set(CONNECTION, KEEP_ALIVE);
        }
        ctx.writeAndFlush(response);
    }

}
