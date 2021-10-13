package com.example.letmesub.dbTest;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static java.lang.Class.forName;

public class MySQLConnectionTest
{
    // MYSQL Connector 의 클래스 DB 연결 드라이버 정의
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // DB 경로
    private static final String URL = "jdbc:mysql://localhost:3306/LetMeSub?serverTimeZone=UTC&allowpublickeyRetrieval=true&CharacterEncoding=UTF-8";
    private static final String USER = "minsang";
    private static final String PASSWORD = "minsang";
    
    @Test
    public void testConnection() throws Exception
    {
        Class.forName(DRIVER);
        try{
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(connection);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
}
