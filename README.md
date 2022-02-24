### 항해99 5기 클론코딩
# 마켓컬리 클론하기

* 기간 : 2022년 2월 18일 ~ 24일
* 참여인원
  * 프론트엔드 : 오예준, 최종현
  * 백엔드 : 고혜지, 최규원, 김가은
* 프론트엔드 : https://github.com/oagree0123/MarketKulry_clone

## 사용 기술(백엔드)
* Spring boot
* JPA
* Spring security
* H2
* MySql
* AWS
* Git Hub
* S3

## 주요 기능
* 전체 상품 조회하기, 세부 상품 조회하기
* 장바구니 담기, 수정, 삭제하기
* 장바구니에 있는 상품 주문하기
* (포토)리뷰 작성하기
* 이미지 파일 S3에 저장, 댓글 삭제 시 S3의 이미지도 삭제

## Issue
*  LazyInitializationException 발생 → fetchType을 EAGER로 바꾸거나, repository를 통해 조회
*  MySql 연결 후 에러 발생 → 칼럼에 예약어 desc가 사용되서 변수명 변경
