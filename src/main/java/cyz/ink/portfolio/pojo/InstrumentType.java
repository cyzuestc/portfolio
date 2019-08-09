package cyz.ink.portfolio.pojo;

import java.util.HashMap;

/**
 * @ Author      : Zink
 * @ Date        : Created in 16:29 2019/8/9
 * @ Description : Bonds, Stocks, Futures and ETFs
 * @ Version     : 1.0
 **/

public enum InstrumentType {
    Bonds("Bonds",1),
    Stocks("Stocks",2),
    Futures("Futures",3),
    ETFs("ETFs",4);

    //instrument Name
    String type;

    //instrument index
    int index;

    public String getType(){
        return type;
    }
    public int getIndex(){
        return index;
    }

    InstrumentType(String type,int index) {
        this.type = type;
        this.index = index;
    }
}
