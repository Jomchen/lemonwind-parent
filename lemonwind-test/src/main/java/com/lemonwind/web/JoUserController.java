package com.lemonwind.web;

import com.alibaba.fastjson.JSONObject;
import com.lemonwind.annotations.ReqValidGroup;
import com.lemonwind.annotations.user.UserGroup;
import com.lemonwind.common.ResultObj;
import com.lemonwind.common.UrlContent;
import com.lemonwind.datastructure.model.JoUser;
import com.lemonwind.dto.JoUserDto;
import com.lemonwind.service.impl.JoUserServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    private JoUserServiceImpl joUserServiceImpl;

    /**
     * 测试分页处理
     * @return
     */
    @GetMapping(UrlContent.NET_USER_HANDLE_PAGE_JOUSER)
    public ResultObj<String> handlePage() {
    	joUserServiceImpl.handlePage(1, 5);
        return ResultObj.success("testJoUser");
    }
    
    /**
     * @author Jomkie
     * @since 2021-04-07 10:26
     * 用户添加
     */
    @PostMapping(UrlContent.NET_USER_ADD)
    @ReqValidGroup(value = UserGroup.UserAdd.class)
    public ResultObj<String> addUser(@RequestBody @Valid JoUserDto dto) {
        log.info("进入了方法 addUser: {}", JSONObject.toJSONString(dto));
        return ResultObj.success("addUser 请求成功");
    }

    /**
     * @author Jomkie
     * @since 2021-04-07 10:26
     * 删除用户
     */
    @GetMapping(UrlContent.NET_USER_DEL)
    @ReqValidGroup(value = UserGroup.UserDel.class)
    public ResultObj<String> delUser(
            @RequestBody
            @NotNull(message = "ID不能为空")
            @PathVariable("id") Long id) {
        log.info("进入了方法 delUser: {}", id);
        return ResultObj.success("delUser 请求成功");
    }
    
    /**
     * @author Jomkie
     * @since 2021-04-7 10:26
     * 用户更新
     */
    @PostMapping(UrlContent.NET_USER_UPDATE)
    @ReqValidGroup(value = UserGroup.UserUpdate.class)
    public ResultObj<List<JoUser>> updateUser(@RequestBody @Valid JoUserDto dto) {
        log.info("进入了方法 updateUser: {}", JSONObject.toJSONString(dto));
        //joUserServiceImpl.updateEntity(dto);
        //return ResultObj.success("updateUser 请求成功");
        return ResultObj.success(joUserServiceImpl.handlePage(1, 5));
    }

    /**
     * @author Jomkie
     * @since 2021/3/29 12:28
     * 通过id查询用户
     */
    @GetMapping(UrlContent.NET_USER_GET_ONE)
    @ReqValidGroup()
    public ResultObj<JoUserDto> getById(
            @NotNull(message = "ID不能为空")
            @PathVariable("id")
                Long id) {
        log.info("进入了方法 getById");
        JoUserDto dto = joUserServiceImpl.getById(id);
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
        List<JoUserDto> list = joUserServiceImpl.getAll();
        return ResultObj.success(list);
    }

    /**
     * @author Jomkie
     * @since 2021-04-29 16:1:31
     * 测试 aop 拦截进行构建参数自动执行
     */
    @PostMapping(UrlContent.NET_USER_CHECK_BUILD_PARAM)
    public ResultObj<Void> checkBuildParam(@RequestBody @Valid JoUserDto joUserDto) {
        log.info("entered the method checkBuildParam, buildParam is {}", joUserDto.getTestBuildParamData());
        return ResultObj.success();
    }

    /**
     * @author Jomkie
     * @since 2021-05-18 20:39:51
     * 测试用户
     */
    @GetMapping(UrlContent.NET_USER_TEST_JOUSER)
    public ResultObj<String> testJoUser() {
        log.info("you entered the method testJoUser.");
        return ResultObj.success("testJoUser");
    }

}
