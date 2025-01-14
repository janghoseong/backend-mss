import product.jpa.entity.Brand
import product.jpa.entity.Category

// 엔티티 리스트에서 브랜드명을 꺼내는 확장함수
fun List<Brand>.getBrandName(code: String): String {
    return this.find { it.brandCode == code }?.brandName.orEmpty()
}

// 엔티티 리스트에서 카테고리명을 꺼내는 확장함수
fun List<Category>.getCategoryName(code: String): String {
    return this.find { it.categoryCode == code }?.categoryName.orEmpty()
}

// 엔티티 리스트에서 카테고리코드를 꺼내는 확장함수
fun List<Category>.getCategoryCode(name: String): String {
    return this.find { it.categoryName == name }?.categoryCode.orEmpty()
}

/**
 * 브랜드명 or 카테고리명이 없는 경우 예외처리를 하는 것을 고려했으나, 데이터 하나의 누락으로 전체 트랜잭션을 에러처리하지 않는 것으로 생각하고 empty 처리했습니다.
 * 브랜드, 카테고리가 저장이 안 돼있는 케이스는 이보다 앞단에서 체크로직이 필요한 부분일 것 같아 상품단에서 로직이 과도하게 복잡해지지 않도록 했습니다.
 */