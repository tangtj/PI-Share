package cn.tangtj.pishare.dispense;

import cn.tangtj.pishare.dao.ComputeInfoDao;
import cn.tangtj.pishare.dao.ComputeResultBitDao;
import cn.tangtj.pishare.dao.ComputeResultDao;
import cn.tangtj.pishare.domain.entity.ComputeResult;
import cn.tangtj.pishare.domain.entity.ComputeResultBit;
import cn.tangtj.pishare.domain.vo.ComputeJobDto;
import cn.tangtj.pishare.domain.vo.ComputeJobResult;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

/**
 *  分发任务
 */
@Service
public class ComputeDispense {

    public static int JOB_SIZE = 10;

    private static final Character[] HEX_NUM = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    private ConcurrentLinkedDeque<Long> jobs = new ConcurrentLinkedDeque<>();

    private ConcurrentLinkedDeque<ComputeJob> jobss = new ConcurrentLinkedDeque<>();

    private Set<ComputeJobResult> results = new HashSet<>();

    private final ComputeResultDao computeResultDao;

    private final ComputeResultHandler computeResultHandler;

    @Autowired
    private ComputeInfoDao computeInfoDao;

    @Autowired
    private ComputeResultBitDao computeResultBitDao;

    private HashMap<String,ComputePool> computePool = new HashMap<>();

    public ComputeDispense(ComputeResultDao computeResultDao, ComputeResultHandler computeResultHandler) {
        this.computeResultDao = computeResultDao;
        this.computeResultHandler = computeResultHandler;
        fillJob();
    }


    /**
     *  分发任务
     * @return
     */
    public synchronized ComputeJobDto dispense(){
        if (jobs.isEmpty()){
            fillJob();
        }
        ComputeJobDto job = new ComputeJobDto();
        job.setBit(getBit());
        return job;
    }

    private synchronized void init(String token){
        List<ComputeResultBit> resultBits = computeResultBitDao.findNeedChecked(token);
        if (resultBits != null && resultBits.size() > 0){

            ComputePool pool = new ComputePool();
            pool.setToken(token);
            var bits = new ArrayList<Long>();

            for(var i:resultBits){
                bits.add(i.getDigit());
            }
            pool.setBitPool(bits);
            computePool.put(token,pool);
        }

    }

    /**
     *  分发任务
     * @return
     */
    public synchronized ComputeJobDto dispense(String tokenId){
        if (jobs.isEmpty()){
            fillJob();
        }
        if (computePool.containsKey(tokenId)){
            var result  = computePool.get(tokenId);
            result.getBitPool().removeFirst();
        }
        ComputeJobDto job = new ComputeJobDto();
        job.setBit(getBit());
        return job;
    }

    public synchronized void reclaim(ComputeJobResult result){

        if (computePool.containsKey(result.getProcessId())){
            try {
                computeResultHandler.put(result);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        //先检查,是否需要的计算结果
        long bits = result.getBit();
        if (!jobs.contains(bits)){
            return;
        }
        //加入结果集
        results.add(result);

        //保存到数据库
        try {
            computeResultHandler.put(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //从任务队列里移除
        jobs.remove(result.getBit());

        //这次的任务完成了
        if (jobs.size() == 0){
            // 添加新任务
            computeInfoDao.updateMaxBit(10L);
        }
    }

    private synchronized Long getBit(){

        Long bit = jobs.pop();
        jobs.addLast(bit);
        return bit;
    }

    private synchronized ComputeJobDto getJob(String tokenId){
        //加入多人验算,但是一个浏览器验算完了怎么办
        ComputeJob job = jobss.pop();
        jobss.addLast(job);
        var computes = job.getResults();
        for (var c:computes){
            if (StringUtils.equals(c.getProcessId(),tokenId)){
                return null;
            }
        }
        ComputeJobDto d = new ComputeJobDto();
        d.setBit(job.getBit());
        return d;
    }

    /**
     *  填充任务
     */
    private synchronized void fillJob(){

        ComputeResult result = computeResultDao.findTopByOrderByEndIndexDesc();
        long startIndex  = 0L;
        if (result != null) {
            startIndex = result.getEndIndex();
        }
        for (int i = 0; i < ComputeDispense.JOB_SIZE; i++) {
            jobs.add(startIndex + i);
        }
    }
}
