DROP DATABASE IF EXISTS Data;
CREATE DATABASE Data;

USE Data;


CREATE TABLE Account (
  uscID INT(11) PRIMARY KEY UNIQUE NOT NULL, # reference accounts on the site
  firstName VARCHAR(30) NOT NULL, # user first name
  lastName VARCHAR(50) NOT NULL, # user last name
  email VARCHAR(50) UNIQUE NOT NULL, # user email address
  password VARCHAR(50) NOT NULL, # user account password
  githubAccount VARCHAR(50) UNIQUE, # link to user github account page
  accountLevel INT(5) NOT NULL
  # accountLevel: student (1), TA/CP (2), professor (3), anything else (assume student account)
);

CREATE TABLE Links (
  linkID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference single-link entries in other tables
  displayedText VARCHAR(30) NOT NULL, # text displayed in the browser for the link
  href VARCHAR(150) NOT NULL, # destination of the link (starting with "/" specify relative paths)
  assignmentID INT(11), # generate lists of links in the assignment table for additional files
  lectureID INT(11), # generate lists of links in the lecture table for additional files
  labID INT(11), # generate lists of links in the lab table for additional files
  examID INT(11) # generate lists of links in the exam table for additional files
#   FOREIGN KEY fk7(lectureID) REFERENCES Lectures(lectureID),
#   FOREIGN KEY fk8(assignmentID) REFERENCES Assignments(assignmentID),
#   FOREIGN KEY fk9(labID) REFERENCES Labs(labID),
#   FOREIGN KEY fk10(examID) REFERENCES PreviousExams(examID)
);

CREATE TABLE Assignments (
  assignmentID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference assignment entries
  number INT(6) UNIQUE NOT NULL, # the assignment number
  title VARCHAR(50) NOT NULL, # the assignment title
  assignedDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) the given date of assignment
  dueDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) the due date of assignment
  pdfLink INT(11), # assignment pdf link
  additionalFiles BOOLEAN NOT NULL DEFAULT FALSE, # link to any additional files (can be null)
  solutionLink INT(11), # link to the solution (only be available after the professor enables it)
  FOREIGN KEY fk1(pdfLink) REFERENCES Links(linkID),
  FOREIGN KEY fk2(solutionLink) REFERENCES Links(linkID)
);

CREATE TABLE Lectures (
  lectureID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference lecture table entries
  number INT(6) UNIQUE NOT NULL, # lecture number
  lectureDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) date of the lecture
  lectureTopics VARCHAR(150) NOT NULL, # list of topics covered in the lecture
  chapters VARCHAR(15) NOT NULL, # chapters covered in the lecture
  lectureSlides BOOLEAN NOT NULL, # whether or not there are slides associated with the lectures
  programsLinks INT(11), # links to demos/programs/github repos
  FOREIGN KEY fk3(programsLinks) REFERENCES Links(linkID)
);

CREATE TABLE Labs (
  labID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference lab table entries
  number INT(6) UNIQUE NOT NULL, # display lab numbers in order
  labDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) date of the lab assigned
  labTopics VARCHAR(150) NOT NULL, # topics covered in the lab
  pdfLink INT(11) NOT NULL, # link to the lab pdf
  additionalFiles BOOLEAN NOT NULL DEFAULT FALSE, # whether or not there are additional files associated with the lab
  solutionLink INT(11) NOT NULL, # link to lab solution zip file / github repo
  FOREIGN KEY fk4(pdfLink) REFERENCES Links(linkID),
  FOREIGN KEY fk5(solutionLink) REFERENCES Links(linkID)
);

CREATE TABLE PreviousExams (
  examID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference exam table entries
  examDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) date of the exam
  type VARCHAR(20) NOT NULL, # (written/programming/other) type of the exam
  pdfLink INT(11) NOT NULL, # link to the exam pdf file
  solutions BOOLEAN NOT NULL, # whether or not there are solution links
  FOREIGN KEY fk6(pdfLink) REFERENCES Links(linkID)
);

CREATE TABLE Calendar (
  eventID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference event in the calendar
  title VARCHAR(30) NOT NULL, # title of the event
  date VARCHAR(12) NOT NULL, # date of the event
  startTime VARCHAR(8) NOT NULL, # (HH:MM) start time of the event
  endTime VARCHAR(8) NOT NULL, # (HH:MM) end time of the event
  participant INT(11), # uscID associated with the other participating user
  isConfirmed BOOLEAN NOT NULL, # whether or not other participant has confirmed the event
  isCourseEvent BOOLEAN NOT NULL, # whether or not the event is a course event on every calendar
  FOREIGN KEY fk11(participant) REFERENCES Account(uscID)
);

CREATE TABLE Attendance (
  attendanceID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference entries in attendance table
  uscID INT(11) NOT NULL, # associate an entry with a user
  date VARCHAR(12) NOT NULL, # (MM/DD/YYYY) date of the user checked into class
  lecturePeriod VARCHAR(8) NOT NULL, # lecture period the user checked in
  FOREIGN KEY fk12(uscID) REFERENCES Account(uscID)
);
