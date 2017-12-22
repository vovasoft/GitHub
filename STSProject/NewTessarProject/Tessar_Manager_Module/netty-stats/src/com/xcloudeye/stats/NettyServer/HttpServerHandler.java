package com.xcloudeye.stats.NettyServer;


import com.sdicons.json.mapper.MapperException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;

//正向查找
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private HttpRequest request;

    private boolean readingChunks;

    private final StringBuilder responseContent = new StringBuilder();

    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MAXSIZE); //Disk
    //解码器
    private HttpPostRequestDecoder decoder;

    public static final File FILE_DIR = new File("C:\\temp");

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (decoder != null) {
            decoder.cleanFiles();
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.err.println(msg.getClass().getName());
        /**
         * msg的类型
         * {@link DefaultHttpRequest}
         * {@link LastHttpContent}
         */
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;
            URI uri = new URI(request.getUri());
            System.err.println("request uri==" + uri.getPath());
            System.out.println(uri.getPath().startsWith("/stats/app/app_rt"));

            // if GET Method: should not try to create a HttpPostRequestDecoder
            if (request.getMethod().equals(HttpMethod.GET)) {
                // GET Method: should not try to create a HttpPostRequestDecoder
                // So stop here
                QueryStringDecoder decoderQuery = new QueryStringDecoder(request.getUri());
                Map<String, List<String>> uriAttributes = decoderQuery.parameters();
                for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                    for (String attrVal : attr.getValue()) {
                        paramMap.put(attr.getKey(), attrVal);
                    }
                }
                writeResponse(ctx.channel(), paramMap, uri);
                return;
            }
            //判断request请求是否是post请求
            if (request.getMethod().equals(HttpMethod.POST)) {
                System.out.println("===this is http post===");
                try {
                    /**
                     * 通过HttpDataFactory和request构造解码器
                     */
                    decoder = new HttpPostRequestDecoder(factory, request);
                } catch (HttpPostRequestDecoder.ErrorDataDecoderException e1) {
                    e1.printStackTrace();
                    ctx.channel().close();
                    return;
                }
                readingChunks = HttpHeaders.isTransferEncodingChunked(request);
               /* responseContent.append("Is Chunked: " + readingChunks + "\r\n");
                responseContent.append("IsMultipart: " + decoder.isMultipart() + "\r\n");*/
                if (readingChunks) {
                    // Chunk version
                    responseContent.append("Chunks: ");
                    readingChunks = true;
                }
            }
        }

        if (decoder != null) {
            if (msg instanceof HttpContent) {
                // New chunk is received
                HttpContent chunk = (HttpContent) msg;
                try {
                    decoder.offer(chunk);
                } catch (HttpPostRequestDecoder.ErrorDataDecoderException e1) {
                    e1.printStackTrace();
                    responseContent.append(e1.getMessage());
                    ctx.channel().close();
                    return;
                }
                try {
                    while (decoder.hasNext()) {

                        InterfaceHttpData data = decoder.next();
                        if (data != null) {
                            try {
                                writeHttpData(data, paramMap);
                            } finally {
                                //释放该对象
                                data.release();
                            }
                        }
                    }
                } catch (HttpPostRequestDecoder.EndOfDataDecoderException e1) {
                    responseContent.append("\r\n\r\nEND OF CONTENT CHUNK BY CHUNK\r\n\r\n");
                }

                // example of reading only if at the end

                if (chunk instanceof LastHttpContent) {
                    writeResponse(ctx.channel(), paramMap, new URI(request.getUri()));
                    readingChunks = false;
                    reset();
                }
                /*readingChunks = HttpHeaders.isTransferEncodingChunked(request);
                responseContent.append("Is Chunked: " + readingChunks + "\r\n");
                responseContent.append("IsMultipart: " + decoder.isMultipart() + "\r\n");
                if (readingChunks) {
                    // Chunk version
                    responseContent.append("Chunks: ");
                    readingChunks = true;
                }*/
            }
        }
    }

    private void reset() {
        request = null;
        // destroy the decoder to release all resources
        decoder.destroy();
        decoder = null;
    }


    private void writeResponse(Channel channel, Map<String, Object> paramMap, URI uri) throws MapperException, ExecutionException {
        // Convert the response content to a ChannelBuffer.
        System.out.print(uri.getPath());
        ByteBuf buf = copiedBuffer(new HttpAction().getCache(uri.getPath(), paramMap, uri.toString()), CharsetUtil.UTF_8);
        responseContent.setLength(0);

        // Decide whether to close the connection or not.
        boolean close = request.headers().contains(CONNECTION, HttpHeaders.Values.CLOSE, true)
                || request.getProtocolVersion().equals(HttpVersion.HTTP_1_0)
                && !request.headers().contains(CONNECTION, HttpHeaders.Values.KEEP_ALIVE, true);

        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (!close) {
            // There's no need to add 'Content-Length' header
            // if this is the last response.
            response.headers().set(CONTENT_LENGTH, buf.readableBytes());
        }

        Set<Cookie> cookies;
        String value = request.headers().get(COOKIE);
        if (value == null) {
            cookies = Collections.emptySet();
        } else {
            cookies = CookieDecoder.decode(value);
        }
        if (!cookies.isEmpty()) {
            // Reset the cookies if necessary.
            for (Cookie cookie : cookies) {
                response.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
            }
        }
        // Write the response.
        ChannelFuture future = channel.writeAndFlush(response);
        // Close the connection after the write operation is done if necessary.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        messageReceived(ctx, msg);
    }

    private void writeHttpData(InterfaceHttpData data, Map<String, Object> paramMap) throws IOException {

        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
            Attribute attribute = (Attribute) data;
            String name = attribute.getName();
            String value = attribute.getValue();
            paramMap.put(name, value);
        } else if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
            FileUpload fileUpload = (FileUpload) data;
            if (fileUpload.isCompleted()) {
                paramMap.put("filename", fileUpload.getFile().getName());
                File dest = new File(FILE_DIR, fileUpload.getFile().getName());
                fileUpload.renameTo(dest);
                decoder.removeHttpDataFromClean(fileUpload);

            }

        }
    }

}
