package cn.succy.jpa.test;

import cn.succy.jpa.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * 使用JPA对数据库进行CRUD操作
 *
 * @author Succy
 * @date 2016-12-18 22:50
 **/

public class CRUDTest {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-demo1");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void destroy() {
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    // 测试查询，调用find方法时就会发出sql语句来查询
    @Test
    public void testFind() {
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println("=========test========");
        System.out.println(customer);
    }

    // 同样是测试查询，但是与find不一样的是，该方法是使用到要查询的对象
    // 时才会发出sql，具有延迟加载
    @Test
    public void testGetReference() {
        Customer customer = entityManager.getReference(Customer.class, 1);
        System.out.println("=========test========");
        System.out.println(customer);
    }

    // 把对象持久化，但是要注意的是，对象不能设置id
    @Test
    public void testPersistence() {
        Customer customer = new Customer();
        customer.setCreateTime(new Date());
        customer.setBirth(new Date());
        customer.setLastName("Mary");
        customer.setAge(24);
        customer.setEmail("mary@163.com");
        entityManager.persist(customer);
    }

    // 测试移除数据库的数据，但是该方法不能删除游离对象
    @Test
    public void testRemove() {
        // 这样子是可以删除的
        Customer customer = entityManager.find(Customer.class, 1);
        entityManager.remove(customer);
        // 下面这种样子是不可以删除的
//        Customer c = new Customer();
//        c.setId(1);
//        entityManager.remove(c);
    }

    /**
     * merge方法，有点类似Hibernate的SaveOrUpdate，下面分情况讨论
     */
    // 情况1、传入的参数是一个临时对象
    // 底层会先创建一个游离对象，再把临时对象的属性复制到游离对象中
    // 接着对游离对象进行持久化操作
    @Test
    public void testMerge1() {
        Customer customer = new Customer();
        customer.setEmail("zs@sina.com");
        customer.setAge(25);
        customer.setLastName("ZS");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        // 此时，该对象是没有id的
        System.out.println(customer.getId());
        Customer customer1 = entityManager.merge(customer);
        // 再看一下执行完merge方法之后，返回的对象是怎么样子的
        // 结果：有id，并且有customer所有的属性，在之前还看到了sql语句
        System.out.println(customer1);
    }

    // 情况2、要传到方法中的是一个游离对象
    // 1 如果EntityManager的缓存中没有该id的对象
    // 2 如果数据库的记录中没有对应游离对象id的记录
    // 3 底层会创建一个游离对象，id是根据生成策略生成的
    // 然后把参数的游离对象属性复制进去，执行持久化操作
    @Test
    public void testMerge2() {
        Customer customer = new Customer();
        customer.setEmail("ls@sina.com");
        customer.setAge(25);
        customer.setLastName("LS");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        // 设置id为10，此时的数据库是没有id=10的记录
        customer.setId(10);

        // 此时的sql是先执行了一次查询，根据id进行查询，发现没有该记录
        // 之后才是对复制过后的游离对象进行插入操作
        Customer customer1 = entityManager.merge(customer);

        // 这两个对象不是同一个对象，结果是false
        System.out.println(customer == customer1);
        // 分别打印一下两个对象，结果是不一样的
        System.out.println("customer# " + customer);
        System.out.println("customer1# " + customer1);
    }

    // 情况3、要传到方法中的是一个游离对象
    // 1 如果EntityManager的缓存中没有该id的对象
    // 2 如果数据库的记录中有对应游离对象id的记录
    // 3 底层会返回查询到的持久化对象，接着，将游离对象的属性
    // 复制到持久化对象中，执行更新操作
    @Test
    public void testMerge3() {
        Customer customer = new Customer();
        customer.setEmail("ls@sina.com");
        customer.setAge(24);
        customer.setLastName("LS");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setId(4);

        // 此时的sql是先执行了一次查询，根据id进行查询，若有记录，返回的是持久化对象，
        // 再把游离对象复制到持久化对象中，接着做持久化操作
        Customer customer1 = entityManager.merge(customer);
        System.out.println(customer1);
    }

    // 情况4、要传到方法中的是一个游离对象
    // 1 如果EntityManager的缓存中有该id的对象
    // 2 如果数据库的记录中有对应游离对象id的记录
    // 3 底层会返回缓存的持久化对象，接着，将游离对象的属性
    // 复制到缓存对象中，执行更新操作
    @Test
    public void testMerge4() {
        Customer customer = new Customer();
        customer.setEmail("ls@sina.com");
        customer.setAge(24);
        customer.setLastName("LS");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setId(4);

        Customer customer1 = entityManager.find(Customer.class, 4);
        System.out.println(customer1);

        Customer customer2 = entityManager.merge(customer);
        System.out.println(customer2);
    }
}
