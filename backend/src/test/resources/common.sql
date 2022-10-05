-- fixture의 COACH id 값을 수동으로 넣어서 values 순서 바꾸면 테스트 깨짐

INSERT INTO available_date_time (available_date_time_status, local_date_time, coach_id)
VALUES ('USED', '2022-07-31 11:00:00', 1),
       ('USED', '2022-07-31 12:00:00', 1),
       ('USED', '2022-07-31 13:00:00', 1),
       ('OPEN', '2022-07-31 14:00:00', 1);

INSERT INTO interview (available_date_time_id, interview_start_time, interview_end_time, interview_status_type, coach_id, crew_id)
VALUES (1, '2022-07-31 11:00:00', '2022-07-31 11:30:00', 'FIXED', 1, 5),
       (2, '2022-07-31 12:00:00', '2022-07-31 12:30:00', 'FIXED', 1, 6),
       (3, '2022-07-31 13:00:00', '2022-07-31 13:30:00', 'EDITABLE', 1, 5);


INSERT INTO form_item (answer, question, interview_id)
VALUES ('1 : 질문 1입니다.', '1 : 답변 1입니다.', 1),
       ('1 : 질문 2입니다.', '1 : 답변 2입니다.', 1),
       ('1 : 질문 3입니다.', '1 : 답변 3입니다.', 1),
       ('2 : 질문 1입니다.', '2 : 답변 1입니다.', 2),
       ('2 : 질문 2입니다.', '2 : 답변 2입니다.', 2),
       ('2 : 질문 3입니다.', '2 : 답변 3입니다.', 2);

-- INSERT INTO comment(member_id, interview_id, comment)
-- VALUES (1, 2, '수달이 작성한 comment');
