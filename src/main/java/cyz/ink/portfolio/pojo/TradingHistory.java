package cyz.ink.portfolio.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

/**
 * @ Author      : Zink
 * @ Date        : Created in 16:50 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/
@Data
@Entity
@Slf4j
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Table(name = "TradingHistory")
public class TradingHistory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float price;
    private int volume;
    private Date date;
    @Column(name = "fundManager_id")
    private int fundManagerId;
    @Column(name = "instrument_id")
    private int instrumentId;
}
