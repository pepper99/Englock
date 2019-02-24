BEGIN TRANSACTION;
DROP TABLE IF EXISTS `wom`;
CREATE TABLE IF NOT EXISTS `wom` (
	`pop`	TEXT,
	`en`	TEXT,
	`th`	TEXT
);
INSERT INTO `wom` VALUES ('n.','Rage','ความเดือดดาล');
INSERT INTO `wom` VALUES ('n.','Admiration','ความชื่นชม');
INSERT INTO `wom` VALUES ('n.','Contempt','การดูถูก');
INSERT INTO `wom` VALUES ('n.','Ecstasy','ความปิติยินดี');
INSERT INTO `wom` VALUES ('n.','Disgust','ความขยะแขยง');
INSERT INTO `wom` VALUES ('n.','Pensiveness','ความเป็นทุกข์');
INSERT INTO `wom` VALUES ('n.','Grief','ความเศร้าโศก');
INSERT INTO `wom` VALUES ('n.','Distraction','ความวอกแวก');
INSERT INTO `wom` VALUES ('n.','Terror','ความสยองขวัญ');
INSERT INTO `wom` VALUES ('n.','Remorse','การสำนึกผิด');
INSERT INTO `wom` VALUES ('n.','Serenity','ความปลอดโปร่ง,ความแจ่มใส');
INSERT INTO `wom` VALUES ('n.','Vigilance','ความระมัดระวัง');
INSERT INTO `wom` VALUES ('n.','Awe','ความน่าเกรงขาม');
INSERT INTO `wom` VALUES ('n.','Optimism','การมองในแง่ดี');
INSERT INTO `wom` VALUES ('n.','Submission','ความอ่อนน้อม,การยอมจำนน');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
INSERT INTO `wom` VALUES ('','','');
DROP TABLE IF EXISTS `school`;
CREATE TABLE IF NOT EXISTS `school` (
	`pop`	TEXT,
	`en`	TEXT,
	`th`	TEXT
);
INSERT INTO `school` VALUES ('n.','Volunteer','อาสาสมัคร');
INSERT INTO `school` VALUES ('n.','Stationery','เครื่องเขียน');
INSERT INTO `school` VALUES ('n.','Discipline','ระเบียบ');
INSERT INTO `school` VALUES ('n.','Assembly','การรวมกลุ่ม');
INSERT INTO `school` VALUES ('n.','Recitations','อาขยาน');
INSERT INTO `school` VALUES ('n.','Orientation','ปฐมนิเทศ');
INSERT INTO `school` VALUES ('n.','Treasurer','เหรัญญิก');
INSERT INTO `school` VALUES ('n.','Athlete','นักกีฬา');
INSERT INTO `school` VALUES ('n.','Student Council','สภานักเรียน');
INSERT INTO `school` VALUES ('n.','Adviser','ครูที่ปรึกษา');
INSERT INTO `school` VALUES ('n.','Harmony','ความสามัคคี');
INSERT INTO `school` VALUES ('n.','Alumni','ศิษย์เก่า');
INSERT INTO `school` VALUES ('n.','Senior','รุ่นพี่');
INSERT INTO `school` VALUES ('n.','Assignment','งานที่ได้รับมอบหมาย');
INSERT INTO `school` VALUES ('v.','Revise','ทบทวน');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES ('','','');
INSERT INTO `school` VALUES (NULL,NULL,NULL);
DROP TABLE IF EXISTS `clothes`;
CREATE TABLE IF NOT EXISTS `clothes` (
	`pop`	TEXT,
	`en`	TEXT,
	`th`	TEXT
);
INSERT INTO `clothes` VALUES ('n.','Suit','ชุดเสื้อผ้า');
INSERT INTO `clothes` VALUES ('n.','Livery','เครื่องแบบคนรับใช้');
INSERT INTO `clothes` VALUES ('n.','Hosiery','ถุงเท้า');
INSERT INTO `clothes` VALUES ('n.','Headgear','หมวก');
INSERT INTO `clothes` VALUES ('n.','Casual','ชุดลำลอง');
INSERT INTO `clothes` VALUES ('n.','Accessory','เครื่องประดับ');
INSERT INTO `clothes` VALUES ('n.','Delicates','เสื้อผ้าซักมือ');
INSERT INTO `clothes` VALUES ('n.','Finery','เสื้อผ้าอาภรณ์ที่หรูหรา');
INSERT INTO `clothes` VALUES ('n.','Knitwear','เสื้อผ้าที่ทำขึ้นด้วยการถัก');
INSERT INTO `clothes` VALUES ('n.','Leathers','หนังสัตว์');
INSERT INTO `clothes` VALUES ('n.','Disguise','สิ่งที่ใช้ปลอมตัว');
INSERT INTO `clothes` VALUES ('n.','Milllinery','หมวกสตรี');
INSERT INTO `clothes` VALUES ('n.','Synthetics','สารสังเคราะห์');
INSERT INTO `clothes` VALUES ('n.','Thermals','เสื้อผ้าสำหรับให้ความอบอุ่น');
INSERT INTO `clothes` VALUES ('n.','Mourning','ชุดไว้ทุกข์');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES ('','','');
INSERT INTO `clothes` VALUES (NULL,NULL,NULL);
INSERT INTO `clothes` VALUES (NULL,NULL,NULL);
COMMIT;
