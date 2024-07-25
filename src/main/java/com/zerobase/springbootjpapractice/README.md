## Ch01 Spring rest 컨트롤러를 통한 게시판 기능 구현
1. 클라이언트 요청에 대한 주소 생성
- 조건
- 작성 : FirstController.java - first()
- 컨트롤러 인식을 위한 Controller 어노테이션 이용
- 주소 매핑은 RequestMapping 이용
- HTTP 메소드는 GET 방식
- 리턴값은 없음
- 주소는 /first-url
2. 클라이언트 요청에 대한 주소와 문자열을 리턴하는 함수 작성
- 조건
- 작성 : FirstController.java - helloworld()
- 컨트롤러 인식을 위한 Controller 어노테이션 이용
- 주소 매핑은 RequestMapping 이용
- HTTP 메소드는 GET 방식
- 리턴값은 "hello world"
- 주소는 /helloworld
3. 클라이언트 요청에 대한 주소와 Rest 함수 작성
- 조건
- 작성 : SecondController.java - helloString()
- 컨트롤러 인식을 위한 Controller 어노테이션 이용
- 주소 매핑은 RequestMapping 이용
- HTTP 메소드는 GET 방식
- 리턴값은 "hello spring"
- 문자열 리턴위한 어노테이션 사용
- 주소는 /hello-spring
4. 클라이언트 요청에 대한 Rest 함수 작성
- 조건
- 작성 : SecondController.java - helloRest()
- Rest 컨트롤러 형식의 어노테이션 이용
- 주소 매핑은 Rest 형식의 어노테이션 이용
- HTTP 메소드는 GET 방식
- 리턴값은 "hello rest"
- 주소는 /hello-rest
5. 클라이언트 요청에 대한 Rest API 형식 함수 작성
- 조건
- 작성 : SecondController.java - helloRestApi()
- Rest 컨트롤러 형식의 어노테이션 이용
- HTTP 메소드는 GET 방식
- 리턴값은 "hello rest api"
- 주소는 /api/helloworld
6. 공지사항 게시판의 목록에 대한 요청을 처리하는 API 작성
- 조건
- 작성 : ApiNoticeController.java - noticeString()
- REST API 형식으로 구현
- HTTP 메소드는 GET 방식
- 리턴값은 "공지사항입니다."
- 주소는 /api/notice
7. 공지사항 게시판의 목록에 대한 요청을 처리하는 API 작성
- 조건
- 작성 : ApiNoticeController.java - notice()
- REST API 형식으로 구현
- HTTP 메소드는 GET 방식
- 리턴값은 공지사한 게시판의 내용을 추상화한 모델(게시글ID, 제목, 내용, 등록일)이며 데이터는 아래 내용을 리턴
- 게시글Id=1, 제목 = 공지사항입니다, 내용 = 공지사항 내용입니다, 등록일 = 오늘
- 주소는 /api/notice2
8. 공지사항 게시판의 목록에 대한 요청을 처리하는 API 작성
- 조건
- 작성 : ApiNoticeController.java - noticeList()
- REST API 형식으로 구현
- HTTP 메소드는 GET 방식
- 리턴값은 공지사한 게시판의 내용을 추상화한 모델(게시글ID, 제목, 내용, 등록일)이며 복수형태의 데이터를 리턴
- 게시글Id=1, 제목 = 공지사항입니다, 내용 = 공지사항 내용입니다, 등록일 = 오늘
- 게시글Id=2, 제목 = 두번째 공지사항입니다, 내용 = 두번째 공지사항 내용입니다, 등록일 = 어제
- 주소는 /api/notice3
9. 공지사항 게시판의 목록에 대한 요청을 처리하는 API 작성
- 조건
- 작성 : ApiNoticeController.java - noticeEmptyList()
- REST API 형식으로 구현
- HTTP 메소드는 GET 방식
- 리턴값은 공지사한 게시판의 내용을 추상화한 모델(게시글ID, 제목, 내용, 등록일)이며 복수형태의 데이터를 리턴
- 요청한 내용이 없는 빈 목록을 리턴
- 주소는 /api/notice4
10. 공지사항 게시판의 목록에 대한 요청을 처리하는 API 작성
- 조건
- 작성 : ApiNoticeController.java - noticeCount()
- REST API 형식으로 구현
- HTTP 메소드는 GET 방식
- 리턴값은 게시판의 게시글 개수(정수)를 리턴
- * 컨트롤러에서 정수형을 리턴해도 클라이언트 쪽에 내려가는 부분은 문자열
- 주소는 /api/notice/count
11. 공지사항에 글을 등록하기 위해 글작성에 대한 API 작성
- 조건
- 작성 : ApiNoticeController.java - addNotice()
- REST API 형식으로 구현
- HTTP 메소드는 POST 방식
- 전달되는 파라미터는 x-www-form-urlencoded형식의 제목, 내용을 입력받음
- 파라미터는 추상화 하지 않고 기본데이터 타입 형태로 전달받음
- 리턴값은 입력된 형태에 게시글ID를 추가해 모델형태로 리턴
- 주소는 /api/notice
12. 공지사항에 글을 등록하기 위해 글작성에 대한 API 작성
- 조건
- 작성 : ApiNoticeController.java - addNoticeFromModel()
- REST API 형식으로 구현
- HTTP 메소드는 POST 방식
- 전달되는 파라미터는 x-www-form-urlencoded형식의 제목, 내용을 입력받음
- 파라미터를 공지사항 모델로 추상화하여 전달받음
- 리턴값은 입력된 형태에 게시글ID와 등록일자를 추가해 모델형태로 리턴
- 주소는 /api/notice2
13. 공지사항에 글을 등록하기 위해 글작성에 대한 API 작성
- 조건
- 작성 : ApiNoticeController.java - addNoticeFromJson()
- REST API 형식으로 구현
- HTTP 메소드는 POST 방식
- 전달되는 파라미터는 application/json형식의 제목, 내용을 입력받음
- 파라미터를 공지사항 모델로 추상화하여 전달받음
- 리턴값은 입력된 형태에 게시글ID와 등록일자를 추가해 모델형태로 리턴
- 주소는 /api/notice3
14. 공지사항에 글을 등록하기 위해 글작성에 대한 API 작성
- 조건
- 작성 : ApiNoticeController.java - addNoticeSaveToDb()
- REST API 형식으로 구현
- HTTP 메소드는 POST 방식
- 전달되는 파라미터는 application/json형식의 제목, 내용을 입력받음
- 전달된 값을 저장하기 위한 JPA Repository 와 Entity를 통해 Database에 저장
- 리턴값은 저장된 id값이 포함된 Entity 리턴
- 주소는 /api/notice4
15. 공지사항에 글을 등록하기 위해 글작성에 대한 API 작성
- 조건
- 작성 : ApiNoticeController.java - addNoticeSaveToDbWithHitAndLike()
- REST API 형식으로 구현
- HTTP 메소드는 POST 방식
- 전달되는 파라미터는 application/json형식의 제목, 내용을 입력받음
- 공지사항 등록일은 현재 시간을 저장, 공지사항 조회수과 좋아요수는 초기값을 0으로 설정
- 전달된 값을 저장하기 위한 JPA Repository 와 Entity를 통해 Database에 저장
- 리턴값은 저장된 id값이 포함된 Entity 리턴
- 주소는 /api/notice5
16. 공지사항에 글을 수정하기위한 상세정보 요청 API 작성
- 조건
- 작성 : ApiNoticeController.java - getNotice()
- REST API 형식으로 구현
- HTTP 메소드는 GET 방식
- db에 프로그램 실행시 데이터 insert
- 조회된결과 있는 경우 entity / 없는 경우 null return
- 주소는 /api/notice/1
17. 공지사항에 글을 수정하기 위한 API 작성
- 조건
- 작성 : ApiNoticeController.java - updateNotice()
- REST API 형식으로 구현
- HTTP 메소드는 PUT 방식
- 전달되는 파라미터는 application/json형식의 제목, 내용을 입력받음
- 공지사항 수정일은 현재시간을 저장, 공지사항 조회수와 좋아요 수는 변경하지 않음
- 데이터를 수정한 경우 Data매핑에대한 Entity로 필요없는 항목까지 받지 말고 필요한 데이터만 입력받게 작성
- 전달된 값을 수정하기 위한 JPA Repository 와 Entity 통해 db에 수정
- 주소는 /api/notice/{id}
18. 공지사항에 글을 수정하기 위한 API 작성
- 조건
- 작성 : ApiNoticeController.java - updateNoticeHandleError()
- REST API 형식으로 구현
- HTTP 메소드는 PUT 방식
- 전달되는 파라미터는 application/json형식의 제목, 내용을 입력받음
- 공지사항 수정일은 현재시간을 저장, 공지사항 조회수와 좋아요 수는 변경하지 않음
- 데이터를 수정한 경우 Data매핑에대한 Entity로 필요없는 항목까지 받지 말고 필요한 데이터만 입력받게 작성
- 공지사항의 글이 존재하지 않을 경우 예외사항을 발생
- 예외처리는 ExceptionHandler 를 통해 구현, 발생하는 예외에 대해서는 400, 예외 메세지를 리턴
- 주소는 /api/notice2/{id}
19. 공지사항에 글을 수정하기 위한 API 작성
- 조건
- 작성 : ApiNoticeController.java - updateNoticeWithUpdateDate()
- REST API 형식으로 구현
- HTTP 메소드는 PUT 방식
- 전달되는 파라미터는 application/json형식의 제목, 내용을 입력받음
- 공지사항 수정일은 현재시간을 저장, 공지사항 조회수와 좋아요 수는 변경하지 않음
- 데이터를 수정한 경우 Data매핑에대한 Entity로 필요없는 항목까지 받지 말고 필요한 데이터만 입력받게 작성
- 데이터 수정일을 추가해 수정한 날짜/시간도 함께 업데이트
- 주소는 /api/notice3/{id}
20. 공지사항에 글의 조회수를 증가시키는 API 작성
- 조건
- 작성 : ApiNoticeController.java - noticeHits()
- REST API 형식으로 구현
- HTTP 메소드는 PATCH 방식
- 주소는 /api/notice/{id}/hits
21. 공지사항에 글을 삭제하기위한 API 작성
- 조건
- 작성 : ApiNoticeController.java - deleteNotice()
- REST API 형식으로 구현
- HTTP 메소드는 DELETE 방식
- 주소는 /api/notice/{id}
22. 공지사항에 글을 삭제하기위한 API 작성
- 조건
- 작성 : ApiNoticeController.java - deleteNotice()
- REST API 형식으로 구현
- HTTP 메소드는 DELETE 방식
- 전달받은 공지사항의 내용이 조회가 되지않는 경우 NotFoundException을 발생 시킴
- 이에대한 결괴는 400에러와 "내용이 존재하지 않습니다."라는 메세지 리턴
- 주소는 /api/notice/{id}
23. 공지사항에 글을 삭제하기위한 API 작성
- 조건
- 작성 : ApiNoticeController.java - deleteNoticeWithFlag()
- REST API 형식으로 구현
- HTTP 메소드는 DELETE 방식
- 게시판의 글을 물리적으로 삭제하지 않고 삭제 플래그값을 이용해 삭제 진행
- 삭제일시는 현재 시간으로 설정
- 공지사항의 글이 이미 삭제된 경우 200 코드와 "이미 삭제된 글입니다" 메시지 리턴
- 주소는 /api/notice2/{id}
24. 공지사항에 글을 삭제하기위한 API 작성
- 조건
- 작성 : ApiNoticeController.java - deleteNoticeList()
- REST API 형식으로 구현
- HTTP 메소드는 DELETE 방식
- 여러개의 글을 동시에 삭제하기 위해 notice Id 목록을 파라미터로 받아 해당 공지사항의 글 삭제 
- 주소는 /api/notices
25. 공지사항에 모든 글을 삭제하기위한 API 작성
- 조건
- 작성 : ApiNoticeController.java - deleteAllNotice()
- REST API 형식으로 구현
- HTTP 메소드는 DELETE 방식
- 주소는 /api/notice/all
26. 글을 작성할때 제목과 내용을 받아 저장하는 API 작성
- 조건
- 작성 : ApiNoticeController.java - addNoticeFromDto()
- REST API 형식으로 구현
- DTO를 통한 파라미터 형태로 받음
- 등록일은 현재일시, 조회수, 좋아요 수는 0으로 설정
- 전달받은 파라미터를 통해 db에 저장
- 주소는 /api/notice6
27. 글을 작성할때 제목과 내용을 받아 저장하는 API 작성
- 조건
- 작성 : ApiNoticeController.java - addNoticeWithBlankValidation()
- REST API 형식으로 구현
- DTO를 통한 파라미터 형태로 받음
- 제목과 내용은 필수 입력 조건 (입력되지 않은 경우 400 리턴)
- 예외발생시 각 에러를 취합해 콜렉션 형태로 리턴
- 주소는 /api/notice7
28. 글을 작성할때 제목과 내용을 받아 저장하는 API 작성
- 조건
- 작성 : ApiNoticeController.java - addNoticeWithLengthValidation()
- REST API 형식으로 구현
- DTO를 통한 파라미터 형태로 받음
- 제목과 내용은 필수 입력 조건 (입력되지 않은 경우 400 리턴)
- 제목의 경우 10자 이상 100자 이하
- 내용의 경우 50자 이상 100자 이하 
- 예외발생시 각 에러를 취합해 콜렉션 형태로 리턴
- 주소는 /api/notice8
29. db에서 공지사항 목록중 파라미터로 전달된 개수만큼 리턴하는 API 작성
- 조건
- 작성 : ApiNoticeController.java - getNoticeLatest()
- REST API 형식으로 구현
- DTO를 통한 파라미터 형태로 받음
- ex) 최근 5개
- 주소는 /api/notice9
30. 공지사항의 내용을 등록한 이후 바로 동일한 제목/내용의 공지사항을 등록하는 경우 등록 막는 API 작성
- 조건
- 작성 : ApiNoticeController.java - addNoticeBlockDuplication()
- 중복 경우 : 동일제목, 동일 내용과 등록일이 현재시간 기준 1분이내의 경우 중복으로 판단
- 예외발생 : DuplicateNoticeException
- 주소는 /api/notice9
## Ch02 Spring JPA를 통한 CRUD 구현하기
31. 사용자 등록시 입력값이 유효하지 않은 경우 예외를 발생시키는 기능 작성
- 입력값 : 이메일(ID), 이름, 비밀번호, 연락처
- 사용자 정의 에러 모델을 이용해 에러 리턴
- ApiUserController - addUserValidation()
32. 사용자 정보를 입력받아 저장하는 API 작성
- 입력값 : 이메일(유일한 값 확인), 이름, 비밀번호, 연락처, 가입일(현재 일시)
- ApiUserController - addUser()
33. 사용자 정보를 수정하는 API 작성
- 사용자 정보가 없는 경우 UserNotFoundException "사용자 정보가 없습니다." 발생
- 수정 정보는 연락처만 수정가능, 수정일자는 현재 날짜
- ApiUserController - updateUser()
34. 사용자 정보 조회(가입한 아이디에 대한)의 기능을 수행하는 API 작성
- 보안상 비밀번호와 가입일, 회원정보 수정일은 내리지 않음
- ApiUserController - getUser()
35. 내가 작성한 공지사항 목록에 대한 API 작성
- 삭제일과 삭제자 아이디는 보안상 내리지 않음
- ApiUserController - userNotice()
36. 사용자 등록시 이미 존재하는 이메일인경우 예외를 발생시키는 API 작성
- 등록한 이메일에 가입된 회원정보가 존재하는 경우 ExitsEmailException발생 
- ApiUserController - addUserExitsEmailException()
37. 사용자 비밀번호를 수정하는 API 작성
- 이전비밀번호와 일치하는 경우 수정
- 일치하지 않는 경우 PasswordNotMatchException "비밀번호가 일치하지 않습니다." 발생
- ApiUserController - updateUserPassword()
38. 회원가입시 비밀번호를 암호화하여 저장하는 API 작성
- ApiUserController - addUserWithEncrypt()
39. 사용자 회원 탈퇴 기능에 대한 API 작성
- 회원정보가 존재하지 않는 경우 예외처리
- 사용자가 등록한 공지사항이 있는 경우 회원삭제가 되지 않음 
- ApiUserController - deleteUser()
40. 사용자 아이디(이메일)을 찾는 API 작성
- 이름과 전화번호에 해당하는 이메일을 찾음
- ApiUserController - getUser()
41. 사용자 비밀번호 초기화 요청(아이디 입력 후 전화번호로 문자 전송받음)의 기능을 수행하는 API 작성
- 아이디에 대한 정보조회후 비밀번호를 초기화해 문자전송
- 초기화 비밀번호는 문자열 10자로 설정
- ApiUserController - resetUserPassword()
42. 내가 좋아요한 공지사항을 보는 API 작성
- ApiUserController - likeNotice()
43. 사용자 이메일과 비밀번호를 통해 JWT 발생하는 API 작성
- JWT 토큰 발행시 사용자 정보가 유효하지 않을때 예외 발생
- 사용자 정보가 존재하지 않는 경우(UserNotFoundException) 발생
- 비밀번호가 일치하지 않는 경우 (PasswordNotMatchException) 발생 
- ApiUserController - createTokenLogin()
44. 사용자 이메일과 비밀번호를 통해 JWT 발생하는 API 작성
- JWT 토큰 발행
- ApiUserController - createToken()
45. JWT 토큰 발행시 발행 유효기간을 1개월로 저장하는 API 작성
- JWT 토큰 발행
- ApiUserController - createToken()
46. JWT 토큰 재발행(특정 정보인증에 대한)하는 API 작성
- 이미 발행된 JWT 토큰 통해 재발행하는 로직 구현 
- 정상적인 회원에 대해 재발행
- ApiUserController - replaceToken()
47. JWT 토큰 삭제 요청하는 API 작성
- ApiUserController - removeToken()
48. 사용자 목록과 사용자 수를 함께 내리는 REST API 작성
- ResponseData 구조
{
  "totalCount": N,
    "data": user목록컬렉션
}
- ApiAdminController - userList()
49. 사용자 상세 정보를 조회하는 API 작성
    - ResponseMessage 클래스로 추상화해 전송 
      {
      "header": {
            result: ture|false,
            resultCode: string 
            message: error message or alert message 
            status: http result code
            }
        "body": 데이터있는 경우 body통해 전송
    }
