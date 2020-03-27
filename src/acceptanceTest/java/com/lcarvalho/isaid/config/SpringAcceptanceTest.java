package com.lcarvalho.isaid.config;

import com.lcarvalho.isaid.api.ApiApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(
  classes = ApiApplication.class,
  loader = SpringBootContextLoader.class)
@WebAppConfiguration
@SpringBootTest
public class SpringAcceptanceTest {
 
}