package pairproject.foodmap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootTest
public class ConnectionTest {

    @Test
    void DB연결확인() {
        try (
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/foodmap??characterEncoding=UTF-8",
                        "foodmap",
                        "java1234"
                )) {
            System.out.println(con);
        } catch (Exception e) {

        }
    }
}
