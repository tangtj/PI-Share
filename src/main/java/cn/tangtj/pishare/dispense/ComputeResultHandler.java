package cn.tangtj.pishare.dispense;

import cn.tangtj.pishare.dao.ComputeResultBitDao;
import cn.tangtj.pishare.domain.entity.ComputeRecord;
import cn.tangtj.pishare.domain.entity.ComputeResultBit;
import cn.tangtj.pishare.domain.vo.ComputeJobResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

            var target = computeResultBitDao.findTopByDigit(result.getBit());
            if (target == null) {

                //保存到数据库
                ComputeResultBit bit = new ComputeResultBit();
                bit.setComputeTime(result.getStartTime());
                bit.setDigit(result.getBit());
                bit.setResult(result.getResult());
                var record = new ComputeRecord();
                record.setToken(result.getProcessId());
                record.setComputeDate(new Date());
                record.setResult(result.getResult());
                computeResultBitDao.save(bit);
            }else {
                var records =  target.getRecords();
                if (records == null){
                    records = new ArrayList<>();
                }
                var record = new ComputeRecord();
                record.setToken(result.getProcessId());
                record.setComputeDate(new Date());
                record.setResult(result.getResult());
                records.add(record);
                target.setRecords(records);
                computeResultBitDao.save(target);
            }
        } catch (Exception e) {
            log.error("处理计算数据保存异常:", e);
        }
    }
}
