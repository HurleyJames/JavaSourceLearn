-- 创建数据表

-- 学生表Student
create table student(sid varchar(6), sname varchar(10), sage datetime, ssex varchar(10));
insert into Student values('01' , '赵雷' , '1990-01-01' , '男');
insert into Student values('02' , '钱电' , '1990-12-21' , '男');
insert into Student values('03' , '孙风' , '1990-05-20' , '男');
insert into Student values('04' , '李云' , '1990-08-06' , '男');
insert into Student values('05' , '周梅' , '1991-12-01' , '女');
insert into Student values('06' , '吴兰' , '1992-03-01' , '女');
insert into Student values('07' , '郑竹' , '1989-07-01' , '女');
insert into Student values('08' , '王菊' , '1990-01-20' , '女');

--- 成绩表SC
create table sc(sid varchar(10), cid varchar(10), score decimal(18, 1));
insert into SC values('01' , '01' , 80);
insert into SC values('01' , '02' , 90);
insert into SC values('01' , '03' , 99);
insert into SC values('02' , '01' , 70);
insert into SC values('02' , '02' , 60);
insert into SC values('02' , '03' , 80);
insert into SC values('03' , '01' , 80);
insert into SC values('03' , '02' , 80);
insert into SC values('03' , '03' , 80);
insert into SC values('04' , '01' , 50);
insert into SC values('04' , '02' , 30);
insert into SC values('04' , '03' , 20);
insert into SC values('05' , '01' , 76);
insert into SC values('05' , '02' , 87);
insert into SC values('06' , '01' , 31);
insert into SC values('06' , '03' , 34);
insert into SC values('07' , '02' , 89);
insert into SC values('07' , '03' , 98);

--- 课程表Course
create table course(cid varchar(10), cname varchar(10), tid varchar(10));
insert into Course values('01' , '语文' , '02');
insert into Course values('02' , '数学' , '01');
insert into Course values('03' , '英语' , '03');

--- 教师表Teacher
create table teacher(tid varchar(10), tname varchar(10));
insert into Teacher values('01' , '张三');
insert into Teacher values('02' , '李四');
insert into Teacher values('03' , '王五');

-------------------------------------------------------------

/*
    1. 查询出每门课都大于80分的学生姓 --> 最低分数的课的分数都大于80分
 */
select name from table group by having min(grade) > 80;

/*
    2. 删除除了自动编号不同，其余字段的结果都相同的学生冗余信息
 */
delete table_name where 自动编号 not in (
select min(自动编号) from table_name group by 学号, 姓名, 课程编号, 课程名称, 分数
)

/*
    3. 查询01课程比02课程的成绩高的学生的信息以及课程分数
 */
select s.*, a.score as score_01, b.score as score_02 from studnet_s,
    (select sid, score from sc where cid=01) a,
    (select sid, score from sc where cid=02) b
where a.sid = b.sid and a.score > b.score and s.sid = a.sid;

/*
    4. 查询平均成绩大于等于60分的学生编号、姓名、平均成绩
 */
select s.sid, sname, avg(score) as avg_score from student as s, sc
where s.sid = sc.sid group by s.sid having avg_score > 60;

/*
    5. 查询SC表中存在成绩的学生信息
 */
select * from student where sid in (select sid from sc where score is not null);


/*
    6. 查询所有同学的学生编号、学生姓名、选课总数、所有课程的总成绩（没成绩的就显示为null）
    使用left join左连接后，左表student的记录会全部显示出来，右表sc只会显示符合条件的记录，不符合的显示为null
 */
select s.sid, s.name, count(cid) as 选课总数, sum(score) as 总成绩
from student as s left join sc on s.sid=sc.sid group by s.sid;

/*
    7. 查有成绩的学生信息
 */
select s.sid, s.sname, count(*) as 选课总数, sum(score) as 总成绩,
    sum(case when cid=01 then score else null end) as score_01,
    sum(case when cid=02 then score else null end) as score_02,
    sum(case when cid=03 then score else null end) as score_03,
from student as s, sc where s.sid=sc.sid group by s.sid;

/*
    8. 查询「李」姓老师的数量
 */
select count(tname) from teacher where tname like '李%';


/*
    9. 查询两门及以上不及格课程的同学的学号，姓名以及平均成绩
 */
select s.sid, s.sname, avg(score) from student as s, sc where s.sid=sc.sid and score < 60
group by s.sid having count(score)>= 2;

/*
    10. 检索“01”课程的分数小于60，按分数降序排列的学生信息
 */
select s.*, score from student as s, sc where cid=01 and score < 60 and s.sid=sc.sid order by score desc;

/*
    11. 查询各科成绩最高分、最低分和平均分。以如下形式显示：
    课程ID    课程name  最高分 最低分 平均分 及格率 中等率 优良率 优秀率
    （及格：>=60    中等：70-80    优良：80-90    优秀：>=90）
    要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列
 */
select c.cid as 课程号, c.cname as 课程名称, count(*) as 选修人数,
    max(score) as 最高分, min(score) as 最低分, avg(score) as 平均分,
    sum(case when score>=60 then 1 else 0 end) / count(*) as 及格率,
    sum(case when score>=70 and score<80 then 1 else 0 end) / count(*) as 中等率,
    sum(case when score>=80 and score<90 then 1 else 0 end) / count(*) as 优良率,
    sum(case when score>=90 then 1 else 0 end) / count(*) as 优秀率,
from sc, course c
where c.cid=sc.cid
group by c.cid
order by count(*) desc, c.cid asc;

/*
    12. 查询各科成绩前三名的记录
 */
select * from (select *, rank() over(partition by cid order by score desc) as graderank from sc) A
where A.graderank<=3;

/*
    13. 查询出只选修两门课程的学生学号和姓名
 */
select s.sid, s.sname, count(cid) from student s, sc where s.sid=sc.sid group by s.sid having count(cid)=2;Ω

/*
    14. 查询1990年出生的学生名单
 */
select * from student where year(sage)=1990;

/*
    15. 查询各学生的年龄，只按年份来算
 */
select sname, year(now() - year(sage)) as age from student;

-------------------------------------------------------------

/*
    练习题
 */

/*
    1. 查询成绩表中成绩在60到80之间的所有记录
 */
select * from sc where score > 60 and score < 80;
--- 或者
select * from sc where score between 60 and 80;

/*
    2. 查询成绩表中成绩为85，86或者88的记录
 */
select * from sc where score = 85 or score = 86 or score = 88;
--- 或者
select * from sc where score in (85, 86, 88);

/*
    3. 查询成绩表中的最高分的学生学号和课程号
 */
select sid, cid, from sc order by score desc limit 1 offset 0;  --- 也可以简写为 limit 1;
--- 或者
select sid, cid from sc where score = (select max(score) from score);

/*
    4. 查询选修某课程的同学人数多于5人的教室姓名
 */
select tname from teacher where tid in
    (select tid from course where cid in (select cid from sc group by cid having count(sid) > 5));