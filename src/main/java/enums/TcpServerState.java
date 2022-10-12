package enums;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class  TcpServerState {
    static final Logger logger = LogManager.getLogger("TcpClientState");
    /*
         ---------------------------------------------
         |       0       |  1       |     2          |
         ---------------|-----------------------------
         |  信息标志位   |  start   |       end        |
         ---------------------------------------------
     */
    public static void main(String[] args) throws Exception {
        TcpState[] state = {TcpState.valueOf("LISTEN")};
        RandomAccessFile cf = new RandomAccessFile("./client.raf","rw");
        FileChannel cfc = cf.getChannel();
        MappedByteBuffer cmbb = cfc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
        show(state[0]);
        new Thread(()->{
            RandomAccessFile file = null;
            try {
                file = new RandomAccessFile("./server.raf","rw");
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
            cmbb.put(1, (byte) 3);
            cmbb.put(2, (byte) s.length());
            byte[] bytes = s.getBytes();
            for(int i = 0; i < bytes.length; i++){
                cmbb.put(i+3,bytes[i]);
            }
            cmbb.put(0, (byte) 1);
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
        System.out.println("当前服务器状态:"+state);
    }
}
