package com.xcloudeye.stats.logic;

import java.util.*;

import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.validator.impl.predicates.Int;
import com.xcloudeye.stats.domain.app.*;
import com.xcloudeye.stats.domain.db.*;
import com.xcloudeye.stats.util.DateUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.xcloudeye.stats.dao.AdvanceDao;
import com.xcloudeye.stats.single.SingleMongo;
import com.xcloudeye.stats.util.StaticValueUtil;

public class AppRebuildLogic {
	
	private static final int BLOODSTRIK_ID = 1001;
	private static final int PLATFORM_ID = 7001;
	private static final int WON_ID = 2001;
	
	private AdvanceDao advanceDao;
	private static Set<String> Set=new HashSet<String>();


	/**
	* <p>Title: </p
	* <p>Description: 默认是对bloodstrike进行操作</p>
	*/
	public AppRebuildLogic(){
	}
	
	/**
	* <p>Title: initLogic</p>
	* <p>Description: 初始化数据库</p>
	* @param appid
	*/
	public void initLogic(int appid){
		SingleMongo singleMongo = SingleMongo.getSinglemongo();
		ClassPathXmlApplicationContext ctx = singleMongo.getDaoContext();
		switch (appid) {
		case BLOODSTRIK_ID:
			this.advanceDao = ctx.getBean("bsAdvanceDao", AdvanceDao.class);
			break;
		case WON_ID:
			this.advanceDao = ctx.getBean("wonAdvanceDao", AdvanceDao.class);
			break;
		default:
			break;
		}
	}
	
	/**
	* <p>Title: getUserActive</p>
	* <p>Description: 根据输入的时间范围内用户留存数据，如果数据库中没有处理后的数据返回null</p>
	* @param start 计算时段的起始时间
	* @param end 计算时段的终止时间
	* @return 返回接口定义的数据结构对象
	*/
	public UserRetention getUserRetention(Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<RetentionData> channels = advanceDao.readAppUserRetention(query);
		if (channels.size() == 0 || channels.equals(null)) {
			return new UserRetention("user_retention", start, end, null);
		}
		return new UserRetention("user_retention", start, end, channels);
	}
	
	/*--------------------------------------user new-------------------------------------------------*/
	
	/**
	* <p>Title: getUserNewDetails</p>
	* <p>Description: 计算每个渠道按日期 新用户数</p>
	* @param ch
	* @param start
	* @param end
	* @return
	*/
	private List<UserNewDetail> getUserNewDetails(String ch, Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is(ch));
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<DailyData> dailyData = advanceDao.getDailyChannels(query);
		
		List<UserNewDetail> userNewDetails = new ArrayList<UserNewDetail>();
		
		for (int i = 0; i < dailyData.size(); i++) {
			userNewDetails.add(new UserNewDetail(dailyData.get(i).getDate()
					, dailyData.get(i).getNew_user()));
		}
		
