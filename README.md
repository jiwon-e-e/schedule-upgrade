# schedule-upgrade

## 1. 프로젝트 소개

schedule_upgrade 프로젝트는 사용자가 회원가입 및 로그인 후 일정을 생성하고 관리할 수 있는 일정 관리 서비스입니다.
사용자는 자신이 작성한 일정에 대해 작성자 여부를 검증받아 수정 및 삭제할 수 있으며, 각 일정에 댓글을 작성할 수 있습니다.

※ 본 프로젝트는 REST API 기반 서버로, 별도의 View 화면은 제공하지 않습니다.

### ERD
<img width="1746" height="773" alt="image" src="https://github.com/user-attachments/assets/60e0fdb4-9e0f-4514-9c37-313e608b60f4" />

### 트러블슈팅

https://kjw81024.tistory.com/47

https://kjw81024.tistory.com/48

https://kjw81024.tistory.com/49


## 2. 주요 기능
1. **User 기능**
 - 회원가입
 - 세션 기반 로그인
 - 잘못된 입력에 대한 유효성 검증 및 오류 메시지 제공

2. **Schedule 기능**
 - 일정 생성
 - 일정 전체 조회
	각 일정에 달린 댓글 개수 제공
 - 일정 단건 조회
 - 일정 수정/ 삭제 (작성자만 가능)

3. **Comment 기능**
 - 댓글 생성
 - 댓글 전체 조회
	일정 단건 조회 시 댓글 전체 조회 정보 확인 가능 

본 프로젝트는 기존 schedule 프로젝트의 업그레이드 버전으로, 사용자 편의성과 검증 기능을 강화하였습니다.
- 세션 기반 인증 -> 일정 접근 시 매번 비밀번호 입력 불필요
- DTO 유효성 검증 강화 -> 잘못된 입력에 대한 정확하고 간결한 에러 메시지 제공
- 일정 조회 시 댓글 개수 제공
- 예외 처리 통합 관리




## 3. 빠른 시작 

### 사전 요구사항
- Java 17 이상
- MySQL 8.x
- Gradle (프로젝트에 포함된 Gradle Wrapper 사용 가능)
- Git

IDE는 IntelliJ IDEA, VSCode 등 자유 선택 가능

### 설치 방법

1. **저장소 클론**
```git bash
git clone https://github.com/jiwon-e-e/schedule-upgrade.git
cd schedule-upgrade
```

2. **데이터 베이스 연결**
MySQL 접속 후 아래 명령어 실행
```SQL
CREATE DATABASE schedule_upgrade;
```

3. **환경 설정**
```src/main/resoures/aplication.properties
spring.datasource.url=jdbc:mysql://localhost:3306/schedule_upgrade
spring.datasource.username={본인 SQL 계정}
spring.datasource.password={본인 SQL 비밀번호}
spring.jpa.hibernate.ddl-auto=update
```

4. **실행**
서버 기본 포트
```
http://localhost:8080
```

5. **API 테스트**

본 프로젝트는 REST API 서버로, 브라우저가 아닌 API 테스트 도구를 통해 확인합니다.
Postman 사용 권장

API 명세서를 확인하세요
```
https://documenter.getpostman.com/view/51113144/2sBXcBmMaA
```

## 4. 저장소 구조

```
com.example.schedule_upgrade
 ├─ comment
 │   ├─ controller
 │   ├─ dto
 │   ├─ entity
 │   ├─ repository
 │   └─ service
 │
 ├─ schedule
 │   ├─ controller
 │   ├─ dto
 │   ├─ entity
 │   ├─ repository
 │   └─ service
 │
 ├─ user
 │   ├─ controller
 │   ├─ dto
 │   ├─ entity
 │   ├─ repository
 │   └─ service
 │
 ├─ global
 │   ├─ config
 │   │   └─ PasswordEncoder
 │   │
 │   ├─ exception
 │   │   ├─ ErrorCode
 │   │   ├─ ErrorResponse
 │   │   ├─ GlobalExceptionHandler
 │   │   └─ ServiceException
 │   │
 │   └─ common
 │       └─ BaseEntity
 │
 └─ ScheduleUpgradeApplication

```

## 5. 기술 스택

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Gradle
- Postman (API 테스트)


## 6. 인증 방식

- Session 기반 인증 방식 사용
- 로그인 성공 시 세션 생성
- 작성자 검증을 통해 수정/삭제 권한 제어

## 7. 예외 처리 방식

- @RestControllerAdvice를 활용한 전역 예외 처리
- ErrorCode Enum을 활용한 통일된 에러 응답 형식
- DTO 유효성 검증 실패 시 커스텀 에러 메시지 반환
