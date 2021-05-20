package com.jomkie.service.impl;

import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.remote.RemoteRequestObj;
import com.jomkie.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RemoteApi remoteApi;

    @Override
    public String testNormal() {
        Map<String, String> map = new HashMap<>();
        map.put("takeStockCode", "takeStockCode");
        map.put("takeStockName", "takeStockName");

        String result = remoteApi.postRequest(RemoteRequestObj.build(
                "http://127.0.0.1:8088/take/stock/find/page/status/list",
                map)
        );
        return result;
    }

}
