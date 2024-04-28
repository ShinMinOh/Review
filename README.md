![foodsense](https://github.com/ShinMinOh/Review/assets/74702677/39e8d677-95de-415c-b86d-02af9b27d34d)
## 소개

**맛집 탐험, 더 맛있게!**

여행의 시작은 어디에서든지 맛집을 찾는 것입니다.  그 첫 걸음에 조금이나마 도움이 되고자 본 서비스를 기획하게 되었습니다. FoodSense는 당신의 미식 여정을 더욱 풍부하고 흥미롭게 만들어줄 웹사이트입니다.

**[로고의 의미]**

로고는 Food(음식)과 Sense(감각)의 합성어입니다. 우리는 음식을 즐기는 과정에서 오감을 모두 동원하여 맛을 평가합니다. 많은 사람들이 이를 공유하고 경험을 나눌 수 있게 만드는 것이 제가 만든 웹 플랫폼의 핵심 가치입니다. 사용자들이 다양한 음식에 대한 리뷰를 확인할 수 있는 곳으로, 그들의 맛있는 여정을 함께하고자 합니다.
<br></br>

## 본 프로젝트의 목적
1. Oauth 2.0 기반의 JWT 인증 방식을 이용한 회원가입과 로그인 기능 구현
2. Docker를 이용한 EC2환경에서의 프로젝트 배포<br>
   (아래 Postman의 Request URL을 확인해보면 EC2_PublicIP:8080 주소로 요청을 보내는 것을 확인하실 수 있습니다)
3. 맛집 CRUD 구현과, 맛집에 달리는 리뷰의 Create, Delete구현, 리뷰 내용을 Slice를 이용한 무한 스크롤 구현
<br></br>

## 기능구현
![signup](https://github.com/ShinMinOh/Review/assets/74702677/0c8ca013-953a-43aa-9d17-ba05c0f0f6fa)
<b>1. 회원가입</b>
<br>
회원가입시, Request로 이메일과 패스워드를 입력하여 회원가입에 성공하면 Response값으로 해당 이메일을 응답값으로 보내준다.
</br>

![signin](https://github.com/ShinMinOh/Review/assets/74702677/3f97bed5-0d38-450f-8496-c42afe70862d)
<b>2. JWT 인증 방식을 통해 회원가입한 회원이 로그인 할 경우, 토큰 생성</b>
<br>
회원가입 정보와 로그인 정보가 일치할 경우, Response 값으로 RoLE_USER 권한이 있는 AccessToken을 생성한다. 
</br>
<br>
응답값 : "GrantType" "AccessToken" "RefreshToken" "accessTokenExpiresIn" 
</br>
