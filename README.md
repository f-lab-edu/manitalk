# 마니톡(Manitalk)
이번엔 누가 내 비밀친구가 될까? 마니또 채팅 서비스 마니톡!

<p align="center">
    <img src="https://github.com/f-lab-edu/manitalk/assets/31975535/ea7d496f-eecf-4ee0-9c9f-74a36397cd1e" width="30%" height="30%"/>   
</p>

<br>

## 소개

모임의 구성원 모두가 다같이 그룹채팅을 즐기고,   
1:1 랜덤으로 매칭되는 마니또 채팅을 통해 그 안에서 비밀 친구를 사귈 수 있는 그룹/개별 채팅 서비스 입니다.

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


- 그룹 채팅 종료
    - 방장이 그룹 채팅을 종료한다.
    - 모든 멤버들이 채팅방에서 나가기 되고, 채팅방과 이력 모두 삭제된다.


    
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
        - 마니또 채팅 기간동안 미션 키워드를 더 많이 들은 사람은 우수 마니또가 된다.


- 마니또 채팅 종료
    - 마니또 채팅 기간이 지나면 채팅방에서 나가기 되고, 채팅방과 이력 모두 삭제된다.
    - 미션의 우수 마니또에게는 뱃지가 수여되며, 그룹 채팅방에서 자신의 닉네임 옆에 뱃지와 개수가 표시된다.

### 회원가입 & 로그인

- Google 계정으로 인증 및 로그인을 진행합니다.

<br>
<br>

## 시스템 디자인

### [Architecture]

<img src="https://github.com/f-lab-edu/manitalk/assets/31975535/a7cba500-7d17-4eb8-91e0-d4f3e691ca86" width="100%"/>

<br>
<br>
<br>
<br>

### [Database Design]

<img src="https://github.com/f-lab-edu/manitalk/assets/31975535/37419379-a4e4-40e4-bbbd-2a0f87561623" width="100%"/>

<br>
<br>
<br>
<br>

## 채팅 동작 흐름

<img src="https://github.com/f-lab-edu/manitalk/assets/31975535/c2bb0c4d-f597-4f93-8919-934b567990ed" width="100%"/>

