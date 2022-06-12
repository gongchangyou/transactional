package com.mouse.transactional.repository.db.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("test")
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

}
