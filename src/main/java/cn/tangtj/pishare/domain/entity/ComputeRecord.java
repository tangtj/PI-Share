package cn.tangtj.pishare.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class ComputeRecord extends BaseEntity {

    private Date computeDate;

    private String token;

    private Integer result;

}
