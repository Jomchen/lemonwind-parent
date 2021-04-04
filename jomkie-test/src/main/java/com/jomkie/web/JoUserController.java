package com.jomkie.web;

import com.jomkie.common.ResultObj;
import com.jomkie.common.UrlContent;
import com.jomkie.dto.JoUserDto;
import com.jomkie.service.JoUserService;
import com.jomkie.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Jomkie
 * @date 2021/3/28 21:04:59
 * 用户接口
 */
@RestController
public class JoUserController {

    @Autowired
    private JoUserService joUserService;

    @Autowired
    private TestService testService;

    /**
     * @author Jomkie
     * @date 2021/3/29 12:28
     * 通过id查询用户
     */
    @GetMapping(UrlContent.USER_GET_BY_ID)
    public ResultObj<JoUserDto> getById(@PathVariable("id") Long id) {
        JoUserDto dto = joUserService.getById(id);
        return ResultObj.success(dto);
    }

    /**
     * @author Jomkie
     * @date 2021/3/28 21:05:30
     * 查询所有用户
     */
    @GetMapping(UrlContent.USER_GET_ALL)
    public ResultObj<List<JoUserDto>> getAll() {
        List<JoUserDto> list = joUserService.getAll();
        testService.test();
        return ResultObj.success(list);
    }

}
