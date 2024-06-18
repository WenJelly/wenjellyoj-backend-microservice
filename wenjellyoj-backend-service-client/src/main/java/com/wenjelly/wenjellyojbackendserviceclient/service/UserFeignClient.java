package com.wenjelly.wenjellyojbackendserviceclient.service;

import com.wenjelly.wenjellyojbacjendmodel.model.entity.User;
import com.wenjelly.wenjellyojbacjendmodel.model.enums.UserRoleEnum;
import com.wenjelly.wenjellyojbacjendmodel.model.vo.UserVO;
import com.wenjelly.wenjellyojbackendcommon.common.ErrorCode;
import com.wenjelly.wenjellyojbackendcommon.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.wenjelly.wenjellyojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 请求转发 -> 用户模块
 * 负责将接收到的接口使用请求转发给 用户模块 / 内部处理
 */
@FeignClient(name = "wenjellyoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * 根据用户id获取用户
     *
     * @param userId 用户id
     * @return 用户对象
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    /**
     * 根据id列表返回用户列表
     *
     * @param idList id列表
     * @return 用户集合
     */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);

    /**
     * 将用户对象封装成VO
     *
     * @param user 用户对象
     * @return 用户VO
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 判断用户是否有权限
     *
     * @param user 用户对象
     * @return 是否有权限
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 判断用户是否已登录
     *
     * @param request 请求
     * @return 返回当前登录用户，如果用户未登录则返回 未登录 异常
     */
    default User getLoginUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }
}
