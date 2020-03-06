package cn.tangtj.pishare.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComputeResultBitDaoTest {

    @Autowired
    private ComputeResultBitDao dao;

    @Test
    void findTop10ByCheckedOrOrderByComputeTime() {
        var result = dao.findTop10ByCheckedOrderByComputeTime(false);
        System.out.println(result);
    }

    @Test
    void findTokne(){
        var result = dao.findNeedChecked("123");
        System.out.println(result);
    }
}