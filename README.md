# PingPong
TCP를 활용하여 비동기 통신을 하는 간단한 단어 주고받기 프로그램 입니다.

-목표: 클라이언트 프로그램이 서버 프로그램에 Ping을 보니면 Pong을 응답받는 프로그램 입니다.

-제약 조건
  - 클라이언트에서 보낸 메시지가 Ping 일 경우에, Pong 응답을 합니다.
  - 클라이언트에서 보낸 메시지가 Ping 이 아닐 경우, 클라이언트에서 보낸 메시지를 그대로 응답합니다.
  - 비동기 요청/응답을 수행하도록 프로그램을 작성합니다.
  - Client는 요청을 보낸 후, Server의 응답을 기다리지 않고 바로 다음 요청을 보낼 수 있습니다.
  - Server는 Client로 부터 요청을 받은 후, 5초간 기다렸다가 응답을 보냅니다.


[실행결과]
- Server
<br><br>
![ServerSide](https://github.com/ShinMinOh/PingPong/assets/74702677/a9c9b31d-ff70-4bb6-8607-d1b762fdeb2c)

<br><br>

- Client
<br><br>
![image](https://github.com/ShinMinOh/PingPong/assets/74702677/b1f3121f-8821-4f4d-a2fb-cde259b08f2b)
