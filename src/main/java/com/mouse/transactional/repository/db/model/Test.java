package com.mouse.transactional.repository.db.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value="test", autoResultMap=true)
public class Test {
  /**
   * 主键
   */
  @TableId(type = IdType.AUTO)
  private Long id;
  /**
   * 值
   */
  private Long value;
  private Long version;

  @TableField(typeHandler = JacksonTypeHandler.class)
  private List<Integer> json;

  /**
   * 这里无法正常转换，parse的时候key还是String
   */
  @TableField(typeHandler = JacksonTypeHandler.class)
  private Map<Integer, String> map;

  /**
   * 这里无法正常转换, parse list时，元素不是A的实例
   * 而是linkedHashMap
   */
  @TableField(typeHandler = JacksonTypeHandler.class)
  private List<A> objList;

  @Data
  @Builder
  public static class A {
    private int a;
  }

}
