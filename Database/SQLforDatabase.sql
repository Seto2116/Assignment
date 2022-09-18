if not exists(select * from sys.databases where name = 'Assignment')
	create database Assignment
GO
use Assignment

if not exists(select * from sys.tables where name = 'School' and type = 'u')
	begin
		create table School(
			SchoolID varchar(5) primary key,
			SchoolName varchar(30),
			Headmaster varchar(30),
			SchoolAddress varchar(30),
			check (SchoolID like 'SC___')
		)
		insert into School
		values ('SC001', 'Nguyen Trai Primary School', 'Nguyen Bich Thuy', '162 Khuong Trung'),
		('SC002', 'Nguyen Trai Secondary School', 'Nguyen Thi Bich Nga', '126 Khuong Trung'),
		('SC003', 'Nguyen Trai High School', 'Nguyen The Hung', '50 Nam Cao');
	end

if not exists(select * from sys.tables where name = 'Class' and type = 'u')
	begin
		create table Class(
			ClassID varchar(4) primary key,
			HomeroomTeacher varchar(30),
			SchoolID varchar(5) foreign key references School(SchoolID) on delete set null,
			check (ClassID like '%A_')
		)
		insert into Class
		values ('5A7', 'Nguyen Ngoc Han', 'SC001'),
		('4A3', 'Tran Anh Minh', 'SC001'),
		('8A1', 'Duong Thi Thu Hue', 'SC002'),
		('9A3', 'Truong Cong Toan', 'SC002'),
		('11A4', 'Khuat Duc Hung', 'SC003'),
		('12A1', 'Le Hai Ngan', 'SC003'),
		('12A5', 'Phan Thanh Quang', 'SC003');
	end

if not exists(select * from sys.tables where name = 'Student' and type = 'u')
	begin
		create table Student(
			StudentID varchar(4) primary key,
			StudentName varchar(30),
			Gender char(7),
			ClassID varchar(4) foreign key references Class(ClassID) on delete set null,
			StudentAddress varchar(30),
			check (StudentID like 'S___')
		);
		insert into Student
		values ('S001', 'Tran Anh Trung', 'Male', '4A3', '37 Nguyen Chi Thanh'),
		('S002', 'Nguyen Phuong Linh', 'Female', '4A3', '92 Tran Huy Lieu'),
		('S003', 'Tran Thuc Anh', 'Female', '5A7', '70 Dao Tan'),
		('S004', 'Tran Tien Hung', 'Male', '8A1', '25 Giai Phong'),
		('S005', 'Dinh Bui Xuan Hieu', 'Male', '9A3', '79 Khuong Trung'),
		('S006', 'Ngo Minh Nhat', 'Male', '9A3', '32 Truong Chinh'),
		('S007', 'Le Ngoc Mai', 'Female', '11A4', '89 Ngo Thi Nham'),
		('S008', 'Nguyen Thu Thao', 'Female', '11A4', '4 Tran Doan'),
		('S009', 'Vu QUang Phuc', 'Male', '12A1', '76 Nguyen Chi Thanh'),
		('S010', 'Nguyen Anh Duy', 'Male', '4A3', 'B1 Tran Huy Lieu');
	end
