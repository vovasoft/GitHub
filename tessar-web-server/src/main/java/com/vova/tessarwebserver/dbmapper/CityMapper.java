package com.vova.tessarwebserver.dbmapper;

/**
 * @author: Vova
 * @create: date 11:52 2017/12/29
 */
import com.vova.tessarwebserver.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CityMapper {

    @Select("SELECT id, name, state, country FROM city WHERE state = #{state}")
    City findByState(@Param("state") String state);

}

