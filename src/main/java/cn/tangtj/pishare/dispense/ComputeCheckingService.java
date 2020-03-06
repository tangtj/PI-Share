package cn.tangtj.pishare.dispense;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class ComputeCheckingService {

    private HashMap<String, Set<Long>> store = new HashMap<>();

    public void add(String token,Long bit){
        if (store.containsKey(token)){
            Set<Long> bits = store.get(token);
            bits.add(bit);
        }else {
            Set<Long> bits = new HashSet<>();
            bits.add(bit);
            store.putIfAbsent(token,bits);
        }
    }



}
