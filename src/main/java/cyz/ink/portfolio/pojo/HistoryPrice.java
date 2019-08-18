package cyz.ink.portfolio.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @ Author      : Zink
 * @ Date        : Created in 16:51 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/
@Data
@Entity
@Slf4j
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Table(name = "historyprice")
public class HistoryPrice {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float price;
    private float high;
    private float low;
    private float open;
    private float close;
    private float changed;
    @JsonFormat(pattern = "YYYY-MM-dd", timezone = "GMT+8")
    private Date date;

    @ManyToOne(targetEntity = Instrument.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "history_price_instrument", referencedColumnName = "id")
    private Instrument instrument;

}
