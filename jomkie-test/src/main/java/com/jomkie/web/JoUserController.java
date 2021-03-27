package com.jomkie.web;

import com.jomkie.common.ResultObj;
import com.jomkie.common.UrlContent;
import com.jomkie.dto.JoUserDto;
import com.jomkie.service.JoUserService;

import java.util.List;

@RestController
public class JoUserController {

    @Autowired
    private JoUserService joUserService;

    @GetMapping(UrlContent.USER_GET_ALL)
    public ResultObj<List<JoUserDto>> getAll() {
        List<JoUserDto> list = joUserService.getAll();
        return ResultObj.success(list);
    }

}
