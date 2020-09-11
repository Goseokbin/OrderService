# Spring Order Service

본 프로젝트는 스프링 부트를 통한 웹서비스 개발을 학습하는 목적으로 진행하였습니다. 단위 테스트 기반으로 CRUD 기능을 작성해 주문을 할 수 있도록 제공하고 REST API을 개발하고,
실무에서  발생할 수 있는  N+1 문제  등 최적화 하는 방법을 학습합니다.


"자바 ORM 표준 JPA-김영한"책과 강의를 참고해서 프로젝트를 진행했습니다.
### 프로젝트 환경 

프로젝트를 실행시키기 위한 도구 및 프로그렘을 나열하세요. 설치 방법도 같이 적어주셔도 됩니다.
- Spring boot : 2.3.2
- Gradle : 6.4.1
- JPA
- H2 데이터 베이스
- 테스트는 JUnit4를 사용했습니다.

### 도메인 모델/테이블 설계

![image](https://user-images.githubusercontent.com/37431938/92863688-2fc46480-f437-11ea-81b8-7ac03fad757e.png)
![image](https://user-images.githubusercontent.com/37431938/92863757-44086180-f437-11ea-886f-6910034da271.png)


REST API는 버전별로 설계를 진행하며 장/단점을 학습합니다.
```
OrderAPIController
v1 : 엔티티를 직접 노출할 경우
v2 : DTO를 통한 조회
v3 : Fetch Join
v4 : JPA 에서 DTO를 직접조회
```

## 구현 화면
 - 메인화면
![image](https://user-images.githubusercontent.com/37431938/92863155-91380380-f436-11ea-854b-322c35648654.png)
- 주문화면
![image](https://user-images.githubusercontent.com/37431938/92863346-c5abbf80-f436-11ea-941d-4258702a5186.png)

