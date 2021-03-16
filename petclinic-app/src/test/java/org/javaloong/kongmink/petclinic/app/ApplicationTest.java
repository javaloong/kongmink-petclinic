package org.javaloong.kongmink.petclinic.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.pf4j.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class ApplicationTest {

    @Autowired ApplicationContext ctx;
    
    @Test
    public void testContextLoads() throws Exception {
        assertNotNull(ctx);
        assertNotNull(ctx.getBean(PluginManager.class));
    }
}
