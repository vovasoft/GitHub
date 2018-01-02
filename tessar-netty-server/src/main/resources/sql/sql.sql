
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