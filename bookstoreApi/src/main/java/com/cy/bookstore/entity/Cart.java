package com.cy.bookstore.entity;

import java.io.Serializable;
import java.util.Objects;

public class Cart extends BaseEntity implements Serializable {
    private Integer cid;
    private Integer pid;
    private Integer uid;
    private Long price;
    private Integer num;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cid=" + cid +
                ", pid=" + pid +
                ", uid=" + uid +
                ", price=" + price +
                ", num=" + num +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        if (!super.equals(o)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(getCid(), cart.getCid()) && Objects.equals(getPid(), cart.getPid()) && Objects.equals(getUid(), cart.getUid()) && Objects.equals(getPrice(), cart.getPrice()) && Objects.equals(getNum(), cart.getNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCid(), getPid(), getUid(), getPrice(), getNum());
    }
}
