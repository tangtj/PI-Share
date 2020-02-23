package cn.tangtj.pishare.web;

import cn.tangtj.pishare.dispense.ComputeDispense;
import cn.tangtj.pishare.domain.R;
import cn.tangtj.pishare.domain.vo.ComputeJobResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class IndexController {


    @Autowired
    private ComputeDispense computeDispense;

    /**
     *  注册客户端
     */
    @GetMapping("/register")
    public String join(HttpSession session){
        return "1";
    }

    @GetMapping("/getJob")
    public R getJob(){
        return R.ok(computeDispense.dispense());
    }

    @PostMapping("/postJob")
    public R postJob(@RequestBody ComputeJobResult result){
        computeDispense.reclaim(result);
        return R.ok();
    }
}
