package utils;


//雪花算法id生成器
//   ------------------------------------------------------
//  | 时间戳 | 机器id+数据id  | 序列id  |
//  +-------+---------------+---------+
//  | 42bit |  5bit + 5bit  |   12bit | 
public class SnowflakeWorker {
    
    // 最后移除id生成的时间戳 
    private long latestTime;
    
    // 数据id所占位数(5位 最大2^5-1)
    private long DATA_ID_BITS = 5L;
    
    //机器id所占位数(5位 最大2^5-1)
    private long MAC_ID_BITS = 5L;
    
    //序列id所占位数
    private long SERIAL_ID_BITS = 12L;

    //数据id最大值
    private long DATA_ID_MAX = -1L ^ (-1L << DATA_ID_BITS);

    //机器id最大值
    private long MAC_ID_MAX = -1L ^ (-1L << MAC_ID_BITS);

    //序列化id最大值
    private long SER_ID_MAX = -1L ^ (-1L << SERIAL_ID_BITS);

    //数据id偏移量(12位序列id)
    private long DATA_SHIFE = 12L;

    //机器id偏移量
    private long MAC_SHIFE = DATA_SHIFE+DATA_ID_BITS;

    //时间戳偏移量
    private long TIME_SHIFE = MAC_SHIFE+MAC_ID_BITS;

    //序列化id
    private Long serid;

    //机器id
    private Long macid;

    //数据id
    private Long dataid;

    {
        serid = 0L;
        latestTime = System.currentTimeMillis();
    }

    public SnowflakeWorker(Long macid, Long dataid) {
        if(macid < 0 || macid >= MAC_ID_MAX){
            throw new IllegalArgumentException(String.format("Machine id must between 0 and %d",macid));
        }
        if (dataid < 0 || dataid >= DATA_ID_MAX){
            throw new IllegalArgumentException(String.format("Data id must between 0 and %d",dataid));
        }
        this.macid = macid;
        this.dataid = dataid;
    }

//    public int IncreamentSerid(){
//        int current;
//        int next;
//        do{
//            current = serid.get();
//            next = (current > SER_ID_MAX)?0:current+1;
//        }while (!serid.compareAndSet(current,next));
//        return next;
//    }

    public Long parseId(Long timestamp, Long dataid, Long macid, long serialid){
        return (timestamp << TIME_SHIFE) |
                (dataid << DATA_ID_BITS) |
                (macid << MAC_ID_BITS) |
                serialid;
    }

    public Long nextId(){
        long curtime = System.currentTimeMillis();
        if(curtime < latestTime){
            throw new IllegalArgumentException(String.format("This current time is %ld less than the latest time of id %ld",
                    curtime,latestTime));
        }
        synchronized (serid){
            if(curtime == latestTime){
                //id生成时间在毫秒之内
                serid = (serid+1)%SER_ID_MAX;
                if (serid == 0) return nextId();
            }else {
                serid = 0L;
            }
        }
        latestTime = curtime;
        return parseId(curtime,dataid,macid,serid);
    }
}
