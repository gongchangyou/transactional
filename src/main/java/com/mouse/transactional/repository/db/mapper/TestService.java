package com.mouse.transactional.repository.db.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.transactional.repository.db.model.Test;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2021/11/17 7:45 下午
 */
@Slf4j
@Service
public class TestService extends ServiceImpl<TestMapper, Test> implements IService<Test> {

    @Transactional(rollbackFor = Exception.class)
    public void incr(long id) throws Exception {
        val model = baseMapper.selectOne(new LambdaQueryWrapper<Test>()
                .eq(com.mouse.transactional.repository.db.model.Test::getId, 1L)
                .last(" for update"));
        log.info("value={}", model.getValue());
        model.setValue(model.getValue() + 1);
        updateById(model);
        throw new Exception("wer");
    }

}
