package com.xcloudeye.stats;

import com.sdicons.json.mapper.MapperException;
import com.xcloudeye.stats.logic.QueryCsvLogic;
import com.xcloudeye.stats.util.DateFormatUtil;
import com.xcloudeye.stats.util.StaticValueUtil;
import com.xcloudeye.stats.util.UploadDownloadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@Controller
@RequestMapping("/csv")
public class QuerCSVController {
	
	private QueryCsvLogic queryCsvLogic ;

	/**
	 * @param app The id of app.
	 * @param start Query start time.
	 * @param end Query end time.
	 * @return Return the data of active_query with jsonp data format.
	 * @throws MapperException
	 */
	@RequestMapping(value = "/active_download",
			method = RequestMethod.GET)
	public 	String
	activeQuery(@RequestParam String app, @RequestParam String start
			, @RequestParam String end, HttpServletRequest request, HttpServletResponse response)
			throws MapperException, ParseException, IOException {
		Integer appid = Integer.valueOf(app);
		Integer startTime = Integer.valueOf(start);
		Integer endTime = Integer.valueOf(end);

		String fileName = DateFormatUtil.intToDate(startTime)+"_ac.csv";
		String downloadPath = StaticValueUtil.DOWNLOAD_PATH + fileName;

		queryCsvLogic = new QueryCsvLogic(appid);
		queryCsvLogic.createActiveCsv(startTime, endTime);

		UploadDownloadUtil.downloadFile(downloadPath, fileName, request, response);

		return null;
	}


	@RequestMapping(value = "/payment_download",
			method = RequestMethod.GET)
	public 	String
	paymentQuery(@RequestParam String app, HttpServletRequest request, HttpServletResponse response)
			throws MapperException, ParseException, IOException {
		Integer appid = Integer.valueOf(app);

		String fileName = DateFormatUtil.getCurrentDateByFormat()+"_pay.csv";
		String downloadPath = StaticValueUtil.DOWNLOAD_PATH + fileName;

		queryCsvLogic = new QueryCsvLogic(appid);
		queryCsvLogic.createPaymentCsv(fileName);

		UploadDownloadUtil.downloadFile(downloadPath, fileName, request, response);

		return null;
	}
}
 