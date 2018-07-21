BEGIN TRANSACTION;
DROP TABLE IF EXISTS `vocab_hard`;
DROP TABLE IF EXISTS `wom`;
CREATE TABLE IF NOT EXISTS `wom` (
	`pop`	TEXT,
	`en`	TEXT,
	`th`	TEXT
);
INSERT INTO `wom` VALUES ('n.','Rage','ความเดือดดาล');
INSERT INTO `wom` VALUES ('n.','Anger','ความโกรธ');
INSERT INTO `wom` VALUES ('n.','Annoyance','ความรำคาญ');
INSERT INTO `wom` VALUES ('n.','Loathing','ความรังเกียจ');
INSERT INTO `wom` VALUES ('n.','Disgust','ความขยะแขยง');
INSERT INTO `wom` VALUES ('n.','Boredom','ความเหนื่อยหน่าย');
INSERT INTO `wom` VALUES ('n.','Grief','ความเศร้าโศก');
INSERT INTO `wom` VALUES ('n.','Sadness','ความเสียใจ');
INSERT INTO `wom` VALUES ('n.','Pensiveness','ความเป็นทุกข์');
INSERT INTO `wom` VALUES ('n.','Amazement','ความประหลาดใจ');
INSERT INTO `wom` VALUES ('n.','Surprise','ความแปลกใจ');
INSERT INTO `wom` VALUES ('n.','Distraction','ความวอกแวก');
INSERT INTO `wom` VALUES ('n.','Terror','ความสยองขวัญ');
INSERT INTO `wom` VALUES ('n.','Fear','ความกลัว');
INSERT INTO `wom` VALUES ('n.','Apprehension','ความหวาดวิตก');
INSERT INTO `wom` VALUES ('n.','Admiration','ความชื่นชม');
INSERT INTO `wom` VALUES ('n.','Trust','ความเชื่อใจ');
INSERT INTO `wom` VALUES ('n.','Acceptance','การยอมรับ');
INSERT INTO `wom` VALUES ('n.','Ecstasy','ความปิติยินดี');
INSERT INTO `wom` VALUES ('n.','Joy','ความสุขสบายใจ');
INSERT INTO `wom` VALUES ('n.','Serenity','ความปลอดโปร่ง,ความแจ่มใส');
INSERT INTO `wom` VALUES ('n.','Vigilance','ความระมัดระวัง');
INSERT INTO `wom` VALUES ('n.','Interest','ความสนใจ');
INSERT INTO `wom` VALUES ('n.','Anticipation','ความมุ่งหวัง');
INSERT INTO `wom` VALUES ('n.','Awe','ความน่าเกรงขาม');
INSERT INTO `wom` VALUES ('n.','Optimism','การมองในแง่ดี');
INSERT INTO `wom` VALUES ('n.','Submission','ความอ่อนน้อม,การยอมจำนน');
INSERT INTO `wom` VALUES ('n.','Remorse','การสำนึกผิด');
INSERT INTO `wom` VALUES ('n.','Contempt','การดูถูก');
INSERT INTO `wom` VALUES ('n.','Aggressiveness','ความก้าวร้าว');
DROP TABLE IF EXISTS `school`;
CREATE TABLE IF NOT EXISTS `school` (
	`pop`	TEXT,
	`en`	TEXT,
	`th`	TEXT
);
INSERT INTO `school` VALUES ('n.','Conflict','ความขัดแย้ง');
INSERT INTO `school` VALUES ('v.','Retest','สอบซ่อม');
INSERT INTO `school` VALUES ('n.','Discipline','ระเบียบ');
INSERT INTO `school` VALUES ('n.','Assembly','การรวมกลุ่ม');
INSERT INTO `school` VALUES ('n.','Recitations','อาขยาน');
INSERT INTO `school` VALUES ('v.','Line-up','เข้าแถว');
INSERT INTO `school` VALUES ('n.','Volunteer','อาสาสมัคร');
INSERT INTO `school` VALUES ('n.','Marching Band','วงโยธวาทิต');
INSERT INTO `school` VALUES ('n.','Stationery','เครื่องเขียน');
INSERT INTO `school` VALUES ('n.','Festival','งานเทศกาล');
INSERT INTO `school` VALUES ('n.','Open House','วันเยี่ยมชมสถาบัน');
INSERT INTO `school` VALUES ('n.','Orientation','ปฐมนิเทศ');
INSERT INTO `school` VALUES ('n.','Post Training','ปัจฉิมนิเทศ');
INSERT INTO `school` VALUES ('n.','Sport Day','กีฬาสี');
INSERT INTO `school` VALUES ('n.','Project','โครงงาน');
INSERT INTO `school` VALUES ('v.','Present','นำเสนอ');
INSERT INTO `school` VALUES ('n.','Best Friend','เพื่อนสนิท');
INSERT INTO `school` VALUES ('n.','Fashion Shows','การแสดงแบบเครื่องแต่งกายสมัยนิยม');
INSERT INTO `school` VALUES ('n.','Class President','หัวหน้าห้อง');
INSERT INTO `school` VALUES ('n.','Treasurer','เหรัญญิก');
INSERT INTO `school` VALUES ('n.','Athlete','นักกีฬา');
INSERT INTO `school` VALUES ('n.','Student Council','สภานักเรียน');
INSERT INTO `school` VALUES ('n.','Adviser','ครูที่ปรึกษา');
INSERT INTO `school` VALUES ('n.','Harmony','ความสามัคคี');
INSERT INTO `school` VALUES ('n.','Alumni','ศิษย์เก่า');
INSERT INTO `school` VALUES ('n.','Senior','รุ่นพี่');
INSERT INTO `school` VALUES ('n.','Junior','รุ่นน้อง');
INSERT INTO `school` VALUES ('n.','Assignment','งานที่ได้รับมอบหมาย');
INSERT INTO `school` VALUES ('v.','Revise','ทบทวน');
INSERT INTO `school` VALUES ('v.','Salute','ทำความเคารพ');
DROP TABLE IF EXISTS `landforms`;
CREATE TABLE IF NOT EXISTS `landforms` (
	`pop`	TEXT,
	`en`	TEXT,
	`th`	TEXT
);
INSERT INTO `landforms` VALUES ('n.','Archipelago','หมู่เกาะ');
INSERT INTO `landforms` VALUES ('n.','Bench','แนวหินที่นูนขึ้น');
INSERT INTO `landforms` VALUES ('n.','Cove','ส่วนเว้าตามแนวเขาหรือหน้าผา');
INSERT INTO `landforms` VALUES ('n.','Dune','ภูเขาทราย');
INSERT INTO `landforms` VALUES ('n.','Cape','แหลม');
INSERT INTO `landforms` VALUES ('n.','Geyser','น้ำพุร้อน');
INSERT INTO `landforms` VALUES ('n.','Glacier','ธารน้ำแข็ง');
INSERT INTO `landforms` VALUES ('n.','Karst','หินปูน');
INSERT INTO `landforms` VALUES ('n.','Gulf','อ่าว');
INSERT INTO `landforms` VALUES ('n.','Hogback','เขาหนอกวัว');
INSERT INTO `landforms` VALUES ('n.','Horst','ส่วนของผิวโลกที่เคลื่อนขึ้นมา');
INSERT INTO `landforms` VALUES ('n.','Isthmus','คอคอด');
INSERT INTO `landforms` VALUES ('n.','Inlet','ปากน้ำ');
INSERT INTO `landforms` VALUES ('n.','Knoll','โคก');
INSERT INTO `landforms` VALUES ('n.','Lagoon','ทะเลสาบน้ำเค็ม');
INSERT INTO `landforms` VALUES ('n.','Marsh','บึง');
INSERT INTO `landforms` VALUES ('n.','Plateau','ที่ราบสูง');
INSERT INTO `landforms` VALUES ('n.','Plain','ที่ราบ');
INSERT INTO `landforms` VALUES ('n.','Estuary','ชะวากทะเล');
INSERT INTO `landforms` VALUES ('n.','Peninsula','คาบสมุทร');
INSERT INTO `landforms` VALUES ('n.','Ravine','เหว');
INSERT INTO `landforms` VALUES ('n.','Ridge','สันเขา');
INSERT INTO `landforms` VALUES ('n.','Swamp','หนองน้ำ');
INSERT INTO `landforms` VALUES ('n.','Terrace','เนินที่ลาดเอียง');
INSERT INTO `landforms` VALUES ('n.','Wetlands','พื้นที่ชุ่มน้ำ');
INSERT INTO `landforms` VALUES ('n.','Valley','หุบเขา');
INSERT INTO `landforms` VALUES ('n.','Cliff','หน้าผา');
INSERT INTO `landforms` VALUES ('n.','Arch','การก่อตัวของหินเป็นรูปโค้ง');
INSERT INTO `landforms` VALUES ('n.','Islet','เกาะเล็ก');
INSERT INTO `landforms` VALUES ('n.','Oasis','บริเวณอุดมสมบูรณ์ในทะเลทราย');
DROP TABLE IF EXISTS `etiquette`;
CREATE TABLE IF NOT EXISTS `etiquette` (
	`pop`	TEXT,
	`en`	TEXT,
	`th`	TEXT
);
INSERT INTO `etiquette` VALUES ('n.','Norms','บรรทัดฐาน');
INSERT INTO `etiquette` VALUES ('n.','Manners','มารยาทสังคม');
INSERT INTO `etiquette` VALUES ('adj.','Genuine','แท้');
INSERT INTO `etiquette` VALUES ('n.','Interaction','การมีปฏิสัมพันธ์');
INSERT INTO `etiquette` VALUES ('n.','Decorum','ความมีกิริยาดี');
INSERT INTO `etiquette` VALUES ('n.','Culture','วัฒนธรรม');
INSERT INTO `etiquette` VALUES ('n.','Philosophy','หลักปรัชญา');
INSERT INTO `etiquette` VALUES ('n.','Nice','น่าคบหา');
INSERT INTO `etiquette` VALUES ('adj.','Polite','สุภาพ');
INSERT INTO `etiquette` VALUES ('adj.','Proper','เหมาะสม');
INSERT INTO `etiquette` VALUES ('n.','Effort','ความพยายาม');
INSERT INTO `etiquette` VALUES ('adj.','Class','ที่ดีเยี่ยม');
INSERT INTO `etiquette` VALUES ('adj.','Personal','ด้วยตนเอง');
INSERT INTO `etiquette` VALUES ('n.','Protocol','ระเบียบการ');
INSERT INTO `etiquette` VALUES ('n.','Behavior','พฤติการณ์');
INSERT INTO `etiquette` VALUES ('adj.','Social','เกี่ยวกับสังคม');
INSERT INTO `etiquette` VALUES ('adj.','Unwritten','เป็นวาจา,เป็นประเพณี');
INSERT INTO `etiquette` VALUES ('n.','Form','รูปแบบ');
INSERT INTO `etiquette` VALUES ('adj.','Friendly','เป็นมิตร');
INSERT INTO `etiquette` VALUES ('adj.','Standards','ซึ่งเป็นมาตรฐาน');
INSERT INTO `etiquette` VALUES ('n.','Code','มาตรการ');
INSERT INTO `etiquette` VALUES ('n.','Business','ธุรกิจ');
INSERT INTO `etiquette` VALUES ('n.','Finishing School','โรงเรียนสอนการเรือนให้กับสตรี');
INSERT INTO `etiquette` VALUES ('v.','Conduct','นำให้ดู');
INSERT INTO `etiquette` VALUES ('n.','Expectations','ความคาดหวัง');
INSERT INTO `etiquette` VALUES ('n.','Touch','ความรู้สึก');
INSERT INTO `etiquette` VALUES ('adj.','Decent','สมควร, น่านับถือ');
INSERT INTO `etiquette` VALUES ('n.','Civility','ความสุภาพ,ความเอื้อเฟื้อ');
INSERT INTO `etiquette` VALUES ('adj.','Good','ดี');
INSERT INTO `etiquette` VALUES ('n.','Seemliness','ความสมควร,การบังควร');
DROP TABLE IF EXISTS `clothes`;
CREATE TABLE IF NOT EXISTS `clothes` (
	`pop`	TEXT,
	`en`	TEXT,
	`th`	TEXT
);
INSERT INTO `clothes` VALUES ('n.','Woolens','ผ้าขนสัตว์');
INSERT INTO `clothes` VALUES ('n.','Uniforms','ชุดเครื่องแบบ');
INSERT INTO `clothes` VALUES ('n.','Underclothes','ชุดชั้นใน');
INSERT INTO `clothes` VALUES ('n.','Suit','ชุดเสื้อผ้า');
INSERT INTO `clothes` VALUES ('n.','Swimwear','ชุดว่ายน้ำ');
INSERT INTO `clothes` VALUES ('n.','Sportswear','ชุดกีฬา');
INSERT INTO `clothes` VALUES ('n.','Regalia','ราชกกุธภัณฑ์');
INSERT INTO `clothes` VALUES ('n.','Livery','เครื่องแบบคนรับใช้');
INSERT INTO `clothes` VALUES ('n.','Hosiery','ถุงเท้า');
INSERT INTO `clothes` VALUES ('n.','Headgear','หมวก');
INSERT INTO `clothes` VALUES ('n.','Costume','เครื่องแต่งกาย');
INSERT INTO `clothes` VALUES ('n.','Casual','ชุดลำลอง');
INSERT INTO `clothes` VALUES ('n.','Accessory','เครื่องประดับ');
INSERT INTO `clothes` VALUES ('n.','Finery','เสื้อผ้าอาภรณ์ที่หรูหรา');
INSERT INTO `clothes` VALUES ('n.','Garb','เครื่องแบบ');
INSERT INTO `clothes` VALUES ('n.','Knitwear','เสื้อผ้าที่ทำขึ้นด้วยการถัก');
INSERT INTO `clothes` VALUES ('n.','Leathers','หนังสัตว์');
INSERT INTO `clothes` VALUES ('n.','Disguise','สิ่งที่ใช้ปลอมตัว');
INSERT INTO `clothes` VALUES ('n.','Milllinery','หมวกสตรี');
INSERT INTO `clothes` VALUES ('n.','Top','เสื้อ (โดยเฉพาะของสตรี)');
INSERT INTO `clothes` VALUES ('n.','Footwear','สิ่งที่ใช้สวมเท้า');
INSERT INTO `clothes` VALUES ('n.','Outerwear','เสื้อผ้าชั้นนอก');
INSERT INTO `clothes` VALUES ('n.','Synthetics','สารสังเคราะห์');
INSERT INTO `clothes` VALUES ('n.','Thermals','เสื้อผ้าสำหรับให้ความอบอุ่น');
INSERT INTO `clothes` VALUES ('n.','Nightclothes','เสื้อผ้าสวมเข้านอน');
INSERT INTO `clothes` VALUES ('n.','Mourning','ชุดไว้ทุกข์');
INSERT INTO `clothes` VALUES ('n.','Delicates','เสื้อผ้าซักมือ');
INSERT INTO `clothes` VALUES ('n.','Fancy Dress','ชุดแฟนซี');
INSERT INTO `clothes` VALUES ('n.','Label','เสื้อผ้าแบรนด์เนม');
INSERT INTO `clothes` VALUES ('n.','Ensemble','ชุด');
COMMIT;
