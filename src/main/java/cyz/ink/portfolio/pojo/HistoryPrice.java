package cyz.ink.portfolio.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
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
@Table(name = "HistoryPrice")
public class HistoryPrice {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "instrument_id")
    private int instrumentId;
    private float price;
    private float high;
    private float low;
    private float open;
    private float close;
    private float changed;
    private Date date;

    public int getId() {
        return id;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public float getPrice() {
        return price;
    }

    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public float getOpen() {
        return open;
    }

    public float getClose() {
        return close;
    }

    public float getChanged() {
        return changed;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public void setChanged(float changed) {
        this.changed = changed;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
