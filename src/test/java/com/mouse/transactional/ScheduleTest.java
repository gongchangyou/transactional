package com.mouse.transactional;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/6/6 23:29
 */
@SpringBootTest
@Slf4j
public class ScheduleTest {
    @Test
    void schedule() {
        new ScheduledThreadPoolExecutor(
                4,
                new ThreadFactory() {
                    private AtomicInteger threadIndex = new AtomicInteger(0);
                    /**
                     * Constructs a new {@code Thread}.  Implementations may also initialize
                     * priority, name, daemon status, {@code ThreadGroup}, etc.
                     *
                     * @param r a runnable to be executed by new thread instance
                     * @return constructed thread, or {@code null} if the request to
                     * create a thread is rejected
                     */
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "test_" + this.threadIndex.incrementAndGet());
                    }
                }
        ).scheduleAtFixedRate(()->{
            log.info("time={}", System.currentTimeMillis());
        }, 0,3, TimeUnit.SECONDS);

        while (true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
