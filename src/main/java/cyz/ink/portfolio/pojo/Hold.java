package cyz.ink.portfolio.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @ Author      : Zink
 * @ Date        : Created in 12:31 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/
@Data
@Entity
@Slf4j
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Table(name = "Hold")
public class Hold {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int volume;

    @Column(name = "fundManager_id")
    private int fundManagerId;

    @Column(name = "instrument_id")
    private int instrumentId;

}
