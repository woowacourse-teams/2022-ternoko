# interview 에 availableDateTime을 연결하자

- [x] 필드추가

[면담가능시간 조회]
- [x] 픽스처 추가
- [x] 테스트 변경
- [x] 로직 변경

[면담예약생성]
- [x] 픽스처 추가
- [ ] 테스트 변경
- [ ] 로직 변경

[면담예약수정]
- [ ] 픽스처 추가
- [ ] 테스트 변경
- [ ] 로직 변경

### 흐름

[면담가능시간 생성/조회]
1. 코치 스케줄 설정에서 시간누르고 put
2. 백엔드에서 put하며 id 생성 후 면담가능시간 조회 response에 id 담아서 반환

[면담예약생성]
3. interview 생성 request에 availableDateTimeId 담아서 보냄
4. interview 생성 필드에 availableDateTimeId 같이 저장

[면담예약수정]
5. 면담가능시간조회시 해당 interview의 availableDateTime USED로 보내기
6. 현재 interview의 availableDateTime인 경우에는 duplicate 검증 통과
7. 데이터 update