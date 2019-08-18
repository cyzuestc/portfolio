package cyz.ink.portfolio.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "tradinghistory")
public class TradingHistory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float price;
    private int volume;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    @ManyToOne(targetEntity = Instrument.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "history_instrument_id", referencedColumnName = "id")
    private Instrument instrument;

    @ManyToOne(targetEntity = FundManager.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "history_fund_manager_id", referencedColumnName = "id")
    public FundManager fundManager;

}
