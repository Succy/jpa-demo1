package cn.succy.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 部门类
 *
 * @author Succy
 * @date 2016-12-27 13:42
 **/
@Table(name = "department")
@Entity
public class Department {
    private Integer id;
    private String deptName;
    private Manager mgr;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "dept_name")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    // 一对一映射，外键唯一约束
    @JoinColumn(name = "mgr_id",unique = true)
    @OneToOne
    public Manager getMgr() {
        return mgr;
    }

    public void setMgr(Manager mgr) {
        this.mgr = mgr;
    }
}
