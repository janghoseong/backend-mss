package product.dto.response

/**
 * response dto는 중첩클래스로 구성했습니다. 변수명을 한글로 하는게 맞나싶긴 하지만, api 스펙상 응답필드명이 한글로 돼있고 불가능할건 없으니 일단 한글로 설정합니다.
 */
data class SingleBrandLowestPriceResponseDto(
    val 최저가: SingleBrandLowestPriceDetail
) {
    data class SingleBrandLowestPriceDetail(
        val 브랜드: String,
        val 카테고리: List<SingleBrandLowestPriceList>,
        val 총액 : String
    ) {
        data class SingleBrandLowestPriceList(
            val 카테고리: String,
            val 가격: String,
        )
    }
}
