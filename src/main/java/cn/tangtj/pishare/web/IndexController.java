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


    private final ComputeDispense computeDispense;

    private final ClientService clientService;

    public IndexController(ClientService clientService, ComputeDispense computeDispense) {
        this.clientService = clientService;
        this.computeDispense = computeDispense;
    }

    /**
     *  注册客户端
     */
    @GetMapping("/register")
    public R join(){
        ClientRegisterInfo info =clientService.register();
        return R.ok(info);
    }

    @RequestMapping("/getJob")
    public R getJob(@RequestHeader("token") String clientId){
        clientService.heart(clientId);
        return R.ok(computeDispense.dispense());
    }

    @PostMapping("/postJob")
    public R postJob(@RequestBody ComputeJobResult result,@RequestHeader("token") String clientId){
        clientService.heart(clientId);
        result.setProcessId(clientId);
        computeDispense.reclaim(result);
        return R.ok();
    }

    @GetMapping("/clientsInfo")
    public R getClientsInfo(){
        return R.ok(clientService.getClientsInfo());
    }
}
