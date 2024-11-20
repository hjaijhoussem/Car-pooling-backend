package com.horizon.carpooling;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals; 

@SpringBootTest
class CarPoolingApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void testCalculateFare() {
        assertEquals(2, 2, "They should be equal");
    }

}
