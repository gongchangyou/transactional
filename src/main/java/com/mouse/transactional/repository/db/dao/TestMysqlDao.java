package com.mouse.transactional.repository.db.dao;

import com.mouse.transactional.repository.db.model.TestModel;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/6/12 22:30
 */
@Component
public interface TestMysqlDao extends CrudRepository<TestModel, Long> {
    @Modifying
    @Query("update test set value=value-1 where id= (:id)")
    boolean updateValue(@Param("id") Long id);

}
