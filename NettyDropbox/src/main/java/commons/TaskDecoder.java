package commons;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

public class TaskDecoder  extends ReplayingDecoder<Task> {
    private final Charset charset = Charset.forName("UTF-8");

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Task newTask = new Task();
        newTask.setClientId(in.readInt());
        newTask.setTaskId(UUID.fromString(in.readCharSequence(36, charset).toString()));
        int strLen = in.readInt();
        newTask.setFilename(
                in.readCharSequence(strLen, charset).toString());
        out.add(newTask);
    }
}
