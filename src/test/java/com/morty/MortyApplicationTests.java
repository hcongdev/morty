package com.morty;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MortyApplicationTests {

    @Test
    public void contextLoads() {
    }

    /**
     * 用于生成activiti的表
     * @param args
     */
    @Test
    public void test(){
        ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        pec.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        pec.setJdbcUrl("jdbc:mysql://localhost:3306/morty?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true&serverTimezone=UTC");
        pec.setJdbcUsername("root");
        pec.setJdbcPassword("123456");

        /**
         * DB_SCHEMA_UPDATE_FALSE 不能自动创建表，需要表存在
         * create-drop 先删除表再创建表
         * DB_SCHEMA_UPDATE_TRUE 如何表不存在，自动创建和更新表
         */
        pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        // 获取流程引擎对象
        ProcessEngine processEngine=pec.buildProcessEngine();
    }

}
