package com.ryan.autocond.service.impl;

import com.ryan.autocond.entity.User;
import com.ryan.autocond.mapper.UserMapper;
import com.ryan.autocond.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuyi
 * @since 2020-09-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