- ApiAdminController - usrDetail()
50. 사용자 목록 조회에 대한 검색을 리턴하는 API 작성
- 이메일, 이름, 전화번호에 대한 검색 결과를 리턴 
- ApiAdminController - findUser()
51. 사용자의 상태를 변경하는 API 작성
- 사용자의 상태: (정상, 정지)
- 이에대한 플래그값은 사용자상태 (Using, Stop)
- ApiAdminController - userStatus()
52. 사용자의 정보를 삭제하는 API 작성
- 작성된 게시글이 있으면 예외 처리 발생
- ApiAdminController - deleteUser()
53. 사용자가 로그인 했을때 이에대한 접속이령이 저장되고 접속이력을 조회하는 API 작성
- 접속이력 정보가 있다는 가정하에 API작성
- UserLoginHistory 엔티티 통해 구현 
- ApiAdminController - userLoginHistory()
54. 사용자의 접속을 제한하는 API 작성
- ApiAdminController - userLock()
55. 사용자의 접속을 제한을 해제하는 API 작성
- ApiAdminController - userUnLock()
56. 회원 전체수와 상태별 회원수에 대한 정보를 API 작성
- 서비스 클래스를 이용해 작성
- ApiAdminController - userStatusCount()
57. 오늘의 사용자 가입 목록을 리턴하는 API 작성
- 서비스 클래스를 이용해 작성
- ApiAdminController - todayUser()
58. 사용자별 공지사항의 게시글 수 리턴하는 API 작성
- nativeQuery이용
- ApiAdminController - userNoticeCount()
59. 사용자별 공지사항의 게시글 수의 좋아요수를 리턴하는 API 작성
- nativeQuery이용
- ApiAdminController - userLogCount()
60. 좋아요를 가장 많이 한 사용자 목록(10개) 를 리턴하는 API 작성
- nativeQuery이용
- ApiAdminController - bestLikeCount()







