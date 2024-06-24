# Study For Code(스포코) 📚

## 프로젝트 소개 📝
Study For Code는 알고리즘 스터디에서 필요한 고충들을 보완하기 위하여 제작된 Review WebIDE로 제작되었으며,

## 기술 스택 💻

### 백엔드 ⚙️
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![H2](https://img.shields.io/badge/H2-00758F?style=for-the-badge&logo=h2&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-FF6347?style=for-the-badge&logo=lombok&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)
![Bouncy Castle](https://img.shields.io/badge/Bouncy%20Castle-336699?style=for-the-badge&logoColor=white)

### DevOps 🛠️
![AWS](https://img.shields.io/badge/Amazon%20AWS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Netlify](https://img.shields.io/badge/Netlify-00C7B7?style=for-the-badge&logo=netlify&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### 협업 도구 🧑‍💻
![Jira](https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jira&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)

## 개발 환경 / Development Environment 🛠️
- **프로그래밍 언어 / Programming Language**: Java 17
- **빌드 도구 / Build Tool**: Gradle
- **프레임워크 / Framework**: Spring Boot 3.3.0
- **라이브러리 및 종속성 관리 / Library and Dependency Management**: Spring Dependency Management Plugin 1.1.5

## 주요 기능 / Key Features 🌟

### 데이터베이스 / Database 💾
- H2 : Version 2.2.224 (2023-09-17)
- MySQL : Ver 5.7.44 for Linux on x86_64
- MongoDB : Ver. 4.4 for Linux on x86_64
- ORM: Spring Data JPA

### 보안 / Security 🔒
- Spring Security

### 유효성 검사 / Validation ✅
- Spring Boot Starter Validation

### JWT 인증 / JWT Authentication 🔑
- jjwt-api 0.11.5
- jjwt-impl 0.11.5
- jjwt-jackson 0.11.5

### 암호화 / Encryption 🔐
- Bouncy Castle Provider 1.69

## 시스템 구상도 📊
### 프로젝트 구조 🗒️
```
.
├── README.md
├── chat
│   ├── Dockerfile
│   ├── build
│   │   ├── classes
│   │   │   └── java
│   │   │       ├── main
│   │   │       │   └── goorm
│   │   │       │       └── chat
│   │   │       │           ├── config
│   │   │       │           ├── controller
│   │   │       │           ├── domain
│   │   │       │           ├── dto
│   │   │       │           ├── repository
│   │   │       │           └── service
│   │   │       └── test
│   │   │           └── goorm
│   │   │               └── chat
│   │   │                   ├── config
│   │   │                   └── service
│   │   ├── generated
│   │   │   └── sources
│   │   │       ├── annotationProcessor
│   │   │       │   └── java
│   │   │       │       └── main
│   │   │       └── headers
│   │   │           └── java
│   │   │               └── main
│   │   ├── libs
│   │   ├── reports
│   │   │   └── tests
│   │   │       └── test
│   │   ├── resolvedMainClassName
│   │   ├── resources
│   │   │   └── main
│   │   ├── test-results
│   │   │   └── test
│   │   │       └── binary
│   │   │           ├── output.bin
│   │   │           ├── output.bin.idx
│   │   │           └── results.bin
│   │   └── tmp
│   │       ├── bootJar
│   │       │   └── MANIFEST.MF
│   │       ├── compileJava
│   │       │   ├── compileTransaction
│   │       │   │   └── stash-dir
│   │       │   │       ├── Message.class.uniqueId0
│   │       │   │       ├── MessageController.class.uniqueId3
│   │       │   │       ├── MessageDto.class.uniqueId2
│   │       │   │       ├── MessageRepository.class.uniqueId5
│   │       │   │       ├── MessageService.class.uniqueId4
│   │       │   │       └── MessageStatus.class.uniqueId1
│   │       │   └── previous-compilation-data.bin
│   │       └── compileTestJava
│   │           ├── compileTransaction
│   │           │   └── stash-dir
│   │           │       └── MessageServiceTest.class.uniqueId0
│   │           └── previous-compilation-data.bin
│   ├── build.gradle
│   ├── docker-compose.yml
│   ├── gradle
│   │   └── wrapper
│   │       ├── gradle-wrapper.jar
│   │       └── gradle-wrapper.properties
│   ├── gradlew
│   ├── gradlew.bat
│   ├── settings.gradle
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── goorm
│       │   │       └── chat
│       │   │           ├── config
│       │   │           ├── controller
│       │   │           ├── domain
│       │   │           ├── dto
│       │   │           ├── repository
│       │   │           └── service
│       │   └── resources
│       │       ├── application.properties
│       │       └── templates
│       │           └── index.html
│       └── test
│           └── java
│               └── goorm
│                   └── chat
└── spoco
    ├── Dockerfile
    ├── build
    │   ├── classes
    │   │   └── java
    │   │       └── main
    │   │           └── goorm
    │   │               ├── message
    │   │               │   ├── controller
    │   │               │   │   ├── request
    │   │               │   │   └── response
    │   │               │   ├── domain
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               └── spoco
    │   │                   ├── SpocoApplication.class
    │   │                   ├── domain
    │   │                   │   ├── algorithm
    │   │                   │   │   ├── controller
    │   │                   │   │   │   ├── request
    │   │                   │   │   │   └── response
    │   │                   │   │   ├── domain
    │   │                   │   │   ├── repository
    │   │                   │   │   └── service
    │   │                   │   ├── auth
    │   │                   │   │   ├── controller
    │   │                   │   │   │   ├── request
    │   │                   │   │   │   └── response
    │   │                   │   │   └── service
    │   │                   │   ├── category
    │   │                   │   │   ├── controller
    │   │                   │   │   │   ├── request
    │   │                   │   │   │   └── response
    │   │                   │   │   ├── domain
    │   │                   │   │   ├── repository
    │   │                   │   │   └── service
    │   │                   │   ├── code
    │   │                   │   │   ├── controller
    │   │                   │   │   │   ├── request
    │   │                   │   │   │   └── response
    │   │                   │   │   ├── domain
    │   │                   │   │   ├── repository
    │   │                   │   │   └── service
    │   │                   │   ├── image
    │   │                   │   │   ├── controller
    │   │                   │   │   │   ├── request
    │   │                   │   │   │   └── response
    │   │                   │   │   ├── domain
    │   │                   │   │   ├── repository
    │   │                   │   │   └── service
    │   │                   │   ├── join
    │   │                   │   │   ├── controller
    │   │                   │   │   ├── domain
    │   │                   │   │   └── repository
    │   │                   │   ├── member
    │   │                   │   │   ├── controller
    │   │                   │   │   │   ├── request
    │   │                   │   │   │   └── response
    │   │                   │   │   ├── domain
    │   │                   │   │   ├── repository
    │   │                   │   │   └── service
    │   │                   │   ├── review
    │   │                   │   │   ├── controller
    │   │                   │   │   │   └── response
    │   │                   │   │   ├── domain
    │   │                   │   │   ├── exception
    │   │                   │   │   ├── repository
    │   │                   │   │   └── service
    │   │                   │   ├── study
    │   │                   │   │   ├── controller
    │   │                   │   │   │   ├── request
    │   │                   │   │   │   └── response
    │   │                   │   │   ├── domain
    │   │                   │   │   ├── repository
    │   │                   │   │   └── service
    │   │                   │   ├── subscribe
    │   │                   │   │   ├── controller
    │   │                   │   │   │   ├── request
    │   │                   │   │   │   └── response
    │   │                   │   │   ├── domain
    │   │                   │   │   ├── repository
    │   │                   │   │   └── service
    │   │                   │   └── testcase
    │   │                   │       ├── controller
    │   │                   │       │   ├── request
    │   │                   │       │   └── response
    │   │                   │       ├── domain
    │   │                   │       ├── dto
    │   │                   │       ├── repository
    │   │                   │       └── service
    │   │                   ├── global
    │   │                   │   ├── common
    │   │                   │   │   ├── auth
    │   │                   │   │   └── response
    │   │                   │   ├── config
    │   │                   │   ├── error
    │   │                   │   │   └── exception
    │   │                   │   └── util
    │   │                   └── infra
    │   │                       └── compiler
    │   │                           ├── compiler
    │   │                           ├── dto
    │   │                           └── service
    │   ├── generated
    │   │   └── sources
    │   │       ├── annotationProcessor
    │   │       │   └── java
    │   │       │       └── main
    │   │       └── headers
    │   │           └── java
    │   │               └── main
    │   ├── libs
    │   │   └── spoco-0.0.1-SNAPSHOT.jar
    │   ├── resolvedMainClassName
    │   ├── resources
    │   │   └── main
    │   │       ├── application.properties
    │   │       └── templates
    │   └── tmp
    │       ├── bootJar
    │       │   └── MANIFEST.MF
    │       └── compileJava
    │           ├── compileTransaction
    │           │   ├── backup-dir
    │           │   └── stash-dir
    │           └── previous-compilation-data.bin
    ├── build.gradle
    ├── docker-compose.yml
    ├── gradle
    │   └── wrapper
    │       ├── gradle-wrapper.jar
    │       └── gradle-wrapper.properties
    ├── gradlew
    ├── gradlew.bat
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
        │   │           │   │   ├── controller
        │   │           │   │   │   ├── request
        │   │           │   │   │   └── response
        │   │           │   │   ├── domain
        │   │           │   │   ├── repository
        │   │           │   │   └── service
        │   │           │   ├── auth
        │   │           │   │   ├── controller
        │   │           │   │   │   ├── request
        │   │           │   │   │   └── response
        │   │           │   │   └── service
        │   │           │   ├── category
        │   │           │   │   ├── controller
        │   │           │   │   │   ├── request
        │   │           │   │   │   └── response
        │   │           │   │   ├── domain
        │   │           │   │   ├── repository
        │   │           │   │   └── service
        │   │           │   ├── code
        │   │           │   │   ├── controller
        │   │           │   │   │   ├── request
        │   │           │   │   │   └── response
        │   │           │   │   ├── domain
        │   │           │   │   ├── repository
        │   │           │   │   └── service
        │   │           │   ├── image
        │   │           │   │   ├── controller
        │   │           │   │   │   ├── request
        │   │           │   │   │   └── response
        │   │           │   │   ├── domain
        │   │           │   │   ├── repository
        │   │           │   │   └── service
        │   │           │   ├── join
        │   │           │   │   ├── controller
        │   │           │   │   ├── domain
        │   │           │   │   └── repository
        │   │           │   ├── member
        │   │           │   │   ├── controller
        │   │           │   │   │   ├── request
        │   │           │   │   │   └── response
        │   │           │   │   ├── domain
        │   │           │   │   ├── repository
        │   │           │   │   └── service
        │   │           │   ├── review
        │   │           │   │   ├── controller
        │   │           │   │   │   └── response
        │   │           │   │   ├── domain
        │   │           │   │   │   ├── Review.java
        │   │           │   │   ├── exception
        │   │           │   │   ├── repository
        │   │           │   │   └── service
        │   │           │   ├── study
        │   │           │   │   ├── controller
        │   │           │   │   │   ├── request
        │   │           │   │   │   └── response
        │   │           │   │   ├── domain
        │   │           │   │   ├── repository
        │   │           │   │   └── service
        │   │           │   ├── subscribe
        │   │           │   │   ├── controller
        │   │           │   │   │   ├── request
        │   │           │   │   │   └── response
        │   │           │   │   ├── domain
        │   │           │   │   ├── repository
        │   │           │   │   └── service
        │   │           │   └── testcase
        │   │           │       ├── controller
        │   │           │       │   ├── request
        │   │           │       │   └── response
        │   │           │       ├── domain
        │   │           │       ├── dto
        │   │           │       ├── repository
        │   │           │       └── service
        │   │           ├── global
        │   │           │   ├── common
        │   │           │   │   ├── auth
        │   │           │   │   └── response
        │   │           │   ├── config
        │   │           │   ├── error
        │   │           │   │   └── exception
        │   │           │   └── util
        │   │           └── infra
        │   │               └── compiler
        │   │                   ├── compiler
        │   │                   ├── dto
        │   │                   └── service
        │   └── resources
        │       ├── application.properties
        │       └── templates
        └── test
            └── java
                └── goorm
                    └── spoco
```

### 스포코 ERD 🗂️
![스포코 ERD](https://drive.google.com/uc?export=view&id=1CZgm5Y-UtrQ_aCh8ycadnAFhYQdKz2Mr)

### 아키텍처 🏗️
![스포코 Architecture](https://drive.google.com/uc?export=view&id=1ndkpqpW_Dhov7-sVhqmSmw2OqYAfoS0c)

## 주요 기능 💡

### 회원관리 👤

#### 회원가입 ✉️
이메일을 사용하여 회원가입을 할 수 있다.
![이미지링크]

#### 로그인 🔓
![이미지링크]

## 팀원 목록 🧑‍🤝‍🧑

<details>
  <summary> 이호성 [BE]</summary>
  <ul>
    <li> Jira 작업 </li>
    <li> GitHub 작업 </li>
    <li> 채팅 기능 구현 (Chat – Backend) </li>
    <li> Spring Security 적용 (Spoco – Backend) </li>
    <li> 코드 리뷰 관리 기능 (Spoco – Backend) </li>
    <li> 사용자 초대 기능 (Spoco – Backend) </li>
    <li> 이미지 등록 및 조회 (Spoco – Backend) </li> 
    <li> 백엔드 코드 리팩토링 및 global 에러 처리 (Backend) </li>
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


