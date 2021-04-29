package com.jomkie.web;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.annotations.ReqValidGroup;
import com.jomkie.annotations.user.UserGroup;
import com.jomkie.common.BaseResponse;
import com.jomkie.common.ResultObj;
import com.jomkie.common.UrlContent;
import com.jomkie.dto.JoUserDto;
import com.jomkie.service.JoUserService;
import com.jomkie.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jomkie
 * @since 2021/3/28 21:04:59
 * 用户接口
 */
@RestController
@Slf4j
public class JoUserController {

    @Autowired
    private JoUserService joUserService;

    @Autowired
    private TestService testService;

    /**
     * @author Jomkie
     * @since 2021-04-07 10:26
     * 用户添加
     */
    @PostMapping(UrlContent.NET_USER_ADD)
    public ResultObj<String> addUser(
            @RequestBody
            @ReqValidGroup(value = UserGroup.UserAdd.class)
                    JoUserDto dto) {

        log.info("进入了方法 addUser: {}", JSONObject.toJSONString(dto));
        return ResultObj.success("addUser 请求成功");
    }

    /**
     * @author Jomkie
     * @since 2021-04-07 10:26
     * 用户更新
     */
    @GetMapping(UrlContent.NET_USER_DEL)
    public ResultObj<String> delUser(
            @RequestBody
            @ReqValidGroup(value = UserGroup.UserDel.class)
            @PathVariable("id") Long id) {

        if (null == id) {
            return ResultObj.fail(BaseResponse.PARAM_ERROR);
        }

        log.info("进入了方法 delUser: {}", id);
        return ResultObj.success("delUser 请求成功");
    }
    
    /**
     * @author Jomkie
     * @since 2021-04-7 10:26
     * 用户更新
     */
    @PostMapping(UrlContent.NET_USER_UPDATE)
    public ResultObj<String> updateUser(
            @RequestBody
            @ReqValidGroup(value = UserGroup.UserUpdate.class)
                    JoUserDto dto) {

        log.info("进入了方法 updateUser: {}", JSONObject.toJSONString(dto));
        return ResultObj.success("updateUser 请求成功");
    }

    /**
     * @author Jomkie
     * @since 2021/3/29 12:28
     * 通过id查询用户
     */
    @GetMapping(UrlContent.NET_USER_GET_ONE)
    public ResultObj<JoUserDto> getById(@PathVariable("id") Long id) {
        log.info("进入了方法 getById");

        JoUserDto dto = joUserService.getById(id);
        return ResultObj.success(dto);
    }

    /**
     * @author Jomkie
     * @since 2021/3/28 21:05:30
     * 查询所有用户
     */
    @GetMapping(UrlContent.NET_USER_GET_ALL)
    public ResultObj<List<JoUserDto>> getAll() {
        log.info("进入了方法 getAll");

        List<JoUserDto> list = joUserService.getAll();
        testService.test();
        return ResultObj.success(list);
    }

    /**
     * @author Jomkie
     * @since 2021-04-29 16:1:31
     * 测试 aop 拦截进行构建参数自动执行
     */
    @PostMapping(UrlContent.NET_USER_CHECK_BUILD_PARAM)
    public ResultObj<Void> checkBuildParam(@RequestBody JoUserDto joUserDto) {
        log.info("entered the method checkBuildParam, buildParam is {}", joUserDto.getTestBuildParamData());
        return ResultObj.success();
    }

}
