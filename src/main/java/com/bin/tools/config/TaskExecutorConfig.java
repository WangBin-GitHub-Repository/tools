package com.bin.tools.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池
 *
 * @author bin.wang
 * @version 1.0 2020/9/25
 */
@Configuration
public class TaskExecutorConfig {

    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        // 设置最大线程数
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        // 设置队列容量
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        // 设置默认线程名称
        executor.setThreadNamePrefix("TaskExecutor-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
