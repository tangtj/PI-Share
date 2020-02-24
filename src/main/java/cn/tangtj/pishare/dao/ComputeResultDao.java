package cn.tangtj.pishare.dao;

import cn.tangtj.pishare.domain.entity.ComputeResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author TANG
 */
@Repository
public interface ComputeResultDao extends JpaRepository<ComputeResult,Long> {

    /**
     *  查找当前最后计算位数
     * @return
     */
    ComputeResult findTopByOrderByEndIndexDesc();
}
