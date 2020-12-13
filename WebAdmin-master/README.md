### 자동 약 제공 및 의약품 관리 시스템 프로젝트 입니다.

사용자가 안드로이드 애플리케이션을 통해 복용해야 하는 약을 정해진 시간에 구급함에서 제공받는 시스템 입니다.

해당 프로젝트는 관리자용 웹, 안드로이드 앱, 스마트 구급함(디바이스), 서버 총 4개의 영역으로 구성되어 있으며, 해당 레파지토리는 관리자용 웹 레파지토리 입니다.

---

# Medicine Box - 관리자용 웹
#### 공급된 구급함과 사용자, 의약품 정보를 관리하는 관리자용 웹
사용자의 빠른 검색을 위해 데이터베이스에 의약품 정보를 추가하고 사용자가 많이 보관하는 의약품, 많이 검색하는 의약품을 시각화를 통해
확인하여 해당 의약품을 수시로 관리할 수 있도록 하는 기능을 제공합니다.

* 로그인 화면

![login](https://user-images.githubusercontent.com/62014520/102003498-9bc2ab80-3d4b-11eb-90c7-24b3048e790c.png)

관리자용 계정으로 로그인할 수 있습니다.

* 메인 화면

![main](https://user-images.githubusercontent.com/62014520/102003456-1808bf00-3d4b-11eb-8cf5-40d6f6916b85.png)

데이터 시각화로, 사용자가 많이 보관하는 의약품과 많이 검색하는 의약품을 파이 그래프 형태로 볼 수 있습니다.

* 사용자 정보 조회 화면

![user](https://user-images.githubusercontent.com/62014520/102003524-df1d1a00-3d4b-11eb-851f-8d7bfa63efe3.png)

가입한 사용자의 정보와 연결된 디바이스 일련번호를 확인할 수 있으며, 수정도 가능합니다.

* 의약품 정보 조회 화면

![medi](https://user-images.githubusercontent.com/62014520/102003539-f78d3480-3d4b-11eb-9ae2-cecf00688fe0.png)

데이터베이스에 등록한 의약품 정보와 사용자의 보관, 검색 횟수를 확인할 수 있습니다. 수정, 삭제도 가능합니다.



## Program Stacks
### Client
* JSP
* HTML
* CSS
* JavaScript
* jQuery
* Bootstrap

### Server
* AWS EC2
* MariaDB
* Java
