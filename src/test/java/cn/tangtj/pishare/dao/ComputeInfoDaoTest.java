package cn.tangtj.pishare.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ComputeInfoDaoTest {


    @Autowired
    private ComputeInfoDao dao;

    @Test
    void updateMaxBit() {
        dao.updateMaxBit(10L);
    }

    @Test
    void queryMxxBit() {
        var max = dao.queryMxxBit();
        System.out.println(max);
    }
}