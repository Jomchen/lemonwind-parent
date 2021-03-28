package com.jomkie.web;

import com.jomkie.common.ResultObj;
import com.jomkie.common.UrlContent;
import com.jomkie.dto.JoUserDto;
import com.jomkie.service.JoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jomkie
 * @date 2021/3/28 21:04:59
 * 用户接口
 */
@RestController
public class JoUserController {

    @Autowired
    private JoUserService joUserService;

    /**
     * @author Jomkie
     * @date 2021/3/28 21:05:30
     * 查询所有用户
     */
    @GetMapping(UrlContent.USER_GET_ALL)
    public ResultObj<List<JoUserDto>> getAll() {
        List<JoUserDto> list = joUserService.getAll();
        return ResultObj.success(list);
    }

}
