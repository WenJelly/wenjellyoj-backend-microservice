package com.wenjelly.wenjellyojbackenduserservice.controller.inner;

/*
 * @time 2024/6/18 14:12
 * @package com.wenjelly.wenjellyojbackenduserservice.controller.inner
 * @project wenjellyoj-backend-microservice
 * @author WenJelly
 */

import com.wenjelly.wenjellyojbacjendmodel.model.entity.User;
import com.wenjelly.wenjellyojbackendserviceclient.service.UserFeignClient;
import com.wenjelly.wenjellyojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 内部调用，处理中转过来的请求
 */
@RestController
@RequestMapping("/inner")
public class InnerUserController implements UserFeignClient {

    @Resource
    private UserService userService;

    /**
     * 根据用户id获取用户
     *
     * @param userId 用户id
     * @return 用户对象
     */
    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }

    /**
     * 根据id列表返回用户列表
     *
     * @param idList id列表
     * @return 用户集合
     */
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList) {
        return userService.listByIds(idList);
    }
}
