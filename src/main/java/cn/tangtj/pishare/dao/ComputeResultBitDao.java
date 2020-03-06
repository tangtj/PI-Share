package cn.tangtj.pishare.dao;

import cn.tangtj.pishare.domain.entity.ComputeResultBit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComputeResultBitDao extends JpaRepository<ComputeResultBit,Long> {

    List<ComputeResultBit> findTop10ByCheckedOrderByComputeTime(Boolean checked);

    ComputeResultBit findTopByDigit(Long digit);
}
