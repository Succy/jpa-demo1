package cn.succy.jpa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 部门经理类
 *
 * @author Succy
 * @date 2016-12-27 13:46
 **/
@Table(name = "manager")
@Entity
public class Manager {
    private Integer id;
    private String mgrName;
    private Department dept;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "mgr_name")
    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    @OneToOne(mappedBy = "mgr", cascade = {CascadeType.REMOVE})
    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}
