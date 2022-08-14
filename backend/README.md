## 수달의 인증 인가 플로우 요구사항

- [ ] 현재 JWT Payload 가 member_id 만으로 이뤄졌는데, memberType 도 추가합니다.
- [ ] login package 만들고 의존성 있는 class 친구들 데려오기  
- [ ] 추가 된 후에 `@onlyCrew` `@onlyCoach` CustomAnnotation 을 만듭니다!
- [ ] RequestScope 에 해당 요청을 보낸 사용자의 memberType 을 넣어줍니다.
- [ ] 그리고 Controller 의 요청이 닿을 때, RequestScope 에 존재하는 memberType 이 가능한 작업인지 확인합니다.
- [ ] 권한이 없는 작업이면 exception 을 날려주고, 권한이 있다면 작업을 수행합니다.


새롭게 공부할 키워드는 `@Aspect` 와 `@RequestScope ` 
