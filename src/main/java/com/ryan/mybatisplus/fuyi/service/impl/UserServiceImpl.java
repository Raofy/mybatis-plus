package com.ryan.mybatisplus.fuyi.service.impl;

import com.ryan.mybatisplus.fuyi.entity.User;
import com.ryan.mybatisplus.fuyi.mapper.UserMapper;
import com.ryan.mybatisplus.fuyi.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuyi
 * @since 2020-09-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
