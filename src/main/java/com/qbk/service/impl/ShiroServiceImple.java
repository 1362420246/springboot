package com.qbk.service.impl;

import com.qbk.service.ShiroService;
import org.springframework.stereotype.Service;

/**
 * Created by 13624 on 2018/6/25.
 */
@Service
public class ShiroServiceImple implements ShiroService {

    @Override
    public String getPasswordByUsername(String username){
        switch (username){
            case "liming":
                return "123";
            case "hanli":
                return "456";
            default:
                return null;
        }
    }
}
