package com.lcarvalho.isaid.config;

import com.lcarvalho.isaid.api.ApiApplication;
import com.lcarvalho.isaid.commons.HttpClient;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(
  classes = {ApiApplication.class, EmbeddedDynamoDBInitializer.class, HttpClient.class},
  loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SpringAcceptanceTest {
}