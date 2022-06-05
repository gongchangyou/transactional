package com.mouse.transactional;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/5/29 11:47
 */
@SpringBootTest
@Slf4j
public class FutureTest {
    @Test
    void complete() {
        CompletableFuture cf = new CompletableFuture<String>();

        val cf1 = cf.thenApply((Object str) -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return ((String)str).replace("a", "d");
        });

        cf.complete("aad");

        try {
            val value = cf.get();
            val value1 = cf1.get(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            log.info("value={} value1={}",value,value1);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    void timeout() {
        CompletableFuture cf = new CompletableFuture<String>();

        cf.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "xxx";
        });

        cf.orTimeout(2, TimeUnit.SECONDS);

        //这里可以直接complete future
//        new Thread(()->{cf.complete("ddd");}).start();

        try {
            val value = cf.get();
            log.info("value={} ",value);
        } catch (InterruptedException | ExecutionException  e) {
            e.printStackTrace();
        }
    }
}
