package com.smartautomation.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.smartautomation.users.controller.HealthController;
import com.smartautomation.users.controller.UserController;
import com.smartautomation.users.mcp.UserMcpTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartUsersCustomeMcpApplicationTests {

    @Autowired
    private HealthController healthController;

    @Autowired
    private UserController userController;

    @Autowired
    private UserMcpTools userMcpTools;

    @Test
    void contextLoads() {
        assertThat(healthController).isNotNull();
        assertThat(userController).isNotNull();
        assertThat(userMcpTools).isNotNull();
    }
}
