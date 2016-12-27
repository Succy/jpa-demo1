package cn.succy.jpa.hibernate.dialect;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * 重写hibernate的数据库方言，将默认的建表字符编码修改成utf-8
 * Hibernate默认的建表字符编码是ISO-8859-1，使用该编码，插入
 * 中文会“？？？”，指定url的字符编码为utf-8也不能解决该问题
 * 只有通过拓展方言来实现
 *
 * @author Succy
 * @date 2016-12-27 16:40
 **/

public class MySQL5DialectUTF8 extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
