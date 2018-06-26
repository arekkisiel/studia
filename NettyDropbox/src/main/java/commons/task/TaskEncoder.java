package commons.task;

import commons.task.Task;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class TaskEncoder extends MessageToByteEncoder<Task>{
    private final Charset charset = Charset.forName("UTF-8");

    protected void encode(ChannelHandlerContext channelHandlerContext, Task task, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(task.getClientId());
        byteBuf.writeCharSequence(task.getTaskId().toString(),charset);
        byteBuf.writeInt(task.getFilename().length());
        byteBuf.writeCharSequence(task.getFilename(), charset);
        byteBuf.writeInt(task.getServerPort());
    }
}
