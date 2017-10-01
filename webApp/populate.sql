INSERT INTO Classroom(name, city, address, capacity, type, PROJECTOR, WHITEBOARD, STUDENTCOMP, AUDIORECORDING, VIDEORECORDING,WHEELCHAIRACCESS,LISTENINGSYSTEM) VALUES
  ('Rodger Stevens LT 19', 'Leeds','LS2 9NH Roger Stevens Building',100,'Lecture theatre', FALSE ,TRUE ,FALSE ,TRUE ,FALSE ,TRUE ,FALSE ),
  ('Chemistry West LT A', 'Leeds','211 Clarendon Rd, LS2 9JT',110,'Lecture theatre',FALSE ,TRUE ,FALSE ,TRUE ,FALSE ,TRUE ,FALSE ),
  ('Edge Sports Hall 1', 'Leeds','Willow Terrace Rd, LS2 9JT',90,'Hall',FALSE ,TRUE ,FALSE ,TRUE ,FALSE ,TRUE ,FALSE ),
  ('Great Hall', 'Leeds','Great hall university of leeds',100,'Hall',FALSE ,FALSE ,FALSE ,FALSE ,FALSE ,TRUE ,FALSE ),
  ('Tower of london', 'London','St Katharines & Wapping EC3N 4AB',4,'Historical site',FALSE ,TRUE ,TRUE ,TRUE ,FALSE ,TRUE ,FALSE );

INSERT INTO Course(title, description, capacity, duration) VALUES
  ('Software Engineering','Understand the software life-cycle. Select a development process appropriate for a given task and context. Use appropriate tools to manage the development process. Work effectively as team to capture the requirements, produce a design and complete the implementation for a given task.',50,15),
  ('Communication Theory', 'This module builds on the first-year syllabus of ELEC1410 to give more quantitative/analytical tools for the design of modern communications systems, including Fourier analysis and signal processing, as well as the statistical treatment of signals ',45,21),
  ('Procedural Prog', 'Learn how to develop a solution to a problem and design and implement its solution as a procedural program.',45,17),
  ('Electronic Circuits', 'The module begins with a look at the concept of ‘signal conditioning’ and then considers how some common signal conditioning functions can be implemented using circuits based on operational-amplifiers.',40,10),
  ('Gates to PC', 'This module aims to give students a deep insight into how a modern computer system works, from the low-level logic gates to high-level programming languages. This is achieved through a series of laboratory tasks in which the students will develop the building blocks of a general purpose computer.',60,10),
  ('Algorithms', 'n completion of this module, students should understand and be proficient in analysis and design of key algorithms that are fundamental to computing (searching, sorting, number conversion, goal seeking, backtracking) and in application of basic data structures (arrays, lists, stacks, and priority queues).',55,11);

INSERT INTO Person(username, password, ADMIN) VALUES
                  ('admin', 'admin',TRUE),
                  ('sWinwood', 'admin',FALSE),
                  ('JimCapaldi', 'admin',FALSE),
                  ('ChrisWood', 'admin',FALSE),
                  ('SashaFJ', 'admin',FALSE),
                  ('MarkHollis', 'admin',FALSE),
                  ('PeterKemb', 'admin',FALSE),
                  ('JasonP', 'admin',FALSE),
                  ('ELFrazer', 'admin',FALSE),
                  ('RobGuth', 'admin',FALSE);

INSERT INTO Trainer(name, address, email, phone) VALUES
                  ('Andrew Lumsden','123 sandwich street','111@leeds.ac.uk','111'),
                  ('Edward Cottle','321 curry grove','222@leeds.ac.uk','222'),
                  ('Samrudh Sharma','45 Italian food lane','333@leeds.ac.uk','333'),
                  ('Michael Glad','34 Fish and Chips street','444@leeds.ac.uk','444'),
                  ('Callum Boustaed','67 KFC avenue','555@leeds.ac.uk','555');


