package cn.tangtj.pishare.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class ComputeInfo extends BaseEntity {

    @Column(nullable = false)
    private Long maxBits;
}
