package vova.nettydemo;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import vova.dao.manager.ManageGameInput;
import vova.dao.manager.ManagePayInput;
import vova.domain.Player;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import com.alibaba.fastjson.JSONArray;
import io.netty.util.CharsetUtil;
import vova.domain.payment.PayReceive;

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


//    public NettyHandler() {
//        jsonarray.add(getJsonObj("name", "ar.sear.ocalplay"));
//        jsonarray.add(getJsonObj("name", "ar.sear.ticket"));
//        jsonarray.add(getJsonObj("name", "ar.sear.tel"));
//        jsonarray.add(getJsonObj("name", "ar.sear.surehotel"));
//    }

    public JSONObject getJsonObj(String name, String value) {
        JSONObject jsonobj = new JSONObject();
        jsonobj.put(name, value);
        return jsonobj;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

//    @Autowired
//    private ManageGameInput manageGameInput;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        //函数执行次数？
        //解析get请求参数
        if (!(msg instanceof FullHttpRequest)) {
            return;
        }


        String uri = msg.uri();
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK); // 响应
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");

        if (!(uri.substring(0, 21)).equals("/tessar/statis/statis")) {
            //System.out.println("return error url");
            ResponseString(ctx,response,"error uri is wrong");
            return;
        }

        Gson gson = new Gson();
        QueryStringDecoder decoder = new QueryStringDecoder(msg.uri());
        Map<String, List<String>> parame = decoder.parameters();
        List<String> flag = parame.get("action");
        String json = parame.get("json").toString();

        ByteBuf bb = null;//Unpooled.copiedBuffer("OK".getBytes());

        if (flag==null){
            ResponseString(ctx, response, "action is none");
        } else if (flag.get(0).equals("game")) {
            ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
            ManageGameInput manageGameInput= (ManageGameInput) ac.getBean("manageGameInput");
            List<Player> players = gson.fromJson(json, new TypeToken<List<Player>>(){}.getType());
            for (Player player : players) {
                manageGameInput.HandPlayerData(player);
            }
            ResponseString(ctx, response, "OK");
        } else if (flag.get(0).equals("pay")) {
            //支付情况
            ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
            ManagePayInput mpi= (ManagePayInput) ac.getBean("managePayInput");
            List<PayReceive> payReceiveList = gson.fromJson(json, new TypeToken<List<PayReceive>>(){}.getType());

            for (PayReceive payReceive : payReceiveList) {
                mpi.HandPayData(payReceive);
            }
            ResponseString(ctx, response, "OK");
        }

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

    private void ResponseString(ChannelHandlerContext ctx,FullHttpResponse response, String Str) {
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        ByteBuf bb = Unpooled.copiedBuffer(Str.getBytes());
        response.content().writeBytes(bb);
        bb.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
