<div align=center><h1>📚 Study For Code</h1></div>

**노션 페이지**   : [Spoco Notion](https://nsoh.notion.site/729c80568f09422da904648779e60d9f)

**프로젝트 문서** : [Jira Confluence](https://goormthinking.atlassian.net/wiki/spaces/kTgB3VkLX5af/overview)

**프로젝트 기간** : 24.05.28 - 24.06.25

**프로젝트 요약**<br>
Study For Code는 알고리즘 스터디를 진행하면서 어려웠던 문제의 풀이 여부, 문제 관리와 코드 리뷰 등 필요한 고충들을 보완하기 위하여 제작하게 된 WebIDE 서비스입니다.
</br></br>

<div align=center><h2>👫 Team 프로필</h2></div>

|<img src="https://avatars.githubusercontent.com/u/44081552?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/86042721?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/128238794?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/68214913?v=4" width="150" height="150"/>|
|:-:|:-:|:-:|:-:|
|BE_이호성<br/>[@githublees](https://github.com/githublees)|BE_홍진석<br/>[@Hong-JinSuk](https://github.com/Hong-JinSuk)|BE_김태우<br/>[@anothercod](https://github.com/anothercod)|BE_김현회<br/>[@hyeonhoi11](https://github.com/hyeonhoi11)|

<details>
  <summary>이호성 [BE]</summary>
  <ul>
    <li> 프로젝트 기획 및 디자인 제공 </li>
    <li> 시스템 아키텍처, ERD 설계 담당 </li>
    <li> 채팅 서버 담당 (Chat) </li>
    <li> WebMvc, Spring Security 적용 (Spoco) </li>
    <li> 코드 리뷰 관련 REST API 구현 (Spoco) </li>
    <li> 이미지 등록 및 조회 기능 구현 (Spoco) </li> 
    <li> 전체 코드 리팩토링 및 버그 수정 (Spoco) </li>
    <li> common, global 백엔드 컨벤션 작성 담당 (Spoco) </li>
    <li> GitHub, Jira 관리 담당 </li>
  </ul>
</details>

<details>
  <summary> 홍진석 [BE]</summary>
  <ul>
    <li> Architecture 구현 </li>
    <li> 채팅 서버 연결 (Chat – Backend) </li>
    <li> 카테고리 관리 기능 (Spoco – Backend) </li>
    <li> IDE 관련 기능 (Spoco – Backend) </li>
    <li> 알고리즘 관리 기능 (Spoco – Backend) </li>
    <li> 관리자 기능 (Spoco – Backend) </li>
    <li> 프로젝트 배포 (Backend) </li>
  </ul>
</details>

<details>
  <summary> 김현회 [BE]</summary>
  <ul>
    <li> API 명세서 작성</li>
    <li> 스터디 관리 기능 (Spoco – Backend) </li>
    <li> 관리자 기능 (Spoco – Backend) </li>
  </ul>
</details>

<details>
  <summary> 김태우 [BE]</summary>
  <ul>
    <li> Jira Confluence 작성 </li>
    <li> 회의록 작성 </li>
    <li> 사용자 관리 기능 (Spoco – Backend) </li>
    <li> GitHub ReadMe 작성 </li>
  </ul>
</details>
</br>

<div align=center><h2>💻 STACKS</h1></div>
<div align=center>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  </br>

  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white">
  <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white">
  <img src="https://img.shields.io/badge/STOMP-F56640?style=for-the-badge&logo=socket.io&logoColor=white">
  <img src="https://img.shields.io/badge/H2-00758F?style=for-the-badge&logo=hibernate&logoColor=white">
  <img src="https://img.shields.io/badge/mongoDB-47A248?style=for-the-badge&logo=MongoDB&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  </br>

  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white">
  <img src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jira&logoColor=white">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
</div>
</br></br>

<div align=center><h2>📊 시스템 구상도</h2></div>

### 🗒️ 디렉토리 계층

```
**[ SpocoProject ]**
├── Dockerfile
├── build.gradle
├── docker-compose.yml
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── goorm
    │   │       ├── message
    │   │       │   ├── controller
    │   │       │   │   ├── request
    │   │       │   │   └── response
    │   │       │   ├── domain
    │   │       │   ├── repository
    │   │       │   └── service
    │   │       └── spoco
    │   │           ├── domain
    │   │           │   ├── algorithm
    │   │           │   ├── auth
    │   │           │   ├── category
    │   │           │   ├── code
    │   │           │   ├── image
    │   │           │   ├── join
    │   │           │   ├── member
    │   │           │   ├── review
    │   │           │   ├── study
    │   │           │   ├── subscribe
    │   │           │   ├── testcase
    │   │           │   │   ├── controller
    │   │           │   │   │   ├── request
    │   │           │   │   │   └── response
    │   │           │   │   ├── domain
    │   │           │   │   ├── dto
    │   │           │   │   ├── repository
    │   │           │   │   └── service
    │   │           ├── global
    │   │           │   ├── common
    │   │           │   │   ├── auth
    │   │           │   │   └── response
    │   │           │   ├── config
    │   │           │   ├── error
    │   │           │   │   └── exception
    │   │           │   └── util
    │   │           ├── infra
    │   │               └── compiler
    │   │                   ├── compiler
    │   │                   ├── dto
    │   │                   └── service
    └── resources
        ├── application.properties
        └── templates
```

---

```
**[ Chat ]**
├── Dockerfile
├── build.gradle
├── docker-compose.yml
├── settings.gradle
└── src
    ├── main
       ├── java
        │   └── goorm
        │       └── chat
        │           ├── config
        │           ├── controller
        │           ├── domain
        │           ├── dto
        │           ├── repository
        │           └── service
        └── resources
            ├── application.properties
            └── templates
                └── index.html 
```

---

### 🗂️ 스포코 ERD
![스포코 ERD](https://drive.google.com/uc?export=view&id=1CZgm5Y-UtrQ_aCh8ycadnAFhYQdKz2Mr)

---

### 🏗️ 시스템 아키텍처
![스포코 Architecture](https://drive.google.com/uc?export=view&id=1ndkpqpW_Dhov7-sVhqmSmw2OqYAfoS0c)
</br></br>

## 💡 시연 영상
https://github.com/study-for-code/Backend/assets/44081552/8f4873f1-b702-498a-94df-cedc078d14cc

</br></br>

<div align=center><h2>🛠️ 개발 환경</h2></div>

- **Programming Language**: Java 17
- **Build Tool**: Gradle
- **Spring Framework**: Spring Framework : 6.1.8
  - Spring Boot : Spring Boot 3.3.0
- **Library and Dependency Management**: Spring Dependency Management Plugin 1.1.5
</br></br>

<div align=center><h2>🌟 프로젝트 버전 관리 </h2></div>

### 데이터베이스 / Database 💾
- **H2** : 2.2.224
- **MySQL** : 5.7.44 for
- **MongoDB** : 5.0.1

### 보안 / Security 🔒
- **Spring Security** : 6.3.0
- **JWT** : 0.11.5
- **Bouncy Castle Provider** : 1.69

### 유효성 검사 / Validation ✅
- **Spring-boot-starter-data-jpa** : 3.3.0
- **Spring-boot-starter-data-mongodb** : 4.3.0
- **Hibernate**
  - hibernate-core : 6.5.2.Final
  - hibernate-validator: 8.0.1.Final
  - hibernate-commons-annotations: 6.0.6.Final

### 추가 라이브러리
- **Tomcat**: 10.1.24
</br></br>
