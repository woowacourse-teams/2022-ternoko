-- fixture의 COACH id 값을 수동으로 넣어서 values 순서 바꾸면 테스트 깨짐
INSERT INTO MEMBER (name, nickname, email, user_id, image_url, role)
VALUES ('김동준', '준', 'test1@woowahan.com', 'U0987654321',
        'https://user-images.githubusercontent.com/26570275/177680212-63242449-1059-450b-96d8-a06b01a1aea5.png',
        'COACH'),
       ('조부용', '브리', 'test2@woowahan.com', 'U0987654322',
        'https://user-images.githubusercontent.com/43205258/177765431-63d39896-c8e1-42e8-a229-08309849f2ff.png',
        'COACH'),
       ('김석홍', '토미', 'test3@woowahan.com', 'U0987654323',
        'https://user-images.githubusercontent.com/26570275/177680217-764aa425-72cb-4b04-adeb-f110121cdf60.png',
        'COACH'),
       ('김재연', '네오', 'test4@woowahan.com', 'U0987654324',
        'https://user-images.githubusercontent.com/26570275/177680191-a4497404-c4cb-4f86-8c2c-f4f9c70bcaf7.png',
        'COACH'),
       ('허수진', '수달', 'test5@woowahan.com', 'U0987654325',
        'https://user-images.githubusercontent.com/43205258/177765431-63d39896-c8e1-42e8-a229-08309849f2ff.png',
        'CREW'),
       ('손수민', '앤지', 'test6@woowahan.com', 'U0987654326',
        'https://user-images.githubusercontent.com/26570275/177680217-764aa425-72cb-4b04-adeb-f110121cdf60.png',
        'CREW'),
       ('김동호', '애쉬', 'test7@woowahan.com', 'U0987654327',
        'https://user-images.githubusercontent.com/26570275/177680191-a4497404-c4cb-4f86-8c2c-f4f9c70bcaf7.png',
        'CREW'),
       ('김상록', '록바', 'test8@woowahan.com', 'U0987654328',
        'https://user-images.githubusercontent.com/26570275/177680191-a4497404-c4cb-4f86-8c2c-f4f9c70bcaf7.png',
        'CREW');
INSERT INTO COACH (id, introduce)
VALUES (1, '저는 준입니다.'),
       (2, '저는 브리입니다.'),
       (3, '저는 토미입니다.'),
       (4, '저는 네오입니다.');

INSERT INTO CREW (id)
VALUES (5),
       (6),
       (7),
       (8);

INSERT INTO available_date_time (available_date_time_status, local_date_time, member_id)
VALUES ('USED', '2022-07-31 11:00:00', 1),
       ('USED', '2022-07-31 12:00:00', 1),
       ('USED', '2022-07-31 13:00:00', 1),
       ('OPEN', '2022-07-31 14:00:00', 1);

INSERT INTO interview (interview_start_time, interview_end_time, interview_status_type, coach_id, crew_id)
VALUES ('2022-07-31 11:00:00', '2022-07-31 11:30:00', 'COMMENT', 1, 5),
       ('2022-07-31 12:00:00', '2022-07-31 12:30:00', 'FIXED', 1, 6),
       ('2022-07-31 13:00:00', '2022-07-31 13:30:00', 'COMMENT', 1, 5);

INSERT INTO form_item (answer, question, interview_id)
VALUES ('1 : 질문 1입니다.', '1 : 답변 1입니다.', 1),
       ('1 : 질문 2입니다.', '1 : 답변 2입니다.', 1),
       ('1 : 질문 3입니다.', '1 : 답변 3입니다.', 1),
       ('2 : 질문 1입니다.', '2 : 답변 1입니다.', 2),
       ('2 : 질문 2입니다.', '2 : 답변 2입니다.', 2),
       ('2 : 질문 3입니다.', '2 : 답변 3입니다.', 2);
