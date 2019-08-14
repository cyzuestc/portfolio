package cyz.ink.portfolio.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Data
@Entity
@Slf4j
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Table(name = "FundManager")
public class FundManager {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private float balance;

}
