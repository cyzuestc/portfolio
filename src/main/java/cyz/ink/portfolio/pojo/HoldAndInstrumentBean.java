package cyz.ink.portfolio.pojo;

import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * @ Author      : Zink
 * @ Date        : Created in 0:20 2019/8/16
 * @ Description : use this bean to store joint bean instead of join table by onetomany
 * @ Version     : 1.0
 **/
@Data
public class HoldAndInstrumentBean {
    Hold hold;
    CurrentPrice currentPrice;
    Instrument instrument;
    int totalPages;
}
