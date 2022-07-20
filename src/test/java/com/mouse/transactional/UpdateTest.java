package com.mouse.transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mouse.transactional.repository.db.dao.TestMysqlDao;
import com.mouse.transactional.repository.db.mapper.TestMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/5/29 11:47
 */
@SpringBootTest
@Slf4j
public class UpdateTest {
    @Autowired
    private TestMysqlDao testMysqlDao;
    @Autowired
    private TestMapper testMapper;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10,0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    @Test
    void singleUpdate() {
            val result = testMysqlDao.updateValue(1L);
            log.info("result={}", result);
    }
    
    @Test
    void findById() {
        val result = testMysqlDao.findById(1L);
        log.info("result={}", result);
    }
    
    @Test
    void update() {
        for (int i = 0; i < 10;i++) {
            threadPoolExecutor.submit(() -> {
                        val result = testMysqlDao.updateValue(1L);
                        log.info("result={}", result);
                    }
            );
        }

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开了10个线程 最后就只有一个线程更新成功
     * ，如果有重试逻辑，那mysql接收到的请求一下就要放大好多倍了
     */
    @Test
    void casUpdate() {
        for (int i = 0; i < 10;i++) {
            threadPoolExecutor.submit(() -> {
                val model = testMapper.selectById(1L);
                log.info("start value={}", model.getValue());
                val ret = testMapper.update(com.mouse.transactional.repository.db.model.Test
                        .builder()
                        .id(1L)
                        .value(model.getValue() + 1)
                                .version(model.getVersion() + 1)
                        .build(), (new LambdaQueryWrapper<com.mouse.transactional.repository.db.model.Test>())
                        .eq(com.mouse.transactional.repository.db.model.Test::getId, 1L)
                        .eq(com.mouse.transactional.repository.db.model.Test::getVersion, model.getVersion())
                );
                log.info("ret={}", ret);
            });
        }

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
