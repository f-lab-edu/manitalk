# 마니톡(Manitalk)
이번엔 누가 내 비밀친구가 될까? 마니또 채팅 서비스 마니톡!

<p align="center">
    <img src="https://github.com/f-lab-edu/manitalk/assets/31975535/ea7d496f-eecf-4ee0-9c9f-74a36397cd1e" width="30%" height="30%"/>   
</p>

<br>

## 소개

모임의 구성원 모두가 다같이 그룹채팅을 즐기고,   
1:1 랜덤으로 매칭되는 마니또 채팅을 통해 그 안에서 비밀 친구를 사귈 수 있는 그룹/개별 채팅 서비스

<br>
<br>

## 사용 기술, 개발 환경
JAVA,
Spring Boot, Spring Web, Spring Websocket, Spring Data JPA/Redis/MongoDB,
MySQL, Redis, MongoDB

<br>
<br>

## 핵심 기능

### 그룹 채팅

- 그룹 채팅 시작
    - 그룹 채팅방의 이름과 비밀코드를 추가하여 방을 생성한다.
        - 그룹 채팅방의 이름은 중복될 수 없다.
    - 방을 생성한 사람이 자동으로 방장이 된다.


- 그룹 채팅방 입장
    - 이름과 비밀코드를 입력하여 그룹 채팅방에 입장한다.
    - 입장시에 닉네임을 설정한다.
        - 한번 설정한 닉네임은 변경이 불가하고, 기존 멤버들의 닉네임과 중복이 불가하다.

- 그룹 채팅 나가기
    - 마니또 채팅이 진행중이면 그룹채팅을 나갈 수 없다.
    - 방장이 그룹채팅을 나가면 남은 멤버 중의 한 사람이 방장이 된다. (랜덤)  

- 그룹 채팅 종료
    - 방장이 그룹 채팅을 종료한다.
    - 모든 멤버들이 채팅방에서 나가기 처리된다.


    
### 마니또 채팅(1:1랜덤 채팅)

- 마니또 채팅 시작
    - 방장이 마니또 채팅을 시작한다.
    - 멤버 전원이 랜덤으로 1:1 매칭 되어 새로운 채팅방이 생성된다.
        - ex) 멤버가 10명인 경우, 5개의 개별 채팅방 생성
        - 홀수 인원인 경우 한 명이 여러개의 채팅을 하게 될 수도 있다.
    - 마니또 채팅의 기간을 설정한다.
        - 최소 1일 ~ 최대 7일까지 설정할 수 있다.
    - 마니또 채팅시에 사용할 새로운 닉네임을 설정한다.
        - 그룹 채팅방에서의 자신의 닉네임과 같을 수 없다.
        - 다른 사람의 닉네임과 중복은 가능하다.
    - 마니또 채팅이 시작되면 그룹 채팅방에 마니또 채팅 시작 알림과 기간이 표시된다.


- 마니또 채팅 진행
    - 미션
        - 각각 ‘~말 듣기’ 미션이 주어진다.
        - 미션 키워드는 ‘고마워’, ‘최고야‘ 등의 칭찬의 말들로 구성한다.
        - 마니또 채팅 기간동안 미션 키워드를 가장 많이 들은 사람은 우수 마니또가 된다.

- 마니또 채팅 나가기
    - 마니또 채팅은 나가기가 불가능하다.

- 마니또 채팅 종료
    - 마니또 채팅 기간이 지나면 채팅방에서 자동으로 나가기 처리된다. (배치)
    - 미션의 우수 마니또에게는 뱃지가 수여되며, 그룹 채팅방에서 자신의 닉네임 옆에 뱃지와 개수가 표시된다.

### 회원가입 & 로그인

- Google 계정으로 인증 및 로그인을 진행한다.

<br>
<br>

## 프로토타입
<table>
    <tr>
        <td align="center">1. 메인 화면</td>
        <td align="center">2. 채팅 목록</td>
        <td align="center">3. 그룹 채팅 입장</td>
    </tr>
    <tr>
        <td align="center"><img src="https://github.com/user-attachments/assets/6bd81862-c995-46d8-ad39-783061d0e2f3"/></td>
        <td align="center"><img src="https://github.com/user-attachments/assets/42657f03-91cb-464a-8a1a-1b4e45cb03b1"/></td>
        <td align="center"><img src="https://github.com/user-attachments/assets/ffaa13a9-6ec8-4a1c-a723-ab55071a5a9f"/></td>
    </tr>
    <tr>
        <td align="center">4. 그룹 채팅</td>
        <td align="center">5. 마니또 채팅</td>
    </tr>
    <tr>
        <td align="center"><img src="https://github.com/user-attachments/assets/996af053-1c48-4101-840b-a27ce0df7441"/></td>
        <td align="center"><img src="https://github.com/user-attachments/assets/6e024705-a3dd-4f6d-aa06-07ae0d8658a9"/></td>
    </tr>
</table>

<br>
<br>

## System Design: 시스템 디자인
👉 [설계](https://github.com/f-lab-edu/manitalk/wiki/System-Design:-%EC%84%A4%EA%B3%84) <br><br>
<img src="https://github.com/user-attachments/assets/beeca749-f53e-4cee-811d-39d74ba2871e" width="50%" height="30%"/>

👉 [Database: ERD](https://github.com/f-lab-edu/manitalk/wiki/System-Design:-Database-(ERD))<br><br>
<img src="https://github.com/user-attachments/assets/e0e20e8e-a7be-4180-9b3e-b9d264d1bd8c" width="50%" height="30%"/>

<br>

## Sequence Diagram: 시퀀스 다이어그램
👉 [그룹 채팅](https://github.com/f-lab-edu/manitalk/wiki/Sequence-Diagram:-%EA%B7%B8%EB%A3%B9-%EC%B1%84%ED%8C%85)<br>
👉 [마니또 채팅](https://github.com/f-lab-edu/manitalk/wiki/Sequence-Diagram:-%EB%A7%88%EB%8B%88%EB%98%90-%EC%B1%84%ED%8C%85)<br>
👉 [메시지 수발신](https://github.com/f-lab-edu/manitalk/wiki/Sequence-Diagram:-%EB%A9%94%EC%8B%9C%EC%A7%80-%EC%88%98%EB%B0%9C%EC%8B%A0)<br><br>
<table>
    <tr>
        <td align="center"><img src="https://github.com/user-attachments/assets/443dab9d-9224-4986-913b-261e59309a3c"/></td>
        <td align="center"><img src="https://github.com/user-attachments/assets/8cb403d5-eed2-4359-9ed5-1588f23f4574"/></td>
        <td align="center"><img src="https://github.com/user-attachments/assets/d13b05b7-941b-4e7d-8028-460b9a2cf821"/></td>
    </tr>
</table>
