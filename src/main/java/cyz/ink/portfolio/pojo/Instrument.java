package cyz.ink.portfolio.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * @ Author      : Zink
 * @ Date        : Created in 16:28 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/
@Data
@Entity
@Slf4j
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Table(name = "Instrument")
public class Instrument {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String symbol;
    private String name;
    @Enumerated(EnumType.STRING)
    private InstrumentType type;

//    @OneToOne
//    private CurrentPrice currentPrice;
}
