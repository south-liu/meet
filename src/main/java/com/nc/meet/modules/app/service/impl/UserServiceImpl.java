package com.nc.meet.modules.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nc.meet.common.exception.MeetException;
import com.nc.meet.common.validator.Assert;
import com.nc.meet.modules.app.entity.UserEntity;
import com.nc.meet.modules.app.form.LoginForm;
import com.nc.meet.modules.app.mapper.UserMapper;
import com.nc.meet.modules.app.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

	@Override
	public UserEntity queryByMobile(String mobile) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile));
	}

	@Override
	public long login(LoginForm form) {
		UserEntity user = queryByMobile(form.getMobile());
		if (user == null) {
			throw new MeetException("手机号或密码错误");
		}
		Assert.isNull(user, "手机号或密码错误");

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new MeetException("手机号或密码错误");
		}

		return user.getUserId();
	}
}
