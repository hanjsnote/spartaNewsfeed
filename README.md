# 프로젝트 구성

1.프로젝트 개발 환경: 런타임 버전: 21.0.7+9-b895.130 aarch64 (JCEF 122.1.9) VM: JetBrains s.r.o.의 OpenJDK 64-Bit Server VM
JDK: corretto-17 Amazon Corretto-17.0.15 - aarch64

2. 개발 기간: 2025.08.18 ~ 2025.08.25
3. 팀원별 구현 기능:
4. 기능: 1. 일정 CRUD, 유저 CRUD
5. 실행 방법 Java 17이상 설치 필요 Schedule2Application의 main() 실행시 프로그램 시작



# API 명세서
# 일정 관리
| 기능        | method | url              | 설명        | 응답 코드       | Request                                                                                     | Response                                                                                                                                                         |
|-----------|--------|------------------|-----------|-------------|---------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 게시물 작성    | POST   | /posts           | 게시물 생성 | 201 Created | {<br/>"name": "JS",<br/>"password": "1234",<br/>"title": "제목",<br/>"contents": "내용"<br/>}   | {<br/>"id": 1,<br/>"name": "JS",<br/>"title": "제목",<br/>"contents":"내용",<br/>"createdAt": "2025-08-01",<br/>"modifiedAt": "2025-08-01"<br/>}                     |
| 게시물 조회    | GET    | /posts/          | 게시물 전부 조회  | 200 OK      |                                                                                             | {<br/>"id": 1,<br/>"name": "JS",<br/>"title": "제목",<br/>"contents":"내용",<br/>"createdAt": "2025-08-01",<br/>"modifiedAt": "2025-08-01"<br/>},<br/>{...},<br>.... |
| 게시물 단건 조회 | GET    | /posts/{user_id} | 게시물 단건 조회  | 200 OK      |                                                                                             | {<br/>"id": 1,<br/>"name": "JS",<br/>"title": "제목",<br/>"contents":"내용",<br/>"createdAt": "2025-08-01",<br/>"modifiedAt": "2025-08-01"<br/>}                     |
| 게시물 수정    | PUT    | /posts/{user_id} | 특정 게시물 수정  | 200 OK      | {<br/>"name": "JS2",<br/>"password": "1234",<br/>"title": "제목2",<br/>"contents": "내용"<br/>} | {<br/>"id": 1,<br/>"name": "JS2",<br/>"title": "제목2",<br/>"contents":"내용",<br/>"createdAt": "2025-08-01",<br/>"modifiedAt": "2025-08-02"<br/>}                   |

| 게시물 삭제    | DELETE | /posts/{user_id} | 특정 게시물 삭제  | 200 OK      |                                                                                             | {}                                                                                                                                    |

# ERD
<img width="729" height="841" alt="스크린샷 2025-08-18 151150" src="https://github.com/user-attachments/assets/2a4855f4-8fde-443a-9027-34781872caa6" />
