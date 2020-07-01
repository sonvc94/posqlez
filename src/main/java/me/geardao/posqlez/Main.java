package me.geardao.posqlez;

import me.geardao.posqlez.connection.ConnectionManager;
import me.geardao.posqlez.entity.Emp;
import me.geardao.posqlez.json.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] agrs) throws SQLException {
        PreparedStatement pstmt;
        Connection connection = ConnectionManager.getInstance().getDataSource().getConnection();
        pstmt = connection.prepareStatement("SELECT * FROM erp.emp");
        ResultSet resultSet = pstmt.executeQuery();
        ObjectMapper<Emp> objectMapper = new ObjectMapper<>(Emp.class);
        List<Emp> list = objectMapper.map(resultSet);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        connection.close();
        ConnectionManager.getInstance().getDataSource().close();
    }
}
