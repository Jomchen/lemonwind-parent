package com.lemonwind.test.service.impl;

import com.lemonwind.test.common.ResultObj;
import com.lemonwind.test.service.AopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lemonwind
 * @since 2021-05-08 11:15:31
 * 业务层
 */
@Service("aopServiceImpl")
@Slf4j
public class AopServiceImpl implements AopService {

    @Override
    public ResultObj<String> testAopServerAndLog() {
        log.info("entered -----------------------> AopServiceImpl.testAopServerAndLog()");
        return ResultObj.success("AopServiceImpl.testAopServerAndLog");
    }

}
