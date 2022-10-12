package enums;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Scanner;

public class TcpClientState {
    static final Logger logger = LogManager.getLogger("TcpClientState");

    /*
        @param ： flags 传入的报文标志位
        @param : target 期待的目标标志
        @return : 是否符合预期
     */
    public boolean CheckExpect(EnumSet<TcpFlag> flags,TcpFlag ... target) throws Exception {
        if(flags.size() != target.length){
            logger.info("Message flags contains "+flags.size()+" which is out of size "+target.length);
            return false;
        }
        for (TcpFlag tcpFlag : target) {
            if(!flags.contains(tcpFlag)){
                logger.info("Message flags expect "+ tcpFlag + " but it doesn't contain");
                throw new Exception("Missing flag");
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        TcpState[] state = {TcpState.valueOf("CLOSED")};
        RandomAccessFile sf = new RandomAccessFile("./server.raf","rw");
        FileChannel sfc = sf.getChannel();
        MappedByteBuffer smbb = sfc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
        show(state[0]);
        new Thread(()->{
            RandomAccessFile file = null;
            try {
                file = new RandomAccessFile("./client.raf","rw");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            FileChannel fc = file.getChannel();
            MappedByteBuffer mbb = null;
            try {
                mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(true){
                byte flag = mbb.get(0); //共享文件标志位
                if(flag == 0) continue;
                byte start = mbb.get(1); //命令起始位置
                byte length = mbb.get(2); //文件结束位置
                byte[] arr = new byte[length];
                for (byte i = 0; i < length; i++) {
                    arr[i] = mbb.get(i+start);
                }
                String s = new String(arr);
                String[] strings = s.split(" ");
                EnumSet<TcpFlag> flags = EnumSet.noneOf(TcpFlag.class);
                Arrays.stream(strings)
                        .forEach((e)->{
                            boolean f = flags.add(TcpFlag.valueOf(e));
                            if(!f) logger.info("Error flags");
                        });
                try {
                    state[0] = state[0].next(flags);
                    show(state[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    mbb.put(0, (byte) 0);
                }
            }
        }).start();
        Scanner sc = new Scanner(System.in);
        while (true){
            String s = sc.nextLine();
            smbb.put(1, (byte) 3);
            smbb.put(2, (byte) s.length());
            byte[] bytes = s.getBytes();
            for(int i = 0; i < bytes.length; i++){
                smbb.put(i+3,bytes[i]);
            }
            smbb.put(0, (byte) 1);
            state[0] = state[0].handle(convert(s.split(" ")));
            show(state[0]);
        }
    }

    public static EnumSet convert(String[] flags){
        EnumSet<TcpFlag> res = EnumSet.noneOf(TcpFlag.class);
        for (String flag : flags) {
            res.add(TcpFlag.valueOf(flag));
        }
        return res;
    }

    public static void show(TcpState state){
        System.out.println("当前客户端状态:"+state);
    }
}
