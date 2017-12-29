package com.vova.tessarwebserver.dbmapper;


import com.vova.tessarwebserver.domain.newadd.NewAddDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.Date;
import java.util.List;

/**
 * @author: Vova
 * @create: date 15:40 2017/12/29
 */

@Mapper
public interface AllInOneMapper {
    @Select("select * from ${tableName} where cID = #{cid} AND gID = #{gid} AND sID = #{sid} AND dateID >= #{sDate} AND dateID <= #{eDate} ")
    List<NewAddDay> findCGSListByTimes(@Param("tableName") String tableName,@Param("cid")String cid,@Param("gid") String gid,
                                    @Param("sid") String sid,@Param("sDate") Date sDate,@Param("eDate") Date eDate);

    @Select("select * from ${tableName} where dateID >= #{sDate} AND dateID <= #{eDate} ")
    List<NewAddDay> findAllListByTimes(@Param("tableName") String tableName,@Param("sDate") Date sDate,@Param("eDate") Date eDate);
}


