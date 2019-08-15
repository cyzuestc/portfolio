package cyz.ink.portfolio.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

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
@Table(name = "currentprice")
public class CurrentPrice {
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
    @Column(name = "instrument_id")
    private int instrumentId;
    @JsonFormat(pattern = "YYYY-MM-dd", timezone = "GMT+8")
    Date date;

}
