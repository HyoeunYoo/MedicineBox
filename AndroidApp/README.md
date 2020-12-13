### 자동 약 제공 및 의약품 관리 시스템 프로젝트 입니다.

사용자가 안드로이드 애플리케이션을 통해 복용해야 하는 약을 정해진 시간에 구급함에서 제공받는 시스템 입니다.

해당 프로젝트는 관리자용 웹, 안드로이드 앱, 스마트 구급함(디바이스), 서버 총 4개의 영역으로 구성되어 있으며, 해당 레파지토리는 안드로이드 앱 레파지토리 입니다.

---

# Medicine Box - 안드로이드 앱
#### 스마트 구급함과 연동하여 의약품을 기록하고 복용 알림을 받는 앱
* 애플리케이션에서 보관하는 의약품을 추가하여 복용 알림을 받아 날짜와 시간에 따라 약을 복용할 수 있습니다.
* 사용기한이 지난 의약품을 수시로 버릴 수 있으며 의약품 별로 의약품 정보와 복용 정보를 관리할 수 있습니다.
* 보관 중인 의약품이 아니더라도 원하는 의약품의 정보를 검색할 수 있습니다.


로그인 화면 > ![login](https://user-images.githubusercontent.com/62014520/102003815-11c81200-3d4e-11eb-8cfc-558289b42fe3.png) ![deviceconn](https://user-images.githubusercontent.com/62014520/102003820-1c82a700-3d4e-11eb-91b3-2baf59d0438d.png) < 디바이스 인증 화면

사용자 계정으로 가입하여 로그인하고 인증을 통해 스마트 구급함과 연동하여 사용을 시작합니다.

메인 화면 > ![main](https://user-images.githubusercontent.com/62014520/102003794-007f0580-3d4e-11eb-8e07-e847f48313f7.png) ![infomedi2](https://user-images.githubusercontent.com/62014520/102003854-71262200-3d4e-11eb-99c0-eba754cd23a5.png) < 보관 의약품 정보 화면

사용자가 스마트 구급함에 보관 중인 의약품을 한 눈에 확인할 수 있으며 목록에서 항목을 선택하면 해당 의약품의 정보와 설정한 복용 정보를 확인할 수 있습니다.

보관 의약품 추가 화면 - 요일별 > ![addday](https://user-images.githubusercontent.com/62014520/102003842-5653ad80-3d4e-11eb-824b-60ca725927b1.png) ![addper](https://user-images.githubusercontent.com/62014520/102003846-5a7fcb00-3d4e-11eb-94ea-53e3113abaff.png) < 보관 의약품 추가 화면 - 주기별

보관 의약품을 요일별, 주기별로 설정하여 추가할 수 있고 추가가 완료되면 선택한 구급함 슬롯의 잠금이 해제되어 약을 투입할 수 있습니다.

의약품 검색 화면 > ![search](https://user-images.githubusercontent.com/62014520/102003828-42a84700-3d4e-11eb-934e-90097d2edfad.png) ![searchresult](https://user-images.githubusercontent.com/62014520/102003831-48059180-3d4e-11eb-9aeb-387c63ea6911.png)

공공데이터포털에서 제공하는 의약품 식별 open API를 통해 의약품을 검색할 수 있습니다.



## Program Stacks
* Android 8.0.0 (Oreo)
* Java
* AWS EC2
* MariaDB
* 공공데이터포털 open API

