package flowable;

import org.flowable.engine.*;
import org.flowable.engine.test.Deployment;
import org.flowable.spring.ProcessEngineFactoryBean;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.impl.test.FlowableSpringExtension;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(FlowableSpringExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ArticleWorkflowUnitTest.TestConfiguration.class)
public class ArticleWorkflowUnitTest {
    @Autowired
    private RuntimeService runtimeService;
 
    @Autowired
    private TaskService taskService;

    @Configuration(proxyBeanMethods = false)
    @EnableTransactionManagement
    static class TestConfiguration {

        @Bean
        public DataSource dataSource() {
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            dataSource.setDriverClass(org.h2.Driver.class);
            dataSource.setUrl("jdbc:h2:mem:flowable-jupiter;DB_CLOSE_DELAY=1000");
            dataSource.setUsername("sa");
            dataSource.setPassword("");
            return dataSource;
        }

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public SpringProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
            SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
            configuration.setDataSource(dataSource);
            configuration.setTransactionManager(transactionManager);
            configuration.setDatabaseSchemaUpdate("true");
            return configuration;
        }

        @Bean
        public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration processEngineConfiguration) {
            ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
            factoryBean.setProcessEngineConfiguration(processEngineConfiguration);
            return factoryBean;
        }

        @Bean
        public RepositoryService repositoryService(ProcessEngine processEngine) {
            return processEngine.getRepositoryService();
        }

        @Bean
        public RuntimeService runtimeService(ProcessEngine processEngine) {
            return processEngine.getRuntimeService();
        }

        @Bean
        public TaskService taskService(ProcessEngine processEngine) {
            return processEngine.getTaskService();
        }

        @Bean
        public HistoryService historyService(ProcessEngine processEngine) {
            return processEngine.getHistoryService();
        }

        @Bean
        public ManagementService managementService(ProcessEngine processEngine) {
            return processEngine.getManagementService();
        }

    }

    @Test
    @Deployment(resources = { "processes/author-workflow.bpmn20.xml" })
    void articleApprovalTest() {

        Map<String, Object> processVariableMap = new HashMap<>();
        processVariableMap.put("author", "pia@pringle.com");
        processVariableMap.put("url", "http://pringle.com/authorOfTheYear");
 
        runtimeService.startProcessInstanceByKey("articleReview", processVariableMap);
        Task task = taskService.createTaskQuery().singleResult();
 
        assertEquals("Review the submitted article", task.getName());
 
        processVariableMap.put("approved", true);
        taskService.complete(task.getId(), processVariableMap);
 
        assertEquals(0, runtimeService.createProcessInstanceQuery().count());

    }

}