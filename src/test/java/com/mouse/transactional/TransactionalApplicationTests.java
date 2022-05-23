package com.mouse.transactional;

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
class TransactionalApplicationTests {
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TestService testService;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10,0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    @Test
    void contextLoads() {
        for (int i = 0; i < 10;i++) {
            val ret = testMapper.insert(
                    com.mouse.transactional.repository.db.model.Test
                    .builder()
                    .id(1L)
                    .value((long) i)
                    .build()
            );
            log.info("ret={}", ret);
        }
    }

    /**
     * 10个线程 最终结果一定不是10
     */
    @Test
    void unThreadSafe() {
        for (int i = 0; i < 10;i++) {
            threadPoolExecutor.submit(() -> {
                val model = testMapper.selectById(1L);
            log.info("start value={}", model.getValue());
            val ret = testMapper.updateById(com.mouse.transactional.repository.db.model.Test
                        .builder()
                        .id(1L)
                        .value(model.getValue() + 1)
                        .build()
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


    /**
     * 这样写也只能是 saveOrUpdate 是原子操作，
     * 但是 selectById 和 saveOrUpdate不是原子的
     */
    @Test
    void saveOrUpdate() {
        for (int i = 0; i < 10;i++) {
            threadPoolExecutor.submit(() -> {
                val model = testMapper.selectById(1L);
                log.info("start value={}", model.getValue());
                val ret = testService.saveOrUpdate(com.mouse.transactional.repository.db.model.Test
                        .builder()
                        .id(1L)
                        .value(model.getValue() + 1)
                        .build()
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
