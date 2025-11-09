

# 📘 윌티켓 프로젝트

<img width="956" height="479" alt="Image" src="https://github.com/user-attachments/assets/1e0f3038-5782-4bbb-a726-22f2e54b71c4" />
## 📌 프로젝트 개요
윌티켓 프로젝트는 콘서트, 뮤지컬, 연극, 전시 등 다양한 공연 티켓을  
웹에서 빠르고 쉽게 예매할 수 있도록 구현한 예매 시스템입니다.

## 사용 기술
**Frontend**  
![HTML5](https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=black)
![JSP](https://img.shields.io/badge/JSP-blue?logo=java&logoColor=white)
![JSTL](https://img.shields.io/badge/JSTL-0769AD?logo=java&logoColor=white)
![AJAX](https://img.shields.io/badge/AJAX-0052CC?logo=javascript&logoColor=white)

**Backend**  
![Java](https://img.shields.io/badge/Java-007396?logo=openjdk&logoColor=white)
![Servlet](https://img.shields.io/badge/Servlet-4285F4?logo=java&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-000000?logo=databricks&logoColor=white)

**Database**  
![Oracle DB](https://img.shields.io/badge/Oracle-F80000?logo=oracle&logoColor=white)

**Server**  
![Tomcat](https://img.shields.io/badge/Tomcat-F8DC75?logo=apachetomcat&logoColor=black)

**IDE/Tools**  
![Eclipse](https://img.shields.io/badge/Eclipse%20IDE-2C2255?logo=eclipseide&logoColor=white)

## ✨ 주요 기능

- 공연 관리
  - 공연 등록 / 수정 / 삭제
  - 공연 일정 및 상세 정보 관리

- 예매 기능
  - 공연 좌석 선택
  - 예매 신청 및 예매 취소
  - 예매 현황 조회

- 회원 기능
  - 회원가입
  - 로그인 / 로그아웃
  - 마이페이지 예매내역 확인

- 좌석 관리
  - 좌석 중복 선택 방지
  - 선택 가능 좌석 상태 표시

- 관람평 기능
  - 공연별 관람평 작성
  - 관람평 조회 및 관리

- 검색 기능
  - 공연명 검색
  - 카테고리 또는 날짜별 공연 검색 

   ## 🗂️ ERD 
<img width="855" height="589" alt="Image" src="https://github.com/user-attachments/assets/c94d1209-1365-4c41-8805-a5c8c5128ebd" /> 

## 🖼️ 주요 화면 (Screenshots) 
<table>
  <tr>
    <td align="center">공연 조회</td>
    <td align="center">공연 선택</td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/49a18fc4-5f90-491e-8fc5-9de69a33aca7" width="550"></td>
    <td><img src="https://github.com/user-attachments/assets/ede3512b-6ed0-4145-a802-39158e8d5092" width="550"></td>
  </tr>
</table>

<br/>

<table>
  <tr>
    <td align="center">좌석 선택</td>
    <td align="center">결제 완료</td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/6d34bb81-0008-4579-8d3d-f01667b1d6b1" width="550"></td>
    <td><img src="https://github.com/user-attachments/assets/ca9614d6-4fab-4a81-afbf-11a60f136466" width="550"></td>
  </tr>
</table>

<br/>

<table>
  <tr>
    <td align="center">관람평 작성</td>
    <td align="center">관람평 조회</td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/11d7dcf7-ab89-4901-8d5c-c34c04efc96e" width="550"></td>
    <td><img src="https://github.com/user-attachments/assets/33e9b5df-e8c3-47aa-be33-389f7ec8aef4" width="550"></td>
  </tr>
</table>

<br/>


## 배운점

- **웹 프로젝트 구조의 이해와 계층 분리**
  - JSP, Servlet, DAO(Mapper) 구조로 역할을 분리하며 유지보수성과 확장성을 고려한 개발 경험

- **데이터베이스 모델링과 SQL 작성 능력 향상**
  - 공연, 좌석, 예매 테이블 설계와 관계 설정을 통해 서비스 흐름을 이해하고 검색·정렬 처리 등 데이터 중심 기능 구현 능력 강화

- **데이터 일관성의 중요성**
  - 예매 → DB 반영 → 화면 갱신 흐름에서 데이터가 어긋나면 큰 문제가 발생하기 때문에 트랜잭션 처리와 데이터 일관성의 중요성을 경험하며 안정적인 예매 기능 구현에 대한 감각을 익힘

## 아쉬운점

- **티켓팅 서비스 특성을 충분히 반영하지 못한 점**
  - 실제 예매 서비스처럼 빠르게 변화하는 좌석 상태나 동시 접속 상황을 완벽히 구현하지 못해 복잡한 예매 로직을 충분히 다루지 못한 부분이 아쉬움

- **좌석 UI 구현의 디테일 부족**
  - 좌석 배치도를 단순한 형태로 구성하여 다양한 공연장의 좌석 구조를 표현하지 못했고, 사용자 친화적인 인터랙션 요소가 부족했던 점이 아쉬움

- **공연 데이터의 다양성 부족**
  - 공연 정보, 일정, 가격, 카테고리 등 실제 서비스 수준의 데이터 구조를 설계하지 못해 확장성 면에서 제한이 있었음


   
