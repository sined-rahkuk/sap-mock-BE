package com.mwaysolution.sapMock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("test")
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	Environment env;

    @Test
    public void shouldOverrideDefaultPropWithTestProp() {
    	assertThat(env.getProperty("spring.datasource.driver-class-name")).isEqualTo("org.h2.Driver");
    	assertThat(env.getProperty("spring.jpa.properties.hibernate.dialect")).isEqualTo("org.hibernate.dialect.H2Dialect");
    	
    }
}
