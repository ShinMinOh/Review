![foodsense](https://github.com/ShinMinOh/Review/assets/74702677/39e8d677-95de-415c-b86d-02af9b27d34d)

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
