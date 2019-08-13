package cyz.ink.portfolio.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * @ Author      : Zink
 * @ Date        : Created in 16:25 2019/8/9
 * @ Description : admin
 * @ Version     : 1.0
 **/

@Data
@Entity
@Slf4j
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Table(name = "Admin")
public class Admin {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;

    }
}
