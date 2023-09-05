package com.learningRoad.dao;


import com.learningRoad.entity.InfoNbrUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InfoNbrUserMapper {
	InfoNbrUser queryInfoNbrUser(String userId);
}
