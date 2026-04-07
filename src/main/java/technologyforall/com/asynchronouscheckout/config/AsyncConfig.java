package technologyforall.com.asynchronouscheckout.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
//    @Bean(name = "orderExecutor")
//    public Executor orderExecutor(){
//
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(10);
//        executor.setMaxPoolSize(500);
//        executor.setQueueCapacity(500);
//        executor.setThreadNamePrefix("Order-Exec-");
//        executor.initialize();
//        return executor;
//    }

}
