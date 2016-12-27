package cn.succy.jpa.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 顾客类
 *
 * @author Succy
 * @date 2016-12-17 11:15
 **/

@Table(name = "customer")
@Entity
public class Customer {
    private Integer id;
    private String lastName;
    private String email;
    private int age;
    private Date createTime;
    private Date birth;

    private Set<Order> orders = new HashSet<>();

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * 使用双向一对多关联映射，关系维护应交给多的一方去维护
     *
     * @return
     */
//    @JoinColumn(name = "customer_id")
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.REMOVE})
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                ", birth=" + birth +
                '}';
    }
}
