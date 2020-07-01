package me.geardao.posqlez.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private HikariDataSource dataSource;

    private static class SingletonHolder{
        private static final ConnectionManager instance = new ConnectionManager();
    }

    public static ConnectionManager getInstance(){
        return SingletonHolder.instance;
    }

    private ConnectionManager(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.ds.PGSimpleDataSource");
        config.setJdbcUrl("jdbc:postgresql://35.185.190.106:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("123456");
        config.setIdleTimeout(180000);
        config.setMaxLifetime(180000);
        config.setMaximumPoolSize(20);
        config.setAutoCommit(false);
        config.setConnectionTestQuery("SELECT current_timestamp");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
//        dataSource.setConnectionInitSql("ALTER SESSION SET CURRENT_SCHEMA = erp");
    }

    public HikariDataSource getDataSource(){
        return this.dataSource;
    }

}
