package cn.tangtj.pishare.client;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TANG
 */
@Slf4j
@Service
public class ClientService {

    private final Cache<String,String> clients = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();


    public ClientRegisterInfo register(){
        String clientId = UUID.randomUUID().toString();
        clients.put(clientId,clientId);
        ClientRegisterInfo info = new ClientRegisterInfo();
        info.setClientId(clientId);
        return info;
    }

    public void heart(String clientId){
        clients.getIfPresent(clientId);
    }

    public long clientNum(){
        return clients.size();
    }


}
