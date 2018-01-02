package com.vova.tessarwebserver;

import com.vova.tessarwebserver.domain.newadd.NewAddDay;
import com.vova.tessarwebserver.dbmapper.AllInOneMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TessarWebServerApplicationTests {


	@Autowired
	AllInOneMapper allInOneMapper;

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	@Test
	public void contextLoads() throws ParseException {

		//SELECT * FROM dd1.newaddday where dateID >= '2001-09-25' and dateID <= '2001-09-28';
		String cid = "ngBrazil";
		String sid = "0";
		String gid="bloodstrke";
		String sDate = "2001-09-25";
		String eDate ="2001-09-28";
		List<NewAddDay> nadList= allInOneMapper.findCGSNewAddListByTimes("NewAddWeek",cid,gid,sid,sdf.parse(sDate),sdf.parse(eDate));
		for (NewAddDay newAddDay : nadList) {
			System.out.println(newAddDay.getDateID());
		}
	}



}
