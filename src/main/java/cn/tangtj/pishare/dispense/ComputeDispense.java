package cn.tangtj.pishare.dispense;

import cn.tangtj.pishare.dao.ComputeResultBitDao;
import cn.tangtj.pishare.dao.ComputeResultDao;
import cn.tangtj.pishare.domain.entity.ComputeResult;
import cn.tangtj.pishare.domain.vo.ComputeJob;
import cn.tangtj.pishare.domain.vo.ComputeJobResult;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    private Set<ComputeJobResult> results = new HashSet<>();

    private final ComputeResultDao computeResultDao;

    private final ComputeResultHandler computeResultHandler;

    public ComputeDispense(ComputeResultDao computeResultDao, ComputeResultHandler computeResultHandler) {
        this.computeResultDao = computeResultDao;
        this.computeResultHandler = computeResultHandler;
        fillJob();
    }


    /**
     *  分发任务
     * @return
     */
    public synchronized ComputeJob dispense(){
        if (jobs.isEmpty()){
            fillJob();
        }
        ComputeJob job = new ComputeJob();
        job.setBit(getBit());
        return job;
    }

    public synchronized void reclaim(ComputeJobResult result){

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
            List<ComputeJobResult> r = results.stream()
                    .sorted(Comparator.comparing(ComputeJobResult::getBit))
                    .collect(Collectors.toList());
            results.clear();

            ComputeResult rs = new ComputeResult();
            rs.setLength((long) r.size());
            rs.setStartIndex(r.get(0).getBit());
            rs.setEndIndex(rs.getStartIndex()+r.size());

            final StringBuilder s = new StringBuilder();

            r.forEach(i-> s.append(HEX_NUM[i.getResult()]));
            rs.setResult(s.toString());

            computeResultDao.save(rs);
        }
    }

    private synchronized Long getBit(){

        Long bit = jobs.pop();
        jobs.addLast(bit);
        return bit;
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
