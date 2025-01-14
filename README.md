# 백엔드 과제

백엔드 과제 - 상품 관련 기능 구현입니다.

---
## 구현 범위

1. 구현 대상 4건에 대한 REST API(public 저장소로 돼있어서 상세 내용은 생략합니다.)
2. 브랜드, 상품 각 서비스의 기능 테스트
3. 구현 대상 시나리오의 통합 테스트

## 실행 환경

- gradle v8.2 (gradle wrapper)
- embedded-redis : 로컬에 openssl 3.x 가 필요합니다. 설치 혹은 실행 시 embedded-redis를 제외하고 실행 할 수 있습니다.

## openssl 설치(optional)
#### Mac
```
brew install openssl
```
#### Windows
```
choco install openssl
```

### 코드 빌드
1. 프로젝트 클론
   ```
   git clone https://github.com/janghoseong/backend-mss.git
   cd backend-mss
   ```

2. Gradle 빌드
   ```
   ./gradlew build
   ```

### 프로젝트 실행
1. Gradle Application 실행
```
./gradlew bootRun -Dspring.profiles.active=local
```
2. openssl이 설치되지 않은 환경인 경우 실행변수 추가
```
./gradlew bootRun -Dspring.profiles.active=local -Duse-embedded-redis=false
```

### 테스트
##### gradle 테스트 또는 직접 HTTP 요청으로 테스트를 수행합니다.
1. gradle을 통한 JUnit 테스트
```
-- 브랜드 API 테스트
./gradlew test --tests "BrandServiceTest"
-- 상품 API 테스트 
./gradlew test --tests "ProductServiceTest"
-- 통합 시나리오 테스트 
./gradlew test --tests "IntegratedTest"  
```
- openssl이 설치되지 않은 경우 실행과 마찬가지로 -Duse-embedded-redis=false 변수 추가가 필요합니다.
```
-- 브랜드 API 테스트
./gradlew test --tests "BrandServiceTest" -Duse-embedded-redis=false    
-- 상품 API 테스트  
./gradlew test --tests "ProductServiceTest" -Duse-embedded-redis=false    
-- 통합 시나리오 테스트
./gradlew test --tests "IntegratedServiceTest" -Duse-embedded-redis=false 
```
2. API 호출 테스트 (Postman, Insomnia, curl 등..)
- 프로젝트를 실행합니다.
```
./gradlew bootRun -Dspring.profiles.active=local
```
- 직접 HTTP 요청을 실행합니다.(예시는 curl 커맨드를 작성했습니다.)
```
--- 구현1) 최저가 상품 조회
curl -X GET "localhost:8080/product/lowest-price" 

--- 구현2) 단일 브랜드 최저가 상품 조회
curl -X GET "localhost:8080/product/lowest-brand-price"   

--- 구현3) 카테고리별 최저가,최고가 상품 조회
curl -X GET "localhost:8080/product/min-max-category-price/{카테고리명}"
 
--- 구현4-a) 브랜드 추가
curl -X POST "localhost:8080/brand" \
     -H "Content-Type: application/json" \
     -d '{"brandCode": "X", "brandName": "X", "requestedBy": "test"}'
     
--- 구현4-b) 브랜드 수정
curl -X PUT "localhost:8080/brand/{brandId}" \
     -H "Content-Type: application/json" \
     -d '{"brandCode": "X", "brandName": "X_수정", "requestedBy": "test"}'    
      
--- 구현4-c) 브랜드 삭제
curl -X DELETE "localhost:8080/brand/{brandId}"

--- 구현4-d) 상품 추가
curl -X POST "localhost:8080/product" \
     -H "Content-Type: application/json" \
     -d '{"productNo": "", "productName": "테스트_상품_1", "brandCode": "A", "categoryCode": "TOP", "price": 10000, "requestedBy": "test"}'
     
--- 구현4-e) 상품 수정
curl -X PUT "localhost:8080/product/{productId}" \
     -H "Content-Type: application/json" \
     -d '{"productNo": "", "productName": "테스트_상품_1_수정", "brandCode": "A", "categoryCode": "TOP", "price": 11000, "requestedBy": "test"}' 
     
--- 구현4-f) 상품 삭제
curl -X DELETE "localhost:8080/product/{productId}" 
```
---
