# 프로젝트 구성

1.프로젝트 개발 환경: 런타임 버전: 21.0.7+9-b895.130 aarch64 (JCEF 122.1.9) VM: JetBrains s.r.o.의 OpenJDK 64-Bit Server VM
JDK: corretto-17 Amazon Corretto-17.0.15 - aarch64

2. 개발 기간: 2025.08.18 ~ 2025.08.25
3. 팀원별 구현 기능
    - 김제경 : 좋아요 기능
    - 한정식 : 로그인, 유저 CRUD
    - 강성규 : 댓글 CRUD
    - 이지호 : 팔로우 기능
    - 김병수 : 게시글 CRUD
4. 기능: 로그인, 유저 CRUD, 게시글 CRUD, 댓글 CRUD, 좋아요 및 팔로우 기능
5. 실행 방법 Java 17이상 설치 필요 Schedule2Application의 main() 실행시 프로그램 시작

# API 명세서

# 로그인 // 완료

| 기능   | method | url           | 설명        | 응답 코드                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             | Request                                                                           | Response                                                                                                                                                                 |
|------|--------|---------------|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 로그인  | POST   | /users/login  | 로그인       | 200 OK   <br/> <br/>"status": 401,<br/>  "message": "이메일 또는 비밀번호가 일치하지 않습니다." | {<br/>"email": "honggildong@gmail.com”,<br/>"password": "1234"<br/>} | {<br/>"로그인 성공”<br/>}  |
| 회원가입 | POST   | /users/signup | 회원가입 | 201 Created <br/><br/>  "status": 400,<br/>"message": "이메일은 필수 입력값입니다.",<br/>"path": "/users/signup",<br/>"error": "BAD_REQUEST",<br/>"timestamp": "2025-08-25T07:11:37.315413"  <br/><br/>  "status": 400,<br/>"message": "이름은 필수 입력값입니다.",<br/>"path": "/users/signup",<br/>"error": "BAD_REQUEST",<br/>"timestamp": "2025-08-25T07:12:31.763326"  <br/> <br/>   "status": 400,<br/> "message": "비밀번호는 필수 입력값입니다.",<br/> "path": "/users/signup",<br/> "error": "BAD_REQUEST",<br/> "timestamp": "2025-08-25T07:13:57.023237" <br/><br/>    "status": 404,<br/> "message": "탈퇴한 이메일 입니다.",<br/> "path": "/users/signup",<br/>"error": "NOT_FOUND",<br/>"timestamp": "2025-08-25T07:17:16.949715" | {<br/> ”email”: “gildong@gmail.com”,<br/>“name”: “홍길동”,<br/>“password”:1234<br/>} | {<br/>”id”: “1”,<br/>“email”: “gildong@gmail.com”,<br/>“name”: “홍길동”,<br/>“createdAt”: “2025-08-23T14:34:34.55864",<br/> “modifiedAt”: “2025-08-23T14:34:34.55864”<br/>} |
| 로그아웃 | POST   | /users/logout | 로그아웃    | 200 OK     <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | {<br/><br/>}                                                                      | {<br/><br/>}                                                                                                                                                             |

<br>

# 유저

| 기능       | method | url             | 설명       | 응답 코드                                                                                                                                                                                                                                                                                                                                                                                                                            | Request                                                                                                                         | Response                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
|----------|--------|-----------------|----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 회원 전체 조회 | GET    | /users?name=홍길동 | 회원 전체 조회 | 200 OK   <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."                                                                                                                                                                                                                                                                                                                                                      | {<br/><br/>}                                                                                                                    | {<br/>{<br/>"id": null,"email": null,<br/>"name": "홍길동1",<br/>"createAt": "2025-08-23T14:34:29.742073",<br/>"modifiedAt": null<br/>},<br/>{<br/>"id": 2,<br/>"email": "gildong2@gmail.com",<br/>"name": "홍길동 수정",<br/>"createAt": "2025-08-23T14:34:34.55864",<br/>"modifiedAt": "2025-08-23T16:33:33.508111"<br/>},<br/> {<br/>"id": null,<br/>"email": null,<br/>"name": "홍길동3",<br/>"createAt": "2025-08-23T14:34:38.357428",<br/>"modifiedAt": null <br/>} ,<br/>{…},,<br/> …. ,<br/>//다른 회원의 민감정보는 null 표시<br/>} |
| 회원 수정    | PATCH  | /users/{userId} | 회원 수정    | 200 OK   <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."    <br/><br/>   "status": 404,<br/> "message": "해당 유저가 존재하지 않습니다.",<br/> "path": "/users/1",<br/>"error": "NOT_FOUND",<br/> "timestamp": "2025-08-25T07:18:47.47681"                                                                                                                                                                                 | {<br/> ”email”: “gildong2@gmail.com”,<br/>“oldPassword”: “123456a*”,<br/>“newPassword”: “123456b*”,<br/> “name”: “홍길동 수정”<br/>} | {<br/> “email”: “gildong2@gmail.com”,<br/> “name”: “홍길동 수정”, <br/>}                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| 회원 탈퇴    | DELETE | /users/{userId} | 회원 탈퇴    | 200 OK    <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."   <br/><br/> "status": 404,<br/> "message": "해당 유저가 존재하지 않습니다.",<br/> "path": "/users/1",<br/> "error": "NOT_FOUND",<br/> "timestamp": "2025-08-25T07:19:46.274074"  <br/><br/>             "status": 409,<br/>  "message": "비밀번호가 일치하지 않습니다.",<br/>  "path": "/users/2",<br/>  "error": "CONFLICT",<br/> "timestamp": "2025-08-25T07:20:25.505792" | {<br/>  "password": "123456a*"<br/>}                                                                                            | {<br/><br/>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |

<br>

# 게시글

| 기능            | method | url              | 설명            | 응답 코드                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    | Request                                                                | Response                                                                                                                                                                                                                                                                                                                                                                                                                  |
|---------------|--------|------------------|---------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 게시물 작성        | POST   | /posts           | 게시물 작성        | 200 OK     <br/><br/> "status": 401 Unauthorized,<br/> "message": "로그인 해주세요."     <br/><br/>   "status": 400,<br/> "message": "제목을 입력해 주세요!",<br/> "path": "/posts",<br/> "error": "BAD_REQUEST",<br/> "timestamp": "2025-08-25T07:23:11.374851"  <br/> <br/> "status": 400,<br/> "message": "내용을 입력해 주세요!",<br/>"path": "/posts",<br/> "error": "BAD_REQUEST",<br/>"timestamp": "2025-08-25T08:02:00.666953"                                                                                                                                                                            | {<br/> "title": "새로운 포스트",<br/> "content": "이것은 새로운 포스트의 내용입니다."<br/>} | {<br/>"id": 1,<br/>"userId": 1,<br/>"name":"홍길동",<br/>"title":"게시물 제목",<br/>"content":"게시물 내용",<br/>"createdAt":"2025-08-18T16:30:00",<br/>"modifiedAt":"2025-08-18T16:30:00"<br/>}                                                                                                                                                                                                                                       |
| 게시물 수정        | PATCH  | /posts/{postId}  | 특정 게시물 수정     | 200 OK   <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."     <br/><br/>     "status": 404,<br/>  "message": "해당 게시물이 존재하지 않습니다.",<br/>  "path": "/posts/1",<br/>  "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T07:21:38.91167"    <br/><br/>   "status": 400,<br/> "message": "제목을 입력해 주세요!",<br/> "path": "/posts",<br/> "error": "BAD_REQUEST",<br/> "timestamp": "2025-08-25T07:23:11.374851"  <br/> <br/> "status": 400,<br/> "message": "내용을 입력해 주세요!",<br/>"path": "/posts",<br/> "error": "BAD_REQUEST",<br/>"timestamp": "2025-08-25T08:02:00.666953" | {<br/> "title": "수정될 제목", <br/>"content": "이것은 수정될 포스트의 내용입니다."<br/>}  | {<br/>"id": 1,<br/>"userId": 1,<br/>"name":"홍길동",<br/>"title":"게시물 제목",<br/>"content":"게시물 내용",<br/>"createdAt":"2025-08-18T16:30:00",<br/>"modifiedAt":"2025-08-18T16:30:00"<br/>}                                                                                                                                                                                                                                       |
| 게시물 전체 조회     | GET    | /posts/?search(string, optional)           | 게시물 전체 조회     | 200 OK   <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."        <br/><br/>     "status": 404,<br/>  "message": "해당 게시물이 존재하지 않습니다.",<br/>  "path": "/posts/1",<br/>  "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T07:21:38.91167"                                                                                                                                                                                                                                                                                                                             | {<br/><br/>}                                                           | {<br/>"id": 1,<br/>"name": "JS2",<br/>"title": "제목2",<br/>"content":"내용",<br/>"createdAt": "2025-08-01",<br/>"modifiedAt": "2025-08-02"<br/>"likeCount": 0<br/>}                                                                                                                                                                                                                                                          |
| 게시물 팔로우 전체 조회 | GET    | /posts/followers | 게시물 팔로우 전체 조회 | 200 OK  <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."         <br/><br/>     "status": 404,<br/>  "message": "해당 게시물이 존재하지 않습니다.",<br/>  "path": "/posts/1",<br/>  "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T07:21:38.91167"                                                                                                                                                                                                                                                                                                                             | {<br/><br/>}                                                           | {<br/>{<br/>"id":1,<br/>"userId":1,<br/>"followerId":1,<br/>"name":"홍길동",<br/>"title":"게시물 제목",<br/>"content":"게시물 내용",<br/>"createdAt":"2025-08-18T16:30:00",<br/>"modifiedAt":"2025-08-18T16:30:00",<br/>"likeCount":1<br/>},<br/>{<br/>...<br/>},<br/>...<br/>}                                                                                                                                                        |
| 게시물 단건 조회     | GET    | /posts/{user_id} | 게시물 단건 조회     | 200 OK     <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."     <br/><br/>     "status": 404,<br/>  "message": "해당 게시물이 존재하지 않습니다.",<br/>  "path": "/posts/1",<br/>  "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T07:21:38.91167"                                                                                                                                                                                                                                                                                                                              | {<br/><br/>}                                                           | {<br/>"{<br/>"id":1,<br/>"userId":1,<br/>"name":"홍길동",<br/>"title":"게시물 제목",<br/>"content":"게시물 내용",<br/>"createdAt":"2025-08-18T16:30:00",<br/>"modifiedAt":"2025-08-18T16:30:00",<br/>"likes":1<br/>},<br/>comments<br/>{<br/>"id":1,<br/>"userId":1,<br/>"content":"게시물 내용",<br/>"name":"홍길동",<br/>"createdAt":"2025-08-18T16:30:00",<br/>"modifiedAt":"2025-08-18T16:30:00"<br/>},<br/>{<br/>...<br/>},<br/>...<br/>} |
| 게시물 삭제        | DELETE | /posts/{user_id} | 게시물 삭제        | 204 No Content   <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."        <br/><br/>  "timestamp": "2025-08-24T23:31:53.132+00:00",<br/> "status": 405,<br/> "error": "Method Not Allowed",<br/> "path": "/posts" <br/><br/>   "status": 404,<br/>  "message": "해당 게시물이 존재하지 않습니다.",<br/>  "path": "/posts/1",<br/>  "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T07:21:38.91167"                                                                                                                                                                               | {<br/><br/>}                                                           | {<br/><br/>}                                                                                                                                                                                                                                                                                                                                                                                                              |

<br>

# 댓글

| 기능       | method | url                                 | 설명       | 응답 코드                                                                                                                                                                                                                                                                                                                                                                                                                                                     | Request                                      | Response                                                                                                                                                                                                                       |
|----------|--------|-------------------------------------|----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 댓글 작성    | POST   | /post/{postId}/comments             | 댓글 작성    | 200 OK   <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."     <br/> <br/>   "status": 404,<br/>  "message": "해당 게시글이 존재하지 않습니다.",<br/>  "path": "/posts/1/comments",<br/> "error": "NOT_FOUND",<br/> "timestamp": "2025-08-25T08:44:20.674806"   <br/> <br/>     "status": 404,<br/>  "message": "해당 댓글이 존재하지 않습니다.",<br/>  "path": "/posts/6/comments/1",<br/>  "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T08:41:48.597024"  | {<br/>"content": "이것은 새로운 포스트의 내용입니다."<br/>} | {<br/> "id": 2,<br/>"userId": 2,<br/>"postId": 1,<br/>"name": "두부11",<br/>"content": "이것은 새로운 포스트의 내용입니다.",<br/>"createdAt": "2025-08-23T19:26:15.638353",<br/>"modifiedAt": "2025-08-23T19:26:15.638353"<br/>}                |
| 댓글 수정    | PATCH  | /post/{postId}/comments/{commentId} | 댓글 수정    | 200 OK     <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."    <br/> <br/>   "status": 404,<br/>  "message": "해당 게시글이 존재하지 않습니다.",<br/>  "path": "/posts/1/comments",<br/> "error": "NOT_FOUND",<br/> "timestamp": "2025-08-25T08:44:20.674806"   <br/> <br/>     "status": 404,<br/>  "message": "해당 댓글이 존재하지 않습니다.",<br/>  "path": "/posts/6/comments/1",<br/>  "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T08:41:48.597024" | {<br/> "content" : "정식님 왜 그러셨어요1..."<br/>}   | {<br/> "id": 2,<br/>"userId": 2,<br/>"postId": 1,<br/>"name": "두부11",<br/>"content": "지호님",<br/>"createdAt": "2025-08-23T19:26:15.638353",<br/>"modifiedAt": "2025-08-23T19:26:15.638353"<br/>}                                |
| 댓글 조회    | GET    | /post/{postId}/comments             | 댓글 조회    | 200 OK    <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."     <br/> <br/>     "status": 404,<br/>  "message": "해당 댓글이 존재하지 않습니다.",<br/>  "path": "/posts/6/comments/1",<br/>  "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T08:41:48.597024"                                                                                                                                                                                    | {<br/> <br/>}                                | {<br/>{<br/>"id": 2,<br/>"userId": 2,<br/>"postId": 1,<br/>"name": "두부11",<br/>"content": "지호님",<br/>"createdAt": "2025-08-23T19:26:15.638353",<br/>"modifiedAt": "2025-08-23T19:26:15.638353"<br/>}<br/>{<br/>...<br/>}<br/>} |
| 댓글 단건 조회 | GET    | /post/{postId}/comments/{commentId} | 댓글 단건 조회 | 200 OK    <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."      <br/> <br/>     "status": 404,<br/>  "message": "해당 댓글이 존재하지 않습니다.",<br/>  "path": "/posts/6/comments/1",<br/>  "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T08:41:48.597024"                                                                                                                                                                                   | {<br/> <br/>}                                | {<br/>"id": 2,<br/>"userId": 2,<br/>"postId": 1,<br/>"name": "두부11",<br/>"content": "지호님",<br/>"createdAt": "2025-08-23T19:26:15.638353",<br/>"modifiedAt": "2025-08-23T19:26:15.638353"<br/>}                                 |
| 댓글 삭제    | DELETE | /post/{postId}/comments/{commentId} | 댓글 삭제    | 204 No Content  <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."      <br/> <br/>     "status": 404,<br/>  "message": "해당 댓글이 존재하지 않습니다.",<br/>  "path": "/posts/6/comments/1",<br/>  "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T08:41:48.597024"                                                                                                                                                                             | {<br/><br/>}                                 | {<br/><br/>}                                                                                                                                                                                                                   |

<br>

# 좋아요

| 기능     | method | url                  | 설명          | 응답 코드                                                                                                                                                                                                                                                                                                                                                                                                                            | Request      | Response                                                                                            |
|--------|--------|----------------------|-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|-----------------------------------------------------------------------------------------------------|
| 좋아요 토글 | POST   | /post/{postId}/likes | 좋아요 추가 및 취소 | 200 OK  <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."  <br/><br/>   "status": 404,<br/> "message": "게시물이 존재하지 않습니다.",<br/> "path": "/posts/1/likes",<br/> "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T08:45:57.950898"<br/><br/>"status" : 403,<br/>"error" : "FORBIDDEN",<br/>"message" : "자신의 글에는 좋아요를 누를 수 없습니다.",<br/>"path" : "/post/{postId}/likes",<br/>"timestamp" : "2025.08.22T16:00:00" | {<br/><br/>} | {<br/>"좋아요가 되었습니다."<br/>}<br/>{<br/>"종아요 취소 되었습니다."<br/>}                                           |
| 좋아요 조회 | GET    | /post/{postId}/likes | 좋아요 조회      | 200 OK  <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."                                                                                                                                                                                                                                                                                                                                                       | {<br/><br/>} | {<br/>[<br/>"likeId":1,<br/>"usesrId":1,<br/>"userName":"홍길동"<br/>],<br/>[<br/>...<br/>],<br/>... } |

<br>

# 팔로우

| 기능     | method | url     | 설명     | 응답 코드                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               | Request      | Response                            |
|--------|--------|---------|--------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|-------------------------------------|
| 팔로우 추가 | POST   | /follow | 팔로우 추가 | 200 OK <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."       <br/> <br/>  "status": 409,<br/>  "message": "이미 팔로우 되어있는 대상입니다.",<br/>  "path": "/api/users/2/follow",<br/>  "error": "CONFLICT",<br/>  "timestamp": "2025-08-25T09:04:58.718616" <br/><br/>  "status": 404,<br/>  "message": "사용자가 존재하지 않습니다.",<br/>  "path": "/api/users/2/follow",<br/> "error": "NOT_FOUND",<br/> "timestamp": "2025-08-25T08:57:26.867855"  <br/><br/>  "status": 400,<br/>  "message": "자기 자신을 팔로우할 수 없습니다.",<br/>"path": "/api/users/1/follow",<br/> "error": "BAD_REQUEST",<br/> "timestamp": "2025-08-25T09:00:13.072167" | {<br/><br/>} | {<br/> "message": "팔로우했습니다."<br/>}  |
| 팔로우 삭제 | DELETE | /follow | 팔로우 삭제 | 200 OK <br/><br/> "status": 401 Unauthorized, <br/>"message": "로그인 해주세요."    <br/> <br/>    "status": 404,<br/>  "message": "사용자가 존재하지 않습니다.",<br/>   "path": "/api/users/3/follow",<br/>   "error": "NOT_FOUND",<br/>  "timestamp": "2025-08-25T09:07:04.968426" <br/> <br/>    "status": 404,<br/>   "message": "팔로우 되어있지 않은 대상입니다.",<br/>   "path": "/api/users/1/follow",<br/>   "error": "NOT_FOUND",<br/>    "timestamp": "2025-08-25T09:06:26.989177"                                                                                                                                                                        | {<br/><br/>} | {<br/> "message": "언팔로우됐습니다."<br/>} |

# 파일 구조

````
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── spartanewsfeed
│   │               ├── SpartaNewsfeedApplication.java
│   │               ├── comment
│   │               │   ├── controller
│   │               │   │   └── CommentController.java
│   │               │   ├── dto
│   │               │   │   ├── request
│   │               │   │   │   └── CommentRequest.java
│   │               │   │   └── response
│   │               │   │       ├── CommentResponse.java
│   │               │   │       └── PostCommentResponse.java
│   │               │   ├── entity
│   │               │   │   └── Comment.java
│   │               │   ├── repository
│   │               │   │   └── CommentRepository.java
│   │               │   └── service
│   │               │       └── CommentService.java
│   │               ├── common
│   │               │   ├── advice
│   │               │   │   └── GlobalExceptionHandler.java
│   │               │   ├── config
│   │               │   │   ├── FilterConfig.java
│   │               │   │   └── PasswordEncoder.java
│   │               │   ├── consts
│   │               │   │   └── Const.java
│   │               │   ├── entity
│   │               │   │   └── BaseEntity.java
│   │               │   ├── exception
│   │               │   │   ├── DataNotFoundException.java
│   │               │   │   ├── GlobalException.java
│   │               │   │   ├── InvalidCredentialException.java
│   │               │   │   ├── NoPermissionException.java
│   │               │   │   └── response
│   │               │   │       └── ErrorResponse.java
│   │               │   └── filter
│   │               │       └── LoginFilter.java
│   │               ├── follow
│   │               │   ├── controller
│   │               │   │   └── FollowController.java
│   │               │   ├── dto
│   │               │   │   └── response
│   │               │   │       └── FollowResponse.java
│   │               │   ├── entity
│   │               │   │   └── Follow.java
│   │               │   ├── repository
│   │               │   │   └── FollowRepository.java
│   │               │   └── service
│   │               │       └── FollowService.java
│   │               ├── like
│   │               │   ├── controller
│   │               │   │   └── LikeController.java
│   │               │   ├── dto
│   │               │   │   └── LikeResponse.java
│   │               │   ├── entity
│   │               │   │   └── Like.java
│   │               │   ├── repository
│   │               │   │   └── LikeRepository.java
│   │               │   └── service
│   │               │       └── LikeService.java
│   │               ├── post
│   │               │   ├── controller
│   │               │   │   └── PostController.java
│   │               │   ├── dto
│   │               │   │   ├── request
│   │               │   │   │   ├── PatchRequest.java
│   │               │   │   │   └── PostRequest.java
│   │               │   │   └── response
│   │               │   │       ├── FollowerGetResponse.java
│   │               │   │       ├── GetResponse.java
│   │               │   │       ├── PatchResponse.java
│   │               │   │       ├── PostResponse.java
│   │               │   │       └── SingleGetResponse.java
│   │               │   ├── entity
│   │               │   │   └── Post.java
│   │               │   ├── repository
│   │               │   │   └── PostRepository.java
│   │               │   └── service
│   │               │       └── PostService.java
│   │               └── user
│   │                   ├── controller
│   │                   │   ├── LoginController.java
│   │                   │   └── UserController.java
│   │                   ├── dto
│   │                   │   ├── request
│   │                   │   │   ├── UserDeleteRequest.java
│   │                   │   │   ├── UserLoginRequest.java
│   │                   │   │   ├── UserSignUpRequest.java
│   │                   │   │   └── UserUpdateRequest.java
│   │                   │   └── response
│   │                   │       ├── UserFindResponse.java
│   │                   │       ├── UserSignUpResponse.java
│   │                   │       └── UserUpdateResponse.java
│   │                   ├── entity
│   │                   │   ├── DeletedEmail.java
│   │                   │   └── User.java
│   │                   ├── repository
│   │                   │   ├── DeletedEmailRepository.java
│   │                   │   └── UserRepository.java
│   │                   └── service
│   │                       └── UserService.java
│   └── resources
│       └── application.yml
└── test
    └── java
        └── com
            └── example
                └── spartanewsfeed
                    └── SpartaNewsfeedApplicationTests.java
````

# ERD

<img width="1050" height="531" alt="image" src="https://github.com/user-attachments/assets/25d9de65-95c9-4bd2-a05c-c4c4ae7f7bb7" />



