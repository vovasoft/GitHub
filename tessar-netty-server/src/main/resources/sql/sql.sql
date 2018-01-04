-- 支付表
create TABLE PayMentDay
(id int PRIMARY KEY auto_increment
,dateID DATE
,cId VARCHAR (64)
,gId VARCHAR (64)
,sId VARCHAR (64)
,newAddNum INT
,newAddPayPlayerNum INT
,newAddPayAllMoney FLOAT
,newAddPlayerARPPU FLOAT
,newAddPayPlayerARPPU FLOAT
,newPayPlayerNum INT
,newPayAllMoney FLOAT
,averageNewPayMoney FLOAT
,activeNum INT
,payPlayerNum INT
,todayAllPayMoney FLOAT
,activeARPPU INT
,payPlayerARPPU FLOAT
,allMoney FLOAT
);

create TABLE PayMentWeek
(id int PRIMARY KEY auto_increment
,dateID DATE
,cId VARCHAR (64)
,gId VARCHAR (64)
,sId VARCHAR (64)
,newAddNum INT
,newAddPayPlayerNum INT
,newAddPayAllMoney FLOAT
,newAddPlayerARPPU FLOAT
,newAddPayPlayerARPPU FLOAT
,newPayPlayerNum INT
,newPayAllMoney FLOAT
,averageNewPayMoney FLOAT
,activeNum INT
,payPlayerNum INT
,todayAllPayMoney FLOAT
,activeARPPU INT
,payPlayerARPPU FLOAT
,allMoney FLOAT
);

create TABLE PayMentMon
(id int PRIMARY KEY auto_increment
,dateID DATE
,cId VARCHAR (64)
,gId VARCHAR (64)
,sId VARCHAR (64)
,newAddNum INT
,newAddPayPlayerNum INT
,newAddPayAllMoney FLOAT
,newAddPlayerARPPU FLOAT
,newAddPayPlayerARPPU FLOAT
,newPayPlayerNum INT
,newPayAllMoney FLOAT
,averageNewPayMoney FLOAT
,activeNum INT
,payPlayerNum INT
,todayAllPayMoney FLOAT
,activeARPPU INT
,payPlayerARPPU FLOAT
,allMoney FLOAT
);

-- 新增表

create table NewAddDay
(id int PRIMARY KEY auto_increment
,dateID DATE
,cID VARCHAR (64)
,gID VARCHAR (64)
,sID VARCHAR (64)
,newAddNum INT
,activeNum INT
,loginCount INT
,averageLogin FLOAT
,allPlayerNum INT
);


create table NewAddWeek
(id int PRIMARY KEY auto_increment
,dateID DATE
,cID VARCHAR (64)
,gID VARCHAR (64)
,sID VARCHAR (64)
,newAddNum INT
,activeNum INT
,loginCount INT
,averageLogin FLOAT
,allPlayerNum INT
);


create table NewAddMon
(id int PRIMARY KEY auto_increment
,dateID DATE
,cID VARCHAR (64)
,gID VARCHAR (64)
,sID VARCHAR (64)
,newAddNum INT
,activeNum INT
,loginCount INT
,averageLogin FLOAT
,allPlayerNum INT
);

-- 留存表
-- int id, Date dateID, String cID, String gID, String sID, String stayList
create table StayDay
(id int PRIMARY KEY auto_increment
,dateID DATE
,cID VARCHAR (64)
,gID VARCHAR (64)
,sID VARCHAR (64)
,newAddNum INT
,stayList VARCHAR (220)
);

create table StayWeek
(id int PRIMARY KEY auto_increment
,dateID DATE
,cID VARCHAR (64)
,gID VARCHAR (64)
,sID VARCHAR (64)
,newAddNum INT
,stayList VARCHAR (128)
);

create table StayMon
(id int PRIMARY KEY auto_increment
,dateID DATE
,cID VARCHAR (64)
,gID VARCHAR (64)
,sID VARCHAR (64)
,newAddNum INT
,stayList VARCHAR (64)
);

-- channel gameid serverid 列表
create table channellist
(id int PRIMARY KEY auto_increment
,name VARCHAR (64)
);

create table gamelist
(id int PRIMARY KEY auto_increment
,name VARCHAR (64)
);

create table serverlist
(id int PRIMARY KEY auto_increment
,name VARCHAR (64)
);

--INSERT
insert into channellist (name) value('aaaaaa');
insert into channellist (name) value('bbbbbb');
insert into channellist (name) value('cccccc');



insert into gamelist (name) value('game1');
insert into gamelist (name) value('game2');
insert into gamelist (name) value('game3');


insert into serverlist (name) value('s1');
insert into serverlist (name) value('s2');
insert into serverlist (name) value('s3');

