package enums;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Optional;
import java.util.stream.Stream;

public enum  TcpFlag {
    SYN, // synchorinzed 同步 建立连接        000001
    FIN, // finish 结束 关闭连接              000010
    ACK, // acknowledge 响应 确认接受到报文    000100
    PSH, // 表示有DATA数据传输                001000
    RST, // 表示连接重置                      010000
    URG; // urgent 紧急                      100000

    public static int GetMessage(TcpFlag ... flags){
        int[] message = {0};
        EnumMap<TcpFlag, Integer> enumMap = new EnumMap<TcpFlag, Integer>(TcpFlag.class);
        enumMap.put(SYN,0b1);
        enumMap.put(FIN,0b10);
        enumMap.put(ACK,0b100);
        enumMap.put(PSH,0b1000);
        enumMap.put(RST,0b10000);
        enumMap.put(URG,0b100000);
        Arrays.stream(flags)
                .reduce((m1,m2) -> {
                    int next = enumMap.get(m2);
                    message[0] = next| enumMap.get(m1)  | message[0];
                    return m1;
                });
        return message[0];
    }

    TcpFlag(){;}
}
