package cn.succy.jpa.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单类
 *
 * @author Succy
 * @date 2016-12-24 12:06
 **/

@Table(name = "orders")
@Entity
public class Order {
    private Integer id;
    private String orderName;
    private Date createTime;
    private Customer customer;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "order_name")
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", createTime=" + createTime +
                ", customer=" + customer +
                '}';
    }
}
