package cn.tangtj.pishare.dispense;

import lombok.Data;

import java.util.List;

@Data
public class ComputePool {

    private String token;

    private List<Long> bitPool;
}
