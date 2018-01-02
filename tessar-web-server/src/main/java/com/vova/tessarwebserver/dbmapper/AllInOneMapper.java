package com.vova.tessarwebserver.dbmapper;


import com.vova.tessarwebserver.domain.initdata.SelectList;
import com.vova.tessarwebserver.domain.newadd.NewAddDay;
import com.vova.tessarwebserver.domain.stayman.StayParent;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author: Vova
 * @create: date 15:40 2017/12/29
 */
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
@Mapper
public interface AllInOneMapper {
    @Select("select * from ${tableName} where cID = #{cid} AND gID = #{gid} AND sID = #{sid} AND dateID >= #{sDate} AND dateID <= #{eDate} ")
    List<NewAddDay> findCGSNewAddListByTimes(@Param("tableName") String tableName, @Param("cid") String cid, @Param("gid") String gid,
                                             @Param("sid") String sid, @Param("sDate") Date sDate, @Param("eDate") Date eDate);

    @Select("select * from ${tableName} where dateID >= #{sDate} AND dateID <= #{eDate} ")
    List<NewAddDay> findAllNewAddListByTimes(@Param("tableName") String tableName, @Param("sDate") Date sDate, @Param("eDate") Date eDate);

    @Select("select * from ${tableName} where cID = #{cid} AND gID = #{gid} AND sID = #{sid} AND dateID >= #{sDate} AND dateID <= #{eDate} ")
    List<StayParent> findCGSStayListByTimes(@Param("tableName") String tableName, @Param("cid") String cid, @Param("gid") String gid,
                                            @Param("sid") String sid, @Param("sDate") Date sDate, @Param("eDate") Date eDate);

    //由于数据库中的接口一样，所以这里channellist代替其他两个表的表名
    @Select("select * from ${tableName}")
    List<SelectList> findCGS(@Param("tableName") String tableName);

}


