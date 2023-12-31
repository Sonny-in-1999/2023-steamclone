# Read Me

#### 아래는 클래스 관련 설명입니다.


### Steam

- 정의 

Steam 애플리케이션 전체를 관리하기 위해 존재

애플리케이션 전체의 일관성과 관리를 용이하게 함.



- 주요기능

1. 게임 관리: 등록된 게임들을 저장/추가/삭제할 수 있음, 특정 게임을 검색하는 것도 가능.
2. 사용자 관리: 등록된 사용자들을 저장/추가/삭제할 수 있음, 특정 사용자를 검색하는 것도 가능.
3. 게임 및 사용자 연결: Steam 애플리케이션에서 사용자가 게임을 소유하는 것을 관리하기 위해, 게임과 사용자를 연결(library), 라이브러리 관련 메서드를 통해 사용자의 게임 라이브러리 관리(게임 추가/삭제)


### User

- 정의

사용자를 나타내며 사용자와 관련된 기능을 제공하기 위해 존재

사용자 식별, 인증, 게임 라이브러리 관리, 게임 실행 등을 처리하여 사용자가 애플리케이션을 편리하게 이용할 수 있도록 함.

- 주요기능

1. 사용자 식별과 인증: loginName, password 속성을 통해 사용자를 식별하고 인증(loginName은 고유 식별자)
2. 게임 라이브러리 관리: library 속성을 통해 사용자의 게임 라이브러리를 관리.(게임 추가/삭제)
3. 게임 실행: 게임 실행 메서드를 통해 사용자는 게임을 실행할 수 있다.(해당 메서드는 특정 게임을 시작하는데 사용)



### Game

- 정의

게임을 나타내고 게임과 관련된 정보와 동작을 제공하기 위해 존재

각각의 게임을 개별적으로 나타내며, 게임고 관련된 정보를 저장하고 게임을 실행하는 기능을 제공.

이를 통해 Steam 애플리케이션에서 각각의 게임을 구분하고 관리할 수 있음.



- 주요기능

1. 게임 정보 관리: 게임 정보를 저장하고, 이를 통해 게임의 제목, 태그, 개발사, 출시일 등을 관리할 수 있음.
2. 게임 실행: 게임 실행 메서드를 통해 게임을 실행할 수 있음.(해당 메서드는 해당 게임을 실행하는데 사용. 게임 실행에 필요한 로직이나 동작을 구현하는 메서드.)




### 연관관계

Steam 클래스와 User 클래스 1:N 매핑(스팀이 유저를 가질 수 있음)

Steam 클래스와 Game 클래스 1:N 매핑(스팀이 여러 게임을 가질 수 있음)

User 클래스와 Game 클래스 1:N 매핑(유저가 여러 게임을 가질 수 있음)

