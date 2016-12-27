package cn.succy.jpa.test;

import cn.succy.jpa.entity.Customer;
import cn.succy.jpa.entity.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * jpa的测试类
 *
 * @author Succy
 * @date 2016-12-17 11:22
 **/

public class JPATest {
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction transaction;

    @Before
    public void init() {
        emf = Persistence.createEntityManagerFactory("jpa-demo1");
        em = emf.createEntityManager();
        transaction = em.getTransaction();
        transaction.begin();
    }

    @After
    public void destroy() {
        transaction.commit();
        em.close();
        emf.close();
    }

    @Test
    public void testJpa() {
        // 1、创建EntityManagerFactory
        String persistenceName = "jpa-demo1";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceName);

        // 2、创建EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 3、开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 4、进行持久化操作
        Customer customer = new Customer();
        customer.setAge(20);
        customer.setEmail("succygsx@sina.com");
        customer.setLastName("succy");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        entityManager.persist(customer);

        // 5、提交事务
        transaction.commit();

        // 6、关闭EntityManager
        entityManager.close();

        // 7、关闭EntityManagerFactory
        entityManagerFactory.close();

    }

    /**
     * 测试对于订单表中的单向多对一映射
     */
    /*@Test
    public void testManyToOnePersistence() {
        Customer customer = new Customer();
        customer.setCreateTime(new Date());
        customer.setBirth(new Date());
        customer.setLastName("ZZ");
        customer.setAge(24);
        customer.setEmail("zz@163.com");

        Order order1 = new Order();
        order1.setCreateTime(new Date());
        order1.setCustomer(customer);
        order1.setOrderName("zz-2016-1");

        Order order2 = new Order();
        order2.setCreateTime(new Date());
        order2.setCustomer(customer);
        order2.setOrderName("zz-2016-2");

        // 注：先持久化1的一端，再持久化n的一端，可以减少额外的update开销
        em.persist(customer);
        em.persist(order1);
        em.persist(order2);
    }*/

    /**
     * 测试关联查询
     */
   /* @Test
    public void testManyToOneFind() {
        Order order = em.find(Order.class, 1);
        // 这里如果没有指定使用懒加载，将会使用左外连接
        System.out.println(order.getOrderName());
        // 可以在ManyToOne里指定fetch属性来做到懒加载
        System.out.println(order.getCustomer().getLastName());
    }*/
    @Test
    public void testManyToOneDelete() {
        // 直接删除1的一端是否可行？
        Customer customer = em.find(Customer.class, 9);
        em.remove(customer);
        // result: 单向多对一结果是，不管在@ManyToOne里边是否指定cascade = CascadeType.REMOVE
        // 都是没有办法进行级联删除的，如果想要级联删除，可以采取双向1-n关系
    }

    /**
     * 使用1-n关联映射，不可避免多发update语句
     */
    @Test
    public void testOneToManyPersistence() {
        Customer customer = new Customer();
        customer.setCreateTime(new Date());
        customer.setBirth(new Date());
        customer.setLastName("KK");
        customer.setAge(24);
        customer.setEmail("kk@163.com");

        Order order1 = new Order();
        order1.setCreateTime(new Date());
        order1.setOrderName("kk-2016-1");

        Order order2 = new Order();
        order2.setCreateTime(new Date());
        order2.setOrderName("kk-2016-2");

        order1.setCustomer(customer);
        order2.setCustomer(customer);

//        customer.getOrders().add(order1);
//        customer.getOrders().add(order2);

        em.persist(order1);
        em.persist(order2);
        em.persist(customer);
    }
}
