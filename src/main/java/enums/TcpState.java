package enums;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.EnumSet;

public enum TcpState {
    CLOSED{
        @Override
        public TcpState handle(EnumSet<TcpFlag> flags) throws Exception {
            if(!CheckExpect(flags,TcpFlag.SYN)) return CLOSED;
            return SYN_SEND;
        }
    },
    LISTEN{
        @Override
        public TcpState next(EnumSet<TcpFlag> flags) throws Exception {
            if(CheckExpect(flags,TcpFlag.RST)) return CLOSED;
            if(!CheckExpect(flags,TcpFlag.SYN)) return LISTEN;
            return SYN_REVD;
        }

        @Override
        public TcpState handle(EnumSet<TcpFlag> flags) throws Exception {
            if(!CheckExpect(flags,TcpFlag.SYN,TcpFlag.ACK)) return LISTEN;
            return SYN_REVD;
        }
    },
    SYN_REVD{
        @Override
        public TcpState next(EnumSet<TcpFlag> flags) throws Exception{
            if (!CheckExpect(flags,TcpFlag.ACK)) return SYN_REVD;
            return ESTABLISHED;
        }

        @Override
        public TcpState handle(EnumSet<TcpFlag> flags) throws Exception {
            if(CheckExpect(flags,TcpFlag.ACK)) return ESTABLISHED;
            if(!CheckExpect(flags,TcpFlag.FIN)) return SYN_REVD;
            return FIN_WAIT_1;
        }
    },
    ESTABLISHED{
        @Override
        public TcpState next(EnumSet<TcpFlag> flags) throws Exception{
            if(!CheckExpect(flags,TcpFlag.FIN)) return ESTABLISHED;
            return CLOSED_WAIT;
        }

        @Override
        public TcpState handle(EnumSet<TcpFlag> flags) throws Exception {
            if(!CheckExpect(flags,TcpFlag.FIN)) return ESTABLISHED;
            return FIN_WAIT_1;
        }
    },
    CLOSED_WAIT{
        @Override
        public TcpState next(EnumSet<TcpFlag> flags) throws Exception{
            //发送FIN 报文
            return LAST_ACK;
        }

        @Override
        public TcpState handle(EnumSet<TcpFlag> flags) throws Exception{
            return LAST_ACK;
        }
    },
    LAST_ACK{
        @Override
        public TcpState next(EnumSet<TcpFlag> flags) throws Exception{
            if(CheckExpect(flags,TcpFlag.FIN)) return CLOSED_WAIT; //前两次回传客户端没收到时回传
            return LAST_ACK;
        }
    },
    SYN_SEND{
        @Override
        public TcpState next(EnumSet<TcpFlag> flags) throws Exception {
            if(!CheckExpect(flags,TcpFlag.SYN,TcpFlag.ACK)) return CLOSED;
            return ESTABLISHED;
        }
    },
    FIN_WAIT_1{
        @Override
        public TcpState next(EnumSet<TcpFlag> flags) throws Exception{
            if(!CheckExpect(flags,TcpFlag.ACK)) return FIN_WAIT_1;
            return FIN_WAIT_2;
        }
    },
    FIN_WAIT_2{
        @Override
        public TcpState next(EnumSet<TcpFlag> flags) throws Exception{
            if(!CheckExpect(flags,TcpFlag.FIN)) return ESTABLISHED; //需要重新发送FIN
            return TIME_WAIT;
        }
    },
    TIME_WAIT{
        @Override
        public TcpState next(EnumSet<TcpFlag> flags) throws Exception{
            if(flags != null) return FIN_WAIT_2;
            return CLOSED;
        }
    };;

    Logger logger = LogManager.getLogger("TcpState");
    public TcpState next(EnumSet<TcpFlag> flags) throws Exception {
        return CLOSED;
    }

    public TcpState handle(EnumSet<TcpFlag> flags) throws Exception {
        return CLOSED;
    }
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
                return false;
            }
        }
        return true;
    }
}
