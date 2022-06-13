package com.mouse.transactional;

import com.mouse.transactional.repository.db.mapper.TestMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
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
public class TypeHandlerTest {
    @Autowired
    private TestMapper testMapper;

    @Test
    void test() {
        val model = com.mouse.transactional.repository.db.model.Test.builder()
                .json(new ArrayList<>(){{
                    add(3);
                    add(4);
                }})
                .map(new HashMap<>(){{
                    put( 1,"a");
                    put(2, "b");
                }})
                .objList(new ArrayList<>(){{
                    add(com.mouse.transactional.repository.db.model.Test.A.builder().a(33).build());
                }})
                .build();
        testMapper.insert(model);
        val test = testMapper.selectById(model.getId());
        log.info("test={}",test);
        val test1 = testMapper.selectById(20);
        log.info("test1={}",test1);
    }
}
