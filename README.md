# 멀티캠퍼스 세미프로젝트 2차

### 주제 : 커넥티드 카 관제 및 데이터수집 모델 구축

### 기간 : 3/9 ~ 3/23 (2주)

### 인원 : 5명

### 프로젝트 기획 배경 및 목표

- 4차산업혁명시대의 주력 사업인 커넥티드카 시스템에서의 데이터 수집 및 분석의 중요성이 강조되고 있다.

  하지만 현재 차량들은 정보 전달 매개체로 활용되지 못하고 있으며 발생하는 데이터들이 버려지고 있는 상황이다.

  따라서 데이터 수집 모델 구축을 통해 차량 인포테인먼트 시스템의 구체적인 활용에 더하여 운전자들의 관심사에 맞는 반응형 광고뿐 아니라 교통사고, 코로나 등 사고 및 공익정보 전달에 대한 역할을 수행할 수 있도록 한다.

### 기대효과

- 향후 파이널 프로젝트를 위한 기초자료로서 사용될 수 있으며 커넥티드 카로 활용될 수 있는 모든 차량에 대해 적용가능하도록 기여할 수 있다.

  데이터 수집 모델의 구축을 통해 운전자뿐만 아닌 보행자까지 양방향의 정보 전달이 가능할 것으로 기대된다.

  다수의 ECU를 사용하여 ECU들과 협조-제어가 가능한 통합 제어를 기대할 수 있으며, 보다 더 고차원적인 관제가 가능할 것이다.

### 개발 결과

- **case 1: FCM을 활용한 ECU 제어**

  1-1 AWS에서의 Web-Server

  ![1](https://user-images.githubusercontent.com/36683607/77812289-b4b6fe00-70e3-11ea-8b84-999825e96a82.png)

  1-2 Client -> Server(Android) 접속

  ![2](https://user-images.githubusercontent.com/36683607/77812290-b680c180-70e3-11ea-988f-e9d6a751ea7d.png)

  1-3 Web-Server에서 데이터 전송

  ![3](https://user-images.githubusercontent.com/36683607/77812291-b680c180-70e3-11ea-806f-bb0c75647f6d.png)

  1-4 확인 (Server(Android), Client, ECU)

  ![4](https://user-images.githubusercontent.com/36683607/77812293-b7195800-70e3-11ea-9a59-ade6fc5c6831.png)

- **case 2: TCP/IP Server를 활용한 ECU 제어**

  2-1 Web-Server에서 데이터 전송

  ![5](https://user-images.githubusercontent.com/36683607/77812294-b7b1ee80-70e3-11ea-9c5c-a9b02fe00546.png)

  2-2 TCP/IP Server 데이터 전달

  ![6](https://user-images.githubusercontent.com/36683607/77812295-b84a8500-70e3-11ea-8922-9f6ad4242dd9.png)

  2-3 확인

  ![7](https://user-images.githubusercontent.com/36683607/77812296-b84a8500-70e3-11ea-8de7-b5e8f7cbce8c.png)

### 향후 개선사항

- 사용자 중심의 UI 디자인 필요성
- Hadoop hive를 사용한 Data IO Cluster 구축 & 수집한 데이터를 활용하여 Decision Tree 구현 및 적용
