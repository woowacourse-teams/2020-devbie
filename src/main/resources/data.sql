INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog', 5000, 'https://cdn.vuetifyjs.com/images/cards/house.jpg', 'BACKEND', 'title', 'JOB', 'hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog1', 5000, 'https://cdn.vuetifyjs.com/images/cards/plane.jpg', 'BACKEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog2', 5000, 'https://cdn.vuetifyjs.com/images/cards/road.jpg', 'BACKEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog3', 5000, 'test', 'BACKEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog4', 5000, 'https://cdn.vuetifyjs.com/images/cards/house.jpg', 'BACKEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog5', 5000, 'https://cdn.vuetifyjs.com/images/cards/plane.jpg', 'BACKEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog6', 5000, 'https://cdn.vuetifyjs.com/images/cards/road.jpg', 'BACKEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog7', 5000, 'test', 'BACKEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('yeji1', 6000, 'test', 'BACKEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog6', 5000, 'https://cdn.vuetifyjs.com/images/cards/road.jpg', 'FRONTEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog7', 5000, 'test', 'FRONTEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('yeji1', 6000, 'test', 'FRONTEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog6', 5000, 'https://cdn.vuetifyjs.com/images/cards/road.jpg', 'FRONTEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('bossdog7', 5000, 'test', 'FRONTEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('yeji1', 6000, 'test', 'FRONTEND', 'title', 'JOB','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('yeji2', 6000, 'test', 'BACKEND', 'title', 'EDUCATION','hi!');
INSERT INTO notice(name, salary, image, job_position, title, notice_type, content) VALUES('yeji3', 6000, 'test', 'FRONTEND', 'title', 'EDUCATION','hi!');


INSERT INTO language(notice_id, languages) values (1, 'CPP');
INSERT INTO language(notice_id, languages) values (1, 'JAVA');
INSERT INTO language(notice_id, languages) values (2, 'CPP');
INSERT INTO language(notice_id, languages) values (3, 'CPP');
INSERT INTO language(notice_id, languages) values (4, 'CPP');
INSERT INTO language(notice_id, languages) values (5, 'CPP');
INSERT INTO language(notice_id, languages) values (6, 'CPP');
INSERT INTO language(notice_id, languages) values (7, 'CPP');
INSERT INTO language(notice_id, languages) values (8, 'CPP');

INSERT INTO question (question_id, user_id, title, content, visit_count) VALUES (1, 100, '가비지 컬렉션이란 무엇인가','GC를 어느정도 까지 알아야할까요?', 100);
INSERT INTO question (question_id, user_id, title, content, visit_count) VALUES (2, 54, 'PUT과 PATCH의 차이','PUT과 PATCH가 둘다 수정을 위한 HTTP METHOD로 알고 있는데, 어떤 차이가 있나요?', 30);

INSERT INTO answer (id, user_id, question_id, content) VALUES (1, 101, 1,'가비지 컬렉션 중요합니다. 기본적인 작동 원리는 알아야 합니다. 자세한 내용은 구글링 해보세요');
INSERT INTO answer (id, user_id, question_id, content) VALUES (2, 102, 1,'가비지 컬렉션 중요합니다. 기본적인 작동 원리는 알아야 합니다. 자세한 내용은 구글링 해보세요');
INSERT INTO answer (id, user_id, question_id, content) VALUES (3, 103, 1,'가비지 컬렉션 중요합니다. 기본적인 작동 원리는 알아야 합니다. 자세한 내용은 구글링 해보세요');
INSERT INTO answer (id, user_id, question_id, content) VALUES (4, 104, 2, 'PUT은 전체 수정, PATCH는 부분 수정을 할 때 사용합니다');
INSERT INTO answer (id, user_id, question_id, content) VALUES (5, 105, 2, 'PUT은 전체 수정, PATCH는 부분 수정을 할 때 사용합니다');
