package cn.tangtj.pishare.web;

import cn.tangtj.pishare.client.ClientRegisterInfo;
import cn.tangtj.pishare.client.ClientService;
import cn.tangtj.pishare.dispense.ComputeDispense;
import cn.tangtj.pishare.domain.R;
import cn.tangtj.pishare.domain.vo.ComputeJobResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class IndexController {


    @Autowired
    private ComputeDispense computeDispense;

    @Autowired
    private ClientService clientService;

    /**
     *  注册客户端
     */
    @GetMapping("/register")
    public R join(){
        ClientRegisterInfo info =clientService.register();
        return R.ok(info);
    }

    @GetMapping("/getJob")
    public R getJob(@RequestHeader("token") String clientId){
        return R.ok(computeDispense.dispense());
    }

    @PostMapping("/postJob")
    public R postJob(@RequestBody ComputeJobResult result,@RequestHeader("token") String clientId){

        computeDispense.reclaim(result);
        return R.ok();
    }
}
