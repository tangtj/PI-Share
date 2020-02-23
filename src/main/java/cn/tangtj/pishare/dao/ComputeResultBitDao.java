package cn.tangtj.pishare.dao;

import cn.tangtj.pishare.domain.entity.ComputeResultBit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputeResultBitDao extends JpaRepository<ComputeResultBit,Long> {
}
