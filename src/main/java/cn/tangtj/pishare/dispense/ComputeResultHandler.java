package cn.tangtj.pishare.dispense;

import cn.tangtj.pishare.dao.ComputeResultBitDao;
import cn.tangtj.pishare.domain.entity.ComputeResultBit;
import cn.tangtj.pishare.domain.vo.ComputeJobResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author TANG
 */
@Slf4j
@Service
public class ComputeResultHandler {

    private final LinkedBlockingQueue<ComputeJobResult> queue = new LinkedBlockingQueue<>();

    private final ComputeResultBitDao computeResultBitDao;

    public ComputeResultHandler(ComputeResultBitDao computeResultBitDao) {
        this.computeResultBitDao = computeResultBitDao;
        new Thread(()-> {
            try {
                exec();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("异常",e);
            }
        }).start();
    }


    private void exec() throws InterruptedException {
        while (true) {
            ComputeJobResult c = queue.take();
            handler(c);
        }
    }

    public void put(ComputeJobResult c) throws InterruptedException {
        if (c == null){
            return;
        }
        queue.put(c);
    }

    private void handler(ComputeJobResult result) {
        try {
            //保存到数据库
            ComputeResultBit bit = new ComputeResultBit();
            bit.setComputeTime(result.getStartTime());
            bit.setDigit(result.getBit());
            bit.setResult(result.getResult());
            computeResultBitDao.save(bit);
        } catch (Exception e) {
            log.error("处理计算数据保存异常:", e);
        }
    }
}
