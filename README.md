# LetMeSub
 구독 서비스추천, 조회, 비교 서비스
 <img width="1423" alt="스크린샷 2021-11-03 오후 6 11 17" src="https://user-images.githubusercontent.com/39581366/140039286-8c04473a-3f73-4f3a-b01d-6cfdc5e655cc.png">

## Description
국내외 수많은 구독 서비스들이 존재하고 있고, 다양한 서비스를 제공하고 있습니다.LET ME SUB은 구독 서비스 추천을 위해 사용자에게 다양한 질문을 하고 사용자가 원하는 가장 적합한 구독 서비스를 추천해 줍니다. 또한 카테고리별로 구독 서비스를 정리해 사용자가 원하는 분야의 구독 서비스들의 정보를 리스트로 제공하고 상세한 정보와 평점을 보여줍니다. 마지막으로 사용자가 원하는 두 구독 서비스를 비교하는 페이지를 제공하고 다른 사용자들은 비교하는 두 구독 서비스 중 어떤 걸 더 선호하는지 알려줍니다. 

#### 1) 회원기능
- 사용자는 제공하는 서비스를 사용하기 위해서 회원가입과 로그인을 한다.

#### 2) 구독 서비스 상세 조회
- 사용자가 메인화면에 출력된 구독 서비스를 클릭하면 상세페이지로 이동한다.
- 사용자는 상세 페이지에서 해당 구독 서비스의 설명, 리뷰를 볼 수 있고, 리뷰를작성할 수 있다.
- 서버는 사용자의 요청에 따라 데이터베이스와 통신하여 작성된 리뷰를 보여주고 사용자에 의해 작성된 리뷰는 데이터베이스에 입력된다.
- 사용자가 메인화면에서 추천하기 버튼을 클릭하면 추천페이지로 이동한다.

#### 3) 구독 서비스 추천 
- 사용자는 추천페이지에서 여러 요소의 질문에 따라 개인의 선호도를 선택하고 적합한 구독 서비스를 추천받는다.
- 서버는 사용자가 선택한 선호도를 가중치 알고리즘에 의해 계산하여 가장 적합한 구독 서비스를 추천 해준다.

#### 4) 구독 서비스 비교 
- 사용자가 메인화면에서 나열된 구독 서비스 중 두 가지를 선택하고 비교하기 버튼을 클릭하면 비교하기 페이지로 넘어간다.
- 사용자는 비교 페이지에서 각 구독 서비스의 설명과 UI를 볼 수 있고, 두 구독서비스의 6가지 요소 중 강점을 육각형 형태로 볼 수 있다.
- 사용자가 비교 페이지에서 둘 중 하나를 선택하도록 해서 데이터베이스에 저장하고 선택한 결과를 반영하여, 유저 선호도를 볼 수 있다.

## Installation

### 1. 프로젝트 불러오기 

```sh
git clone https://github.com/LetMeSub/LetMeSub.git
```

### 2. /main/resource 에 application.properties 파일 생성후 환경값 입력 (DB 연결 설정)

```sh
# 해당 내용을 application.properties 에 입력해 주세요.
server.port=8080
# mysql-driver connector
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# DB  LET ME SUB
spring.datasource.jdbc-url=jdbc:mysql://["mysql_host주소"]:3306/["DB 명"]?serverTimeZone=UTC&allowpublickeyRetrieval=true&CharacterEncoding=UTF-8
# user
spring.datasource.username=["DB User"]
# password
spring.datasource.password=["DB PassWord"]
```

### 3. MySQL DB 생성 & Table 생성 

#### 1. LetMeSub Database 생성
```sh
CREATE DATABASE letmesub;
```
#### 2. LetMeSub Table 생성

- subscribe Table 생성
```sh
CREATE TABLE subscribe (
  `subscribe_name` char(20) NOT NULL,
  `subscribe_describe` varchar(1000) DEFAULT NULL,
  `subscribe_count` int DEFAULT NULL,
  `subscribe_category` char(20) DEFAULT NULL,
  `subscribe_weight` int DEFAULT NULL,
  `subsribe_rate` int DEFAULT NULL,
  PRIMARY KEY (`subscribe_name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

- user Table 생성
```sh
CREATE TABLE `letmesub`.`user` (
  `user_id` char(20) NOT NULL,
  `user_pw` char(20) NOT NULL,
  `user_email` char(20) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

- comment Table 생성
```sh
CREATE TABLE comment (
  `comment_num` int NOT NULL AUTO_INCREMENT,
  `comment_contents` char(40) NOT NULL,
  `user_id` char(20) NOT NULL,
  `subscribe_name` char(20) DEFAULT NULL,
  PRIMARY KEY (`comment_num`),
  KEY `user_id` (`user_id`),
  KEY `subscribe_name` (`subscribe_name`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY(`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY(`subscribe_name`) REFERENCES `subscribe` (`subscribe_name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

```

### 4. Gradle Build
```sh
 # /LetMeSub 디렉터리에서 명령어 실행 
 ./gradlew build
 ```

### 5. Jar 파일 실행
```sh
 # ~/LetMeSub/build/libs 디렉터리에서 Jar 파일 실행
 java -jar ["빌드된 jar 파일"] 
```

### 6. insert.txt에 있는 쿼리스크립트 실행
```sh
insert.txt 의 쿼리문을 DBMS 에서 실행
```


# Environment
