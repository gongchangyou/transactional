package com.mouse.transactional.repository.db.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/6/12 22:28
 */
@Table("test")
@Data
public class TestModel {
    @Id
    private Long id;
    /**
     * 值
     */
    @Column("value")
    private Long value;
}