INSERT INTO EXISTINGCOURSE(COURSEID ,CLASSROOM ,TRAINERID ,STARTTIME ,ENDTIME ) VALUES
((SELECT ID FROM COURSE WHERE TITLE ='Software Engineering'),(SELECT ID FROM CLASSROOM WHERE NAME ='Rodger Stevens LT 19'),(SELECT ID FROM TRAINER WHERE NAME ='Andrew Lumsden'),'2017-05-06 12:00:00','2017-05-17 09:00:00'),
((SELECT ID FROM COURSE WHERE TITLE ='Communication Theory'),(SELECT ID FROM CLASSROOM WHERE NAME ='Chemistry West LT A'), (SELECT ID FROM TRAINER WHERE NAME ='Edward Cottle'),'2017-05-05 12:00:00','2017-05-14 09:00:00'),
((SELECT ID FROM COURSE WHERE TITLE ='Procedural Prog'),     (SELECT ID FROM CLASSROOM WHERE NAME ='Edge Sports Hall 1'),  (SELECT ID FROM TRAINER WHERE NAME ='Samrudh Sharma'),'2017-05-07 12:00:00','2017-05-17 09:00:00'),
((SELECT ID FROM COURSE WHERE TITLE ='Electronic Circuits'), (SELECT ID FROM CLASSROOM WHERE NAME ='Great Hall'),          (SELECT ID FROM TRAINER WHERE NAME ='Michael Glad'),'2017-05-08 12:00:00','2017-05-18 09:00:00'),
((SELECT ID FROM COURSE WHERE TITLE ='Algorithms'),          (SELECT ID FROM CLASSROOM WHERE NAME ='Tower of london'),     (SELECT ID FROM TRAINER WHERE NAME ='Michael Glad'),'2017-05-08 12:00:00','2017-05-18 09:00:00');

INSERT INTO COMPLETED(username, course,existingcourse,finished) VALUES
  ((SELECT ID FROM PERSON WHERE USERNAME ='sWinwood')  ,(SELECT ID FROM COURSE WHERE TITLE ='Software Engineering'),(SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Software Engineering')),FALSE),
  ((SELECT ID FROM PERSON WHERE USERNAME ='sWinwood')  ,(SELECT ID FROM COURSE WHERE TITLE ='Communication Theory'),(SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Communication Theory')),TRUE),
  ((SELECT ID FROM PERSON WHERE USERNAME ='JimCapaldi') ,(SELECT ID FROM COURSE WHERE TITLE ='Communication Theory'),(SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Communication Theory')),FALSE),
  ((SELECT ID FROM PERSON WHERE USERNAME ='ChrisWood')  ,(SELECT ID FROM COURSE WHERE TITLE ='Procedural Prog'),     (SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Procedural Prog')),TRUE),
  ((SELECT ID FROM PERSON WHERE USERNAME ='ChrisWood')  ,(SELECT ID FROM COURSE WHERE TITLE ='Electronic Circuits'), (SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Electronic Circuits')),TRUE),
  ((SELECT ID FROM PERSON WHERE USERNAME ='ChrisWood')  ,(SELECT ID FROM COURSE WHERE TITLE ='Communication Theory'),(SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Communication Theory')),FALSE),
  ((SELECT ID FROM PERSON WHERE USERNAME ='SashaFJ')    ,(SELECT ID FROM COURSE WHERE TITLE ='Algorithms'),          (SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Algorithms')),FALSE),
  ((SELECT ID FROM PERSON WHERE USERNAME ='MarkHollis') ,(SELECT ID FROM COURSE WHERE TITLE ='Algorithms'),          (SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Algorithms')),FALSE),
  ((SELECT ID FROM PERSON WHERE USERNAME ='PeterKemb')  ,(SELECT ID FROM COURSE WHERE TITLE ='Algorithms'),          (SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Algorithms')),FALSE),
  ((SELECT ID FROM PERSON WHERE USERNAME ='JasonP')     ,(SELECT ID FROM COURSE WHERE TITLE ='Algorithms'),          (SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Algorithms')),FALSE);


INSERT INTO PREREQUISITE(COURSEID, PREREQUISITEID) VALUES
((SELECT ID FROM COURSE WHERE TITLE ='Software Engineering'),(SELECT ID FROM COURSE WHERE TITLE ='Communication Theory'));

INSERT INTO WAITINGLIST (USERID, EXISTINGCOURSEID, TIMEADDED)  VALUES
((SELECT ID FROM PERSON WHERE USERNAME ='ELFrazer'),(SELECT ID FROM EXISTINGCOURSE WHERE COURSEID =(SELECT ID FROM COURSE WHERE TITLE ='Algorithms')),'2017-05-02 12:00:00');
