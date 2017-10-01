CREATE TABLE Person(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
  username VARCHAR(20) NOT NULL,
  password VARCHAR(20) NOT NULL,
  admin BOOLEAN,
  PRIMARY KEY (id)
);

CREATE TABLE Classroom (
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(20) NOT NULL,
  city VARCHAR(20),
  address VARCHAR(40),
  capacity INTEGER NOT NULL,
  type VARCHAR(20),
  projector BOOLEAN,
  whiteboard BOOLEAN,
  studentcomp BOOLEAN,
  audiorecording BOOLEAN,
  videorecording BOOLEAN,
  wheelchairaccess BOOLEAN,
  listeningsystem BOOLEAN,
  PRIMARY KEY (id)
);

CREATE TABLE Course (
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
  title VARCHAR(20) NOT NULL,
  description LONG VARCHAR,
  capacity INTEGER,
  duration INTEGER,
  PRIMARY KEY (id)
);

CREATE TABLE Trainer(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(30),
  address VARCHAR(50),
  email VARCHAR(40),
  phone VARCHAR(15),
  PRIMARY KEY (id)
);

CREATE TABLE ExistingCourse(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
  courseID INTEGER,
  classroom INTEGER,
  startTIME TIMESTAMP,
  endTIME TIMESTAMP,
  trainerID INTEGER,
  PRIMARY KEY (id),
  FOREIGN KEY (classroom) REFERENCES Classroom(id),
  FOREIGN KEY (courseID) REFERENCES Course(id),
  FOREIGN KEY (trainerID) REFERENCES Trainer(id)
);

CREATE TABLE Completed(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
  username INTEGER,
  course INTEGER,
  existingcourse INTEGER,
  finished BOOLEAN,
  PRIMARY KEY (id),
  FOREIGN KEY (username) REFERENCES Person(id),
  FOREIGN KEY (existingcourse) REFERENCES ExistingCourse(id),
  FOREIGN KEY (course) REFERENCES Course(id)
);

CREATE TABLE Prerequisite(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
  courseID INTEGER,
  prerequisiteID INTEGER,
  PRIMARY KEY (id),
  FOREIGN KEY (courseID) REFERENCES Course(id),
  FOREIGN KEY (prerequisiteID) REFERENCES Course(id)
);

CREATE TABLE WaitingList(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
  userID INTEGER,
  existingCourseID INTEGER,
  timeADDED TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (userID) REFERENCES Person(id),
  FOREIGN KEY (existingCourseID) REFERENCES ExistingCourse(id)
);
