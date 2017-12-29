package com.vova.tessarwebserver.domain;

/**
 * @author: Vova
 * @create: date 11:52 2017/12/29
 */
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CityMapper {

    @Select("SELECT id, name, state, country FROM city WHERE state = #{state}")
    City findByState(String state);

}

