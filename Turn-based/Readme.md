## 아키텍처

<p align="center">
    <img src="../image/turn_based/3.png" width="70%">
</p>

-   다수의 Client가 Server에 접속
-   Server는 Client와 통신하며 턴제 게임 로직을 수행하는 Thread와 통신
-   DB는 player와 monster에 대한 기본적인 정보를 저장함

## Client 역할

<p align="center">
    <img src="../image/turn_based/4.png" width="70%">
</p>

### 기능

클라이언트가 가지고 있는 기능은 크게 2가지이다.

1.  클라이언트는 서버에서 `실시간으로 전송된 패킷을 파싱하여 콘솔에 출력`한다. 패킷의 내부의 데이터는 플레이어나 적의 이미지와 hp등의 정보를 가지고 있다.

2.  클라이언트는 서버로 닉네임과 직업 설정을 최초로 입력받고 게임이 시작되면 player 캐릭터의 행동을 지시하는 Command 정보를 자신의 턴에 입력한다.

## DB 역할

![view](../image/turn_based/DB_1.png)

### Player Table

해당 테이블에는 플레이어가 선택한 직업의 기본 hp,mp, 공방, 플레이어 이미지와 기술명 등이 저장되어 있다.

| data                | dataType | description       |
| ------------------- | -------- | ----------------- |
| Id                  | number   | 구별자            |
| position            | varchar  | 직업 Ex) Warrior  |
| max_hp              | number   | hp 총량           |
| max_pp              | number   | MP 총량           |
| basic_attack_point  | number   | 공격력            |
| basic_defence_point | number   | 방어력            |
| image               | varchar  | 아스키코드 이미지 |
| tech1               | varchar  | 기술명1           |
| tech2               | varchar  | 기술명2           |
| tech3               | varchar  | 기술명3           |
| tech4               | varchar  | 기술명4           |

### Enemy Table

Enemy 테이블의 경우 전반적으로 Player테이블과 동일하나 직업을 나타내는 Position이 없고 몬스터의 이름을 나타내는 name이 존재한다.

| data                | dataType | description       |
| ------------------- | -------- | ----------------- |
| Id                  | number   | 구별자            |
| name                | varchar  | 몬스터 이름       |
| max_hp              | number   | hp 총량           |
| max_pp              | number   | MP 총량           |
| basic_attack_point  | number   | 공격력            |
| basic_defence_point | number   | 방어력            |
| image               | varchar  | 아스키코드 이미지 |
| tech1               | varchar  | 기술명1           |
| tech2               | varchar  | 기술명2           |
| tech3               | varchar  | 기술명3           |
| tech4               | varchar  | 기술명4           |

## Server 역할

<p align="center">
    <img src="../image/turn_based/5.png" width="70%">
</p>

-   서버는 DB에서 데이터를 받아와 Stat 클래스 내의 필드 값에 전부 저장된다.
-   해당 데이터는 3계층의 추상 클래스로 구성되어 있다. 최상위 추상 클래스인 Character에 DB에서 가져온 `캐릭터의 기본적인 정보`와 상속받을 player와 enemy의 `공통된 메서드(데미지 처리, 회복, 강화 등...)`가 정의되어 있다.

<p align="center">
    <img src="../image/turn_based/6.png" width="70%">
</p>

<p align="center">
    <img src="../image/turn_based/server.gif" width="70%">
</p>

## Game Logic 역할
