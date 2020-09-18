package com.nc.meet.modules.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nc.meet.modules.app.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
