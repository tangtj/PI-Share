package cn.tangtj.pishare.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class ComputeResultBit extends BaseEntity {

    /**
     *  计算时间
     */
    @Column(nullable = false)
    private Date computeTime;

    @Column(nullable = false)
    private Boolean checked = true;

    /**
     *  第多少位数
     */
    @Column(nullable = false,unique = true)
    private Long digit;

    /**
     *  计算结果
     */
    @Column(nullable = false)
    private Integer result;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<ComputeRecord> records;
}