package cn.tangtj.pishare.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TANG
 */
@Slf4j
@Service
public class ClientService implements HttpSessionListener {

    private AtomicInteger clients = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("有新客户端注册");
        clients.decrementAndGet();
    }

    @Override
    public final void sessionDestroyed(HttpSessionEvent se) {
        log.info("有客户端被注销");
        if (clients.intValue() < 1){
            clients.set(1);
        }
        clients.incrementAndGet();
    }

    public int getClientsNum(){
        return clients.get();
    }
}
