-- fixture의 COACH id 값을 수동으로 넣어서 values 순서 바꾸면 테스트 깨짐
INSERT INTO MEMBER (email, name, image_url, nickname, role)
VALUES ('test1@woowahan.com', '이름',
        'https://user-images.githubusercontent.com/26570275/177680212-63242449-1059-450b-96d8-a06b01a1aea5.png', '준',
        'COACH'),
       ('test2@woowahan.com', '이름',
        'https://user-images.githubusercontent.com/43205258/177765431-63d39896-c8e1-42e8-a229-08309849f2ff.png', '브리',
        'COACH'),
       ('test3@woowahan.com', '이름',
        'https://user-images.githubusercontent.com/26570275/177680217-764aa425-72cb-4b04-adeb-f110121cdf60.png', '토미',
        'COACH'),
       ('test4@woowahan.com', '이름',
        'https://user-images.githubusercontent.com/26570275/177680191-a4497404-c4cb-4f86-8c2c-f4f9c70bcaf7.png', '네오',
        'COACH'),
       ('test5@woowahan.com', '이름',
        'https://user-images.githubusercontent.com/43205258/177765431-63d39896-c8e1-42e8-a229-08309849f2ff.png', '수달',
        'CREW'),
       ('test6@woowahan.com', '이름',
        'https://user-images.githubusercontent.com/26570275/177680217-764aa425-72cb-4b04-adeb-f110121cdf60.png', '앤지',
        'CREW'),
       ('test7@woowahan.com', '이름',
        'https://user-images.githubusercontent.com/26570275/177680191-a4497404-c4cb-4f86-8c2c-f4f9c70bcaf7.png', '애쉬',
        'CREW'),
        ('test8@woowahan.com',
        'https://user-images.githubusercontent.com/26570275/177680191-a4497404-c4cb-4f86-8c2c-f4f9c70bcaf7.png', '록바',
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
