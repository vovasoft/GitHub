package com.vova.tessarwebserver.dbmapper;


import com.vova.tessarwebserver.domain.newadd.NewAddDay;
import org.apache.ibatis.annotations.*;
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


//    //
//    @Select("<script> " +
//            "SELECT * " +
//            "from ${tableName} " + "<where>" +
//            " <if test= \"cid != null\"> cID=#{cid}</if> " +
//            " <if test= \"gid != null\"> AND gID=#{gid}</if> " +
//            " <if test= \"sid != null\"> AND sID=#{sid}</if> " +
//            " <if test= \"sDate != null\">" +
//            "<![CDATA[ and dateID >= #{sDate,jdbcType=DATE}]]>"+
//            "</if> "+
//            " <if test= \"eDate != null\">" +
//            "<![CDATA[ and dateID <= #{eDate,jdbcType=DATE}]]>"+
//            "</if> " +
//            " </where> " +
//            " </script> ")
    List<NewAddDay> findCGSListByTimes(@Param("tableName") String tableName, @Param("cid") String cid, @Param("gid") String gid,
                                       @Param("sid") String sid, @Param("sDate") Date sDate, @Param("eDate") Date eDate);

    @Select("select * from ${tableName} where dateID >= #{sDate} AND dateID <= #{eDate} ")
    List<NewAddDay> findAllListByTimes(@Param("tableName") String tableName, @Param("sDate") Date sDate, @Param("eDate") Date eDate);
}