		return userNewDetails;
	}
	
	/**
	* <p>Title: getUserNew</p>
	* <p>Description: 根据输入的时间范围内计算每天的新注册用户数据，如果数据库中没有处理后的数据返回null</p>
	* @param start 计算时段的起始时间
	* @param end 计算时段的终止时间
	* @return 返回接口定义的数据结构对象
	*/
	public UserNew getUserNew(Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<String> chs = advanceDao.readDistinctDaily("channel"
				, query.getQueryObject(), StaticValueUtil.ADVANCE_DAILY);
		System.out.println("UserNew--channels:"+chs.size());
		
		List<UserNewChannel> channels = new ArrayList<UserNewChannel>();
		
		Iterator ch_cursor = chs.iterator();
		while (ch_cursor.hasNext()) {
			String ch = ch_cursor.next().toString();
			DailyData dailyOne = advanceDao.getDailyOneChannel(new Query(Criteria.where("channel").is(ch)));
			channels.add(new UserNewChannel(ch, dailyOne.getParent(), dailyOne.getChild()
					, dailyOne.getSeq(), getUserNewDetails(ch, start, end)));
		}
		
		return new UserNew("app", "user_new", null, channels);
	}

	/**
	 * <p>Title: getUserNewByCh</p>
	 * <p>Description: 根据输入的时间范围内计算每天的新注册用户数据，如果数据库中没有处理后的数据返回null</p>
	 * @param channel 指定渠道的值
	 * @param start 计算时段的起始时间
	 * @param end 计算时段的终止时间
	 * @return 返回接口定义的数据结构对象
	 */
	public UserNew getUserNewByCh(String channel, Integer start, Integer end){

		List<UserNewChannel> channels = new ArrayList<UserNewChannel>();
		DailyData dailyOne = advanceDao.getDailyOneChannel(new Query(Criteria.where("channel").is(channel)));
		if(dailyOne == null ){
			return new UserNew("app", "user_new", null, channels);
		}
		channels.add(new UserNewChannel(channel, dailyOne.getParent(), dailyOne.getChild()
				, dailyOne.getSeq(), getUserNewDetails(channel, start, end)));

		return new UserNew("app", "user_new", null, channels);
	}
	
	/*------------------------------------user active---------------------------------------------------*/
	
	/**
	* <p>Title: getUserNewDetails</p>
	* <p>Description: 计算每个渠道按日期 新用户数</p>
	* @param ch
	* @param start
	* @param end
	* @return
	*/
	private List<UserActiveDetail> getUserActiveDetails(String ch, Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is(ch));
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<DailyData> dailyData = advanceDao.getDailyChannels(query);
		
		List<UserActiveDetail> userActiveDetails = new ArrayList<UserActiveDetail>();
		
		for (int i = 0; i < dailyData.size(); i++) {
			userActiveDetails.add(new UserActiveDetail(dailyData.get(i).getDate()
					, dailyData.get(i).getNew_user(), dailyData.get(i).getActive_user()));
		}
		
		return userActiveDetails;
	}
	
	/**
	* <p>Title: getUserActive</p>
	* <p>Description: 返回指定时间的User Active 接口数据</p>
	* @param start
	* @param end
	* @return
	*/
	public UserActive getUserActive(Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<String> chs = advanceDao.readDistinctDaily("channel"
				, query.getQueryObject(), StaticValueUtil.ADVANCE_DAILY);
		
		List<UserActiveChannel> channels = new ArrayList<UserActiveChannel>();
		
		Iterator ch_cursor = chs.iterator();
		while (ch_cursor.hasNext()) {
			String ch = ch_cursor.next().toString();
			DailyData dailyOne = advanceDao.getDailyOneChannel(new Query(Criteria.where("channel").is(ch)));
			channels.add(new UserActiveChannel(ch, dailyOne.getParent(), dailyOne.getChild()
					, dailyOne.getSeq(), getUserActiveDetails(ch, start, end)));
		}
		
		return new UserActive("app", "user_active", null, channels);
	}
	
	/*----------------------------------------user pay-----------------------------------------------*/
	
	private List<UserPayDetail> getUserPayDetails(String ch, Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is(ch));
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<DailyData> dailyData = advanceDao.getDailyChannels(query);
		
		List<UserPayDetail> details = new ArrayList<UserPayDetail>();
		
		for (int i = 0; i < dailyData.size(); i++) {
			details.add(new UserPayDetail(dailyData.get(i).getDate(), dailyData.get(i).getNew_payer()
					, dailyData.get(i).getPayer(), dailyData.get(i).getAll_order(), dailyData.get(i).getPayed_order()));
		}
		
		return details;
	}
	
	
	public UserPay getUserPay(Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<String> chs = advanceDao.readDistinctDaily("channel"
				, query.getQueryObject(), StaticValueUtil.ADVANCE_DAILY);
		
		List<UserPayChannel> channels = new ArrayList<UserPayChannel>();
		
		Iterator ch_cursor = chs.iterator();
		while (ch_cursor.hasNext()) {
			String ch = ch_cursor.next().toString();
			DailyData dailyOne = advanceDao.getDailyOneChannel(new Query(Criteria.where("channel").is(ch)));
			channels.add(new UserPayChannel(ch, dailyOne.getParent(), dailyOne.getChild()
					, dailyOne.getSeq(), getUserPayDetails(ch, start, end)));
		}
		
		return new UserPay("app", "user_pay", null, channels);
	}
	
	/*------------------------------------app rt---------------------------------------------------*/
	
	/**
	* <p>Title: getAppRtDetails</p>
	* <p>Description: channel =all 的渠道表示部分渠道的总值</p>
	* @param start
	* @param end
	* @return
	*/
	private List<AppRtDetail> getAppRtDetails(Integer start, Integer end){
		
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is("all"));
		criterias.add(Criteria.where("time").gte(start));
		criterias.add(Criteria.where("time").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
			
		List<FifteenData> fifData = advanceDao.getFifteenChannels(query);

		List<AppRtDetail> details = new ArrayList<AppRtDetail>();
		
		for (int i = 0; i < fifData.size(); i++) {
			details.add(new AppRtDetail(fifData.get(i).getTime(), fifData.get(i).getNew_user()
					, fifData.get(i).getActive_user(), fifData.get(i).getPayer(), fifData.get(i).getNew_payer()
					, fifData.get(i).getAll_order(), fifData.get(i).getPayed_order(), fifData.get(i).getIncome()));
		}
		
		return details;
	}


	//五分钟新注册用户
	private List<FiveNewUserDetail> getFiveNewUserDetails(Integer start, Integer end){

		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is("all"));
		criterias.add(Criteria.where("time").gte(start));
		criterias.add(Criteria.where("time").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);

		List<FiveNewUserDate> fiveNewUserData = advanceDao.getNewUser(query);

		List<FiveNewUserDetail> details = new ArrayList<FiveNewUserDetail>();

		for (int i = 0; i < fiveNewUserData.size(); i++) {
			details.add(new FiveNewUserDetail(fiveNewUserData.get(i).getTime(), fiveNewUserData.get(i).getNew_user()));
		}

		return details;
	}
	/**
	 * <p>Title: getActInFiveMinutesDetail</p>
	 * <p>Description: channel =all 的渠道表示部分渠道的总值</p>
	 * @param start
	 * @param end
	 * @return
	 */
	private List<ActInFiveMinutesDetail> getActInFiveMinutesDetail(Integer start, Integer end){

		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is("all"));
		criterias.add(Criteria.where("time").gte(start));
		criterias.add(Criteria.where("time").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);

		List<FiveActData> fiveData = advanceDao.getFiveAct(query);

		List<ActInFiveMinutesDetail> details = new ArrayList<ActInFiveMinutesDetail>();

		Map<Integer,Integer> detailMap = new HashMap<>();
		List<Integer> timeLine = new ArrayList<Integer>();

		for (int i=0;i<StaticValueUtil.FIVE_MINUTES_COUNT ;i++){
			timeLine.add(start + i * 60 * 5);
		}

		int currentDate = DateUtil.getCurrentTimeSecond()/86400*86400+StaticValueUtil.THREE_HOURS_SECONDS;

		if(start==currentDate){
			if(fiveData !=null&&fiveData.size()!=0) {
				for (int j = 0; j <fiveData.size(); j++) {
					detailMap.put(fiveData.get(j).getTime(), 0);
				}
			}else {
				for (int m = 0; m < timeLine.size(); m++) {
					Integer t = timeLine.get(m);
					detailMap.put(t, 0);
				}
			}
		}else{
			for (int m = 0; m <timeLine.size(); m++) {
				Integer t = timeLine.get(m);
				detailMap.put(t, 0);
			}
			System.out.print("message:"+detailMap.size());
		}

		for(int i = 0;i<fiveData.size();i++){
			detailMap.put(fiveData.get(i).getTime(), fiveData.get(i).getActive_user());
		}
		for (Integer key : detailMap.keySet()) {
			Integer value = detailMap.get(key);
			details.add(new ActInFiveMinutesDetail(key,value));
		}

