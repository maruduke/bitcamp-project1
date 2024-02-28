## 아키텍처

<p align="center">
    <img src="../image/turn_based/3.png" width="70%">
</p>

-   다수의 Client가 서버에 접속
-   Client와 통신하며 게임에 대한 로직을 수행하며 통신하는 Server 존재
-   player와 enemy에 대한 정보를 저장하는 DB존재

## Client 역할

<p align="center">
    <img src="../image/turn_based/4.png" width="70%">
</p>

### 기능

클라이언트가 가지고 있는 기능은 크게 2가지이다.

-   클라이언트는 서버에서 실시간으로 전송된 패킷을 파싱하여 콘솔에 출력하는 역할을 한다. 패킷 정보는 이미지, 적에 대한 정보와 자신과 다른 플레이어의 정보를 포함한다.
-   클라이언트는 서버로 닉네임과 직업 설정을 최초로 입력받고 게임이 시작되면 player의 행동을 지시하는 command 정보를 자신의 턴에 입력한다.

## Server 역할

<p align="center">
    <img src="../image/turn_based/5.png" width="70%">
</p>

<p align="center">
    <img src="../image/turn_based/6.png" width="70%">
</p>

<p align="center">
    <img src="../image/turn_based/server.gif" width="70%">
</p>

## DB 역할

## Game Logic 역할
