package cn.tangtj.pishare.dao;

import cn.tangtj.pishare.domain.entity.ComputeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ComputeInfoDao extends JpaRepository<ComputeInfo,Long> {

    @Transactional
    @Modifying
    @Query("update ComputeInfo set maxBits = :bits + maxBits")
    Integer updateMaxBit(@Param("bits") Long bits);

    @Query(value = "select c.max_bits from compute_info c order by c.max_bits limit 1",nativeQuery = true)
    Long queryMxxBit();
}
