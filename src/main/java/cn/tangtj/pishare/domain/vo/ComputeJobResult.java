package cn.tangtj.pishare.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ComputeJobResult {

    /**
     *  计算开始时间
     */
    private Date startTime;

    /**
     *  计算完成时间
     */
    private Date endTime;

    /**
     *  计算的位数
     */
    private Long bit;

    /**
     *  计算结果
     */
    private Integer result;

    /**
     *  处理人
     */
    private String processId;

}
