package cn.tangtj.pishare.dispense;

import cn.tangtj.pishare.domain.vo.ComputeJobResult;
import lombok.Data;

import java.util.List;

/**
 * @author TANG
 */
@Data
public class ComputeJob {

    private Long bit;

    private List<ComputeJobResult> results;

}