//		for (int i = 0; i < fiveData.size(); i++) {
//			details.add(new ActInFiveMinutesDetail(fiveData.get(i).getTime(),fiveData.get(i).getActive_user()));
//		}


		return details;
	}


	/**
	 * <p>Title: getRepeatFiveMinutesDetail</p>
	 * 五分钟不去重
	 * <p>Description: channel =all 的渠道表示部分渠道的总值</p>
	 * @param start
	 * @param end
	 * @return
	 */
	private List<RepeatFiveMinutesDetail> getRepeatFiveMinutesDetail(Integer start, Integer end){

		List<Criteria> criterias = new ArrayList<Criteria>();
//		criterias.add(Criteria.where("channel").is("all"));
		criterias.add(Criteria.where("time").gte(start));
		criterias.add(Criteria.where("time").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);

		List<RepeatFiveActData> repeatfiveData = advanceDao.getRepeatFiveAct(query);

		List<RepeatFiveMinutesDetail> details = new ArrayList<RepeatFiveMinutesDetail>();

		Map<Integer,Integer> detailMap = new HashMap<>();
		System.out.println(detailMap.size()+"detailMap.size");
		List<Integer> timeLine = new ArrayList<Integer>();

		for (int i=0;i<StaticValueUtil.FIVE_MINUTES_COUNT ;i++){
			     timeLine.add(start + i * 60 * 5);
		}

		int currentDate = DateUtil.getCurrentTimeSecond()/86400*86400+StaticValueUtil.THREE_HOURS_SECONDS;

		if(start==currentDate){
			if(repeatfiveData !=null&&repeatfiveData.size()!=0) {
				for (int j = 0; j <repeatfiveData.size(); j++) {
						detailMap.put(repeatfiveData.get(j).getTime(), 0);
				}
			}else {
				for (int m = 0; m <timeLine.size(); m++) {
					Integer t = timeLine.get(m);
					detailMap.put(t, 0);
				}
			  }
			}else{
			for (int m = 0; m <timeLine.size(); m++) {
				Integer t = timeLine.get(m);
				detailMap.put(t, 0);
			}
		}

		for(int i = 0;i<repeatfiveData.size();i++){
			detailMap.put(repeatfiveData.get(i).getTime(), repeatfiveData.get(i).getActive_user());
		}
		for (Integer key : detailMap.keySet()) {
			Integer value = detailMap.get(key);
			details.add(new RepeatFiveMinutesDetail(key,value));
		}
		return details;
	}


	/**
	* <p>Title: getAppRt</p>
	* <p>Description: app 数据部分渠道15分钟显示</p>
	* @param date
	* @return
	*/
	public AppRt getAppRt(Integer date){
		Integer start = date;
		Integer end = date + StaticValueUtil.ONE_DAY_SECONDS; 
		
		return new AppRt("app", "app_rt", null, getAppRtDetails(start, end));
	}


	/**
	 * <p>Title: getFiveMinutesAct</p>
	 * <p>Description: act_user 数据部分渠道5分钟显示</p>
	 * @param date
	 * @return
	 */
	public ActInFiveMinutes getFiveMinutesAct(Integer date){
		Integer start = date;
		Integer end = date + StaticValueUtil.ONE_DAY_SECONDS;

		return new ActInFiveMinutes("app", "fiveminutes_act", null, getActInFiveMinutesDetail(start, end));
	}


	/**
	 * <p>Title: getFiveMinutesAct</p>
	 * 五分钟数据不去重复
	 * <p>Description: act_user 数据部分渠道5分钟显示</p>
	 * @param date
	 * @return
	 */
	public RepeatFiveMinutes getRepeatMinutesAct(Integer date){
		Integer start = date;
		Integer end = date + StaticValueUtil.ONE_DAY_SECONDS;

		return new RepeatFiveMinutes("app", "repeatfiveminutes_act", null, getRepeatFiveMinutesDetail(start, end));
	}


	/**
	 * <p>Title: getFiveMinutesAct</p>
	 * 五分钟新注册用户
	 * <p>Description: act_user 数据部分渠道5分钟显示</p>
	 * @param date
	 * @return
	 */
	public FiveNewUser getFiveNewUser(Integer date){
		Integer start = date;
		Integer end = date + StaticValueUtil.ONE_DAY_SECONDS;

		return new FiveNewUser("app", "five_new_user", null, getFiveNewUserDetails(start, end));
	}
	
	/*---------------------------------------app trend------------------------------------------------*/
	
	
	public AppTrend getAppTrend(Integer start, Integer end){
		AppTrendGeneric generic = advanceDao.getGeneric().get(0);
		
		List<AppTrendDetail> details = new ArrayList<AppTrendDetail>();
		List<AppTrendTrend> trend = new ArrayList<AppTrendTrend>();
		
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is("all"));
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
			
		List<DailyData> dailyData = advanceDao.getDailyChannels(query);
		for (int i = 0; i < dailyData.size(); i++) {
			trend.add(new AppTrendTrend(dailyData.get(i).getDate()
					, dailyData.get(i).getNew_user(), dailyData.get(i).getActive_user()));
			details.add(new AppTrendDetail(dailyData.get(i).getDate(), dailyData.get(i).getPayer()
					, dailyData.get(i).getNew_payer(), dailyData.get(i).getIncome()));
		}
		return new AppTrend("app", "app_trend", null, generic, trend, details);
	}
	
	/*------------------------------------ch detail---------------------------------------------------*/
	
	private List<ChannelDetailDetail> getChDetails(String ch, Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is(ch));
		criterias.add(Criteria.where("time").gte(start));
		criterias.add(Criteria.where("time").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<FifteenData> data = advanceDao.getFifteenChannels(query);
		
		List<ChannelDetailDetail> details = new ArrayList<ChannelDetailDetail>();
		
		for (int i = 0; i < data.size(); i++) {
			details.add(new ChannelDetailDetail(data.get(i).getTime(), data.get(i).getNew_user()));
		}
		
		return details;
	}
	
	public ChannelDetail getChDetail(Integer date){
		Integer start = date;
		Integer end = date + StaticValueUtil.ONE_DAY_SECONDS;
		
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("time").gte(start));
		criterias.add(Criteria.where("time").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<String> chs = advanceDao.readDistinctDaily("channel"
				, query.getQueryObject(), StaticValueUtil.ADVANCE_FIFTEEN);
		
		List<ChannelDetailChannel> channels = new ArrayList<ChannelDetailChannel>();
		
		Iterator ch_cursor = chs.iterator();
		while (ch_cursor.hasNext()) {
			String ch = ch_cursor.next().toString();
			FifteenData chOne = advanceDao.getFifteenOneChannel(new Query(Criteria.where("channel").is(ch)));
			channels.add(new ChannelDetailChannel(ch, chOne.getParent(), chOne.getChild()
					, chOne.getSeq(), getChDetails(ch, start, end)));
		}
		
		return new ChannelDetail("app", "ch_detail", null, channels);
	}
	
	/*--------------------------------------ch list-------------------------------------------------*/
	
	private List<ChannelListDetail> getChDetailDetail(String ch, Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is(ch));
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<DailyData> dailyData = advanceDao.getDailyChannels(query);
		
		List<ChannelListDetail> details = new ArrayList<ChannelListDetail>();
		
		for (int i = 0; i < dailyData.size(); i++) {
			System.out.println("payer---:"+dailyData.get(i).getPayer());
			System.out.println("new_payer---:"+dailyData.get(i).getNew_payer());
			details.add(new ChannelListDetail(dailyData.get(i).getDate(),  dailyData.get(i).getNew_user()
					, dailyData.get(i).getActive_user(), dailyData.get(i).getPayer(), dailyData.get(i).getNew_payer()
					, dailyData.get(i).getAll_order(), dailyData.get(i).getPayed_order(), dailyData.get(i).getIncome()));
		}
		
		return details;
	}
 
	
	public ChannelList getChList(Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<String> chs = advanceDao.readDistinctDaily("channel"
								, query.getQueryObject(), StaticValueUtil.ADVANCE_DAILY);
		System.out.println("UserNew--channels:"+chs.size());
		
		List<ChannelListChannel> channels = new ArrayList<ChannelListChannel>();
		
		Iterator ch_cursor = chs.iterator();
		while (ch_cursor.hasNext()) {
			String ch = ch_cursor.next().toString();
			DailyData dailyOne = advanceDao.getDailyOneChannel(new Query(Criteria.where("channel").is(ch)));
			channels.add(new ChannelListChannel(ch, dailyOne.getParent(), dailyOne.getChild()
					, dailyOne.getSeq(), getChDetailDetail(ch, start, end)));
		}
		
		return new ChannelList("app", "ch_list", null, channels);
	} 

	/*---------------------------------------pay trend------------------------------------------------*/
	
	private List<PayTrendDetail> getPayTrendDetail(String ch, Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is(ch));
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<DailyData> dailyData = advanceDao.getDailyChannels(query);
		
		List<PayTrendDetail> details = new ArrayList<PayTrendDetail>();
		
		for (int i = 0; i < dailyData.size(); i++) {
			details.add(new PayTrendDetail(dailyData.get(i).getDate(), dailyData.get(i).getPayer()
					, dailyData.get(i).getAll_order(), dailyData.get(i).getPayed_order(), dailyData.get(i).getIncome()));
		}
		
		return details;
	}
 
	
	public PayTrend getPayTrend(Integer start, Integer end){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("date").gte(start));
		criterias.add(Criteria.where("date").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		List<String> chs = advanceDao.readDistinctDaily("channel"
								, query.getQueryObject(), StaticValueUtil.ADVANCE_DAILY);
		System.out.println("UserNew--channels:"+chs.size());
		
		List<PayTrendChannel> channels = new ArrayList<PayTrendChannel>();
		
		Iterator ch_cursor = chs.iterator();
		while (ch_cursor.hasNext()) {
			String ch = ch_cursor.next().toString();
			DailyData dailyOne = advanceDao.getDailyOneChannel(new Query(Criteria.where("channel").is(ch)));
			channels.add(new PayTrendChannel(ch, dailyOne.getParent(), dailyOne.getChild()
					, dailyOne.getSeq(), getPayTrendDetail(ch, start, end)));
		}
		
		return new PayTrend("app", "pay_trend", null, channels);
	}
	/**
	 *  实施 计算 微端 留存
	 * @param start
	 *  @param end
	 * @param day
	 * @return
	 * @throws MapperException
	 */
	public  String RententionDailySchedule(int start,int end,int day) throws MapperException {
		int startTime = start+StaticValueUtil.ONE_DAY_SECONDS*day;
		int endTime = end+StaticValueUtil.ONE_DAY_SECONDS*day;
		if(Set.isEmpty()){
			Set=advanceDao.getRegOrLogByTime(start, end, "reg");
		}
		Set<String> regIdSet=new HashSet<String>(Set);
		String str="";
		if(!regIdSet.isEmpty()) {
			int reg=regIdSet.size();
			Set<String> logIdSet = advanceDao.getRegOrLogByTime(startTime, endTime, "log");
			regIdSet.removeAll(logIdSet);
			int Rent=reg-regIdSet.size();
			//str="the "+day+" day :login "+Rent+"  ,reg :"+reg+" \n";
			str=Rent+"";
			regIdSet.clear();
		}else{
			str="0";
		}
		return str;
	}
	public String getSetSize(){
		return String.valueOf(Set.size());
	}
	public void clearSet(){
		Set.clear();
	}
	
	
}
