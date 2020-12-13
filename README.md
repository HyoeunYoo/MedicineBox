# MedicineBox
### 자동 약 제공 및 의약품 관리 시스템

사용자가 안드로이드 애플리케이션을 통해 복용해야 하는 약을 정해진 시간에 구급함에서 제공받는 시스템 입니다.

해당 프로젝트는 관리자용 웹, 안드로이드 앱, 스마트 구급함(디바이스), 서버 총 4개의 영역으로 구성되어 있습니다.

---

### 주요 기능 시연

* 약 복용

![play1](https://user-images.githubusercontent.com/62014520/102005404-ea784180-3d5b-11eb-9d59-4b7408e4a246.png)

① 메인 화면에서 복용하기 버튼을 누르면 현재 시간과 오늘 복용하는 약에 설정된 복용 시간을 비교합니다.

② 비교한 시간이 1시간 이내이면 ‘복용 하시겠습니까?’라는 메시지가 뜹니다.

③ 확인을 누르면 디바이스에서 해당 약이 제공됩니다.

* 약 버리기

![plqy2](https://user-images.githubusercontent.com/62014520/102005407-ed733200-3d5b-11eb-928e-a2b98452ed37.png)

① 메인 화면에서 약 버리기 버튼을 누르면 오늘 날짜와 보관 의약품의 사용기한을 비교 합니다.

② 사용기한이 지나지 않았으면 ‘유통기한이 지난 약이 없습니다.’라는 메시지가 뜹니다.

③ 사용기한이 지났다면 ‘약을 버리시겠습니까?’라는 메시지가 뜹니다.

④ 확인을 누르면 해당 의약품이 보관되어 있는 슬롯의 잠금장치가 해제되어 약을 꺼내 버릴 수 있고 해당 약은 보관 의약품 목록에서 삭제됩니다.

* 잔여량 측정

![plqy3](https://user-images.githubusercontent.com/62014520/102005412-efd58c00-3d5b-11eb-8ed9-32790d7b94bc.png)

① 잔여량을 측정 중에는 LED가 여러 색상으로 돌아가고 슬롯의 뚜껑에 부착되어 있는 초음파 센서로 잔여량을 측정합니다.

② 측정이 완료되면 LED의 색상이 약이 매우 적으면 빨간색, 중간 양이면 노란색, 가득 차 있으면 녹색으로 설정됩니다. (사진에서는 모든 공간이 비어있는 상태이므로 모두 빨간색 LED가 들어왔습니다.)

---

### 설계

* 소프트웨어 구성도

![software](https://user-images.githubusercontent.com/62014520/102004738-ab93bd00-3d56-11eb-90d5-243e72936960.png)

* 하드웨어 구성도

![hardware](https://user-images.githubusercontent.com/62014520/102005226-8acd6680-3d5a-11eb-9f93-bbba5e6f0823.png)

* 데이터베이스

![db](https://user-images.githubusercontent.com/62014520/102005484-712d1e80-3d5c-11eb-97e8-c78320be5fa1.png)
