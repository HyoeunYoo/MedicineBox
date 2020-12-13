### 자동 약 제공 및 의약품 관리 시스템 프로젝트 입니다.

사용자가 안드로이드 애플리케이션을 통해 복용해야 하는 약을 정해진 시간에 구급함에서 제공받는 시스템 입니다.

해당 프로젝트는 관리자용 웹, 안드로이드 앱, 스마트 구급함(디바이스), 서버 총 4개의 영역으로 구성되어 있으며, 해당 레파지토리는 디바이스 레파지토리 입니다.

---

# Medicine Box - 디바이스
#### 임베디드 시스템을 활용하여 약을 보관, 제공받는 스마트 구급함
복용 시간에 알맞는 약을 제공하고 잔여량을 측정하여 LED로 표시합니다. 슬롯의 뚜껑에는 잠금 장치가 있어 약을 추가, 삭제하는 경우가 아니면 열리지 않도록 합니다.

* 디바이스 외관

![device_out](https://user-images.githubusercontent.com/62014520/102004362-5f934900-3d53-11eb-9496-2cd1fe49198f.png)

1번~6번 슬롯에는 알약을 보관하고 7번 슬롯에는 일반 의약품을 보관합니다. 초기화 버튼으로 앱과 디바이스를 연동하고 보관 중인 슬롯의 약 잔여량을 LED로 표시합니다.

* 디바이스 내부

![device_in](https://user-images.githubusercontent.com/62014520/102004363-615d0c80-3d53-11eb-91bd-0f49384e0407.png)

릴레이 모듈로 잠금 장치인 리니어 솔레노이드를 제어하고 약 제공 시 서보 모터를 일정 속도와 각도로 작동시켜 약을 떨어뜨립니다.

![device_in2](https://user-images.githubusercontent.com/62014520/102004366-628e3980-3d53-11eb-972a-15c5161bd78a.png)

약을 추가, 삭제하는 경우에 리니어 솔레노이드가 작동하여 잠금 장치가 잠금, 해제되고 초음파 센서를 이용해 잔여량을 측정합니다.

![device_in3](https://user-images.githubusercontent.com/62014520/102004367-628e3980-3d53-11eb-850a-88fa4f486367.png)

잔여량 표시는 네오 픽셀을 이용하여 충분하면 녹색, 부족하면 빨간색, 적당하면 노란색을 표시합니다.

* 하드웨어 구성도

![hardware](https://user-images.githubusercontent.com/62014520/102005226-8acd6680-3d5a-11eb-9f93-bbba5e6f0823.png)

* 회로도

![circuit_diagram](https://user-images.githubusercontent.com/62014520/102005228-8bfe9380-3d5a-11eb-8cbd-55238ca2c759.png)



## Program Stacks
* Raspberry Pi 4
* python
* bash
