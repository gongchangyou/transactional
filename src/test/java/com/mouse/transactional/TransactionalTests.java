package com.mouse.transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mouse.transactional.repository.db.mapper.TestMapper;
import com.mouse.transactional.repository.db.mapper.TestService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class TransactionalTests {
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TestService testService;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10,0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));


    /**
     * 以下方案也不行, 不是线程安全
     * 无法实现10个线程给 value + 10 的功能
     */
    @Test
    void TransactionalUpdate() {
        for (int i = 0; i < 10;i++) {
            threadPoolExecutor.submit(() -> {
                try {
                    testService.incr(1L);
                } catch (Exception e) {
                    log.error("msg {}", e.getMessage(), e);
                }
            });
        }

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
