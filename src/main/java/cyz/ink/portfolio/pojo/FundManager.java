package cyz.ink.portfolio.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fundManager")
public class FundManager {

    @Id
    private int id;
    private String name;
    private String password;
    private String phone;
    private int money;

}
