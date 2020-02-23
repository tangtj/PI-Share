package cn.tangtj.pishare.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class ComputeResultBit extends BaseEntity {

    /**
     *  计算时间
     */
    @Column(nullable = false)
    private Date computeTime;

    /**
     *  第多少位数
     */
    @Column(nullable = false,unique = true)
    private Long Digit;

    /**
     *  计算结果
     */
    @Column(nullable = false)
    private Integer result;
}