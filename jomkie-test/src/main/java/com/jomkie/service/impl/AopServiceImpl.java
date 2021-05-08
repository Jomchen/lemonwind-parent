package com.jomkie.service.impl;

import com.jomkie.common.ResultObj;
import com.jomkie.service.AopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Jomkie
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
