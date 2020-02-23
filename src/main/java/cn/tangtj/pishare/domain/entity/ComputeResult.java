package cn.tangtj.pishare.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class ComputeResult extends BaseEntity {

    /**
     *  结果
     */
    @Column(nullable = false)
    private String result;

    /**
     *  开始位数
     */
    @Column(nullable = false)
    private Long startIndex;

    /**
     *  开始结束
     */
    @Column(nullable = false)
    private Long endIndex;

    /**
     *  长度
     */
    @Column(nullable = false)
    private Long length;
}
