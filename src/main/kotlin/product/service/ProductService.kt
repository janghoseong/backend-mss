package product.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import getBrandName
import getCategoryCode
import getCategoryName
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import product.dto.request.ProductRequestDto
import product.dto.response.CategoryMinMaxPriceResponseDto
import product.dto.response.LowestPriceResponseDto
import product.dto.response.ProductResponseDto
import product.dto.response.SingleBrandLowestPriceResponseDto
import product.dto.shared.CustomException
import product.jpa.entity.Brand
import product.jpa.entity.Category
import product.jpa.entity.Product
import product.jpa.repo.BrandRepository
import product.jpa.repo.CategoryRepository
import product.jpa.repo.ProductRepository
import java.sql.Timestamp
import java.time.Instant

@Service
class ProductService(
    private val redisService: RedisService,
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    @Value("\${use-embedded-redis:false}") val useRedis: Boolean
) {

    val objectMapper = ObjectMapper()

    /**
     * 구현1 - 카테고리별 최저가 상품 조회
     * 브랜드와 카테고리 정보를 코드성 데이터로 다뤘기 때문에 캐시에서 꺼내서 변환하는 처리를 합니다.
     * 카테고리별 최저가 상품을 담고 최저가 상품이 여러건 나오는 경우 브랜드가 적게 나온 쪽을 선택합니다.(API 응답 샘플과 맞는 기준)
     * 금액은 포맷팅을 위해 String으로 처리합니다.
     */
    fun getLowestPriceProducts(): LowestPriceResponseDto {
        // 캐시에서 브랜드와 카테고리 정보를 가져온다
        var brands = fetchBrandCache()
        var categories = fetchCategoryCache()

        /**
         * DB쿼리로 최저가 목록을 가져온다.
         * nativeQuery를 써서 쿼리조회를 한 이유는 상품 데이터는 예제와 같이 적은 케이스는 실환경에서는 거의 없을 것이고,
         * DB와의 통신을 줄이는 것이 성능상 유리하다고 판단해서입니다.
         */
        //
        val products = productRepository.findLowestPriceProductsByCategoryCodes().toMutableList();
        if(products.isNotEmpty()) {
            try {
                // 리턴할 상품 리스트
                val productList = mutableListOf<Product>()
                // 최저가 상세 리스트
                val detailList = mutableListOf<LowestPriceResponseDto.LowestPriceDetail>()

                // 정렬은 카테고리 ID 순서대로 합니다.
                products.sortBy { product -> categories.find { category -> category.categoryCode == product.categoryCode }?.id }

                /**
                 * 동일 카테고리에 최저가 상품이 2개 이상인 경우
                 * 목록안에 수가 가장 적은 브랜드를 우선 선택한다
                 */
                val brandCount = mutableMapOf<String, Int>()
                val productBrandMap = products.groupBy { it.brandCode }
                // 브랜드별 등장 횟수 저장
                for(brand in productBrandMap) {
                    brandCount[brand.key] = brand.value.size
                }
                val productCategoryMap = products.groupBy { it.categoryCode }
                // 카테고리별 리스트 저장
                for(category in productCategoryMap) {
                    var selectedProduct = Product()
                    if(category.value.size > 1) {
                        // 브랜드 카운트에서 가장 적게 나온 브랜드를 선택, null 이면 그냥 첫번째걸로 선택
                        selectedProduct = category.value.find { c ->
                            c.brandCode == brandCount.filter { brand ->
                                category.value.map { it.brandCode }.contains(brand.key) }.minByOrNull { it.value }?.key
                        } ?: category.value.first()
                    } else if(category.value.size == 1) { // 카테고리 내에 하나뿐이면 그냥 저장
                        selectedProduct = category.value.first()
                    }
                    productList.add(selectedProduct) // 상품리스트에 담는다
                }
                for (product in productList) {
                    val lowestPrice = LowestPriceResponseDto.LowestPriceDetail(
                        categories.getCategoryName(product.categoryCode),
                        brands.getBrandName(product.brandCode),
                        String.format("%,d", product.price),
                    )
                    detailList.add(lowestPrice)
                }

                // 최종 리턴 객체
                return LowestPriceResponseDto(
                    detailList,
                    String.format("%,d", productList.sumOf { it.price })
                )
            } catch (e: Exception) {
                throw CustomException("상품 정보 목록 변환에 실패했습니다.\n ${e.message}", products)
            }
        } else {
            throw CustomException("등록된 상품이 없습니다.", null)
        }
    }

    /**
     * 구현2 - 단일 브랜드 최저가 상품 목록 조회
     * 브랜드와 카테고리 정보를 코드성 데이터로 다뤘기 때문에 캐시에서 꺼내서 변환하는 처리를 합니다.
     * 최저가 브랜드가 2개 이상인 경우 브랜드코드 순서대로 첫번째 브랜드를 선택합니다.
     * 금액은 포맷팅을 위해 String으로 처리합니다.
     */
    fun getLowestBrandPriceProduct(): SingleBrandLowestPriceResponseDto {
        // 캐시에서 브랜드와 카테고리 정보를 가져온다
        var brands = fetchBrandCache()
        var categories = fetchCategoryCache()

        // DB쿼리로 단일 브랜드 최저가 목록을 가져온다.
        val products = productRepository.findLowestPriceProductsBySingleBrand().toMutableList();
        if(products.isNotEmpty()) {
            try {
                // 최저가 상세 리스트
                val detailList = mutableListOf<SingleBrandLowestPriceResponseDto.SingleBrandLowestPriceDetail.SingleBrandLowestPriceList>()

                // 정렬은 카테고리 ID 순서대로 합니다.
                products.sortBy { product -> categories.find { category -> category.categoryCode == product.categoryCode }?.id }

                // 리스트 저장
                for (product in products) {
                    val price = SingleBrandLowestPriceResponseDto.SingleBrandLowestPriceDetail.SingleBrandLowestPriceList(
                        categories.getCategoryName(product.categoryCode),
                        String.format("%,d", product.price),
                    )
                    detailList.add(price)
                }

                // 최종 리턴 객체
                return SingleBrandLowestPriceResponseDto(
                    SingleBrandLowestPriceResponseDto.SingleBrandLowestPriceDetail(
                        brands.getBrandName(products.first().brandCode),
                        detailList,
                        String.format("%,d", products.sumOf { it.price })
                    )
                )
            } catch (e: Exception) {
                throw CustomException("상품 정보 목록 변환에 실패했습니다.\n ${e.message}", products)
            }
        } else {
            throw CustomException("등록된 상품이 없습니다.", null)
        }
    }

    /**
     * 구현3 - 카테고리 이름으로 최저,최고 가격 상품 조회
     * 브랜드와 카테고리 정보를 코드성 데이터로 다뤘기 때문에 캐시에서 꺼내서 변환하는 처리를 합니다.
     * 최저가, 최고가는 리스트입니다. 동일한 가격이 있을 경우 여러건이 반환될 수 있습니다.(배열)
     * 금액은 포맷팅을 위해 String으로 처리합니다.
     */
    fun getMinMaxPriceProductByCategory(categoryName: String): CategoryMinMaxPriceResponseDto {
        // 캐시에서 브랜드와 카테고리 정보를 가져온다
        var brands = fetchBrandCache()
        var categories = fetchCategoryCache()

        // DB쿼리로 최저, 최고가 목록을 가져온다.
        try {
            val categoryCode = categories.getCategoryCode(categoryName)
            val minPriceProductList = productRepository.findMinPriceProductByCategory(categoryCode)  // 최저가 조회
            val maxPriceProductList = productRepository.findMaxPriceProductByCategory(categoryCode)  // 최고가 조회
            return CategoryMinMaxPriceResponseDto(
                categoryName,
                minPriceProductList.map {
                    CategoryMinMaxPriceResponseDto.CategoryPriceDetail(
                        brands.getBrandName(it.brandCode),
                        String.format("%,d", it.price)
                    )
                },
                maxPriceProductList.map {
                    CategoryMinMaxPriceResponseDto.CategoryPriceDetail(
                        brands.getBrandName(it.brandCode),
                        String.format("%,d", it.price)
                    )
                }
            )
        } catch (e: Exception) {
            throw CustomException("최저, 최고가 목록 조회 실패. ${e.message}", categoryName)
        }
    }

    /**
     * 구현4 - 상품 추가
     * 상품번호(product_no)를 unique한 조건으로 key처럼 쓸까 했는데 ID가 있어서 우선은 참조성 데이터로만 다뤘습니다.
     * 상품번호는 "브랜드_카테고리_순번" 의 규칙으로 자동 생성하는 형태로 했습니다.
     */
    @Transactional
    fun insertProduct(productRequestDto: ProductRequestDto): ProductResponseDto {
        // 추가하려는 브랜드 & 카테고리의 마지막 등록된 상품을 가져온다(product_no 생성을 위해)
        val lastProduct = productRepository.findLastByBrandCodeAndCategoryCode(productRequestDto.brandCode, productRequestDto.categoryCode)
        val currentSeq = lastProduct?.productNo?.substringAfterLast("_") ?: "0"
        val nextSeq = Integer.valueOf(currentSeq) + 1
        val nextProductNo = "${productRequestDto.brandCode}_${productRequestDto.categoryCode}_$nextSeq"

        val product = Product().apply {
            productNo = nextProductNo
            productName = productRequestDto.productName
            brandCode = productRequestDto.brandCode
            categoryCode = productRequestDto.categoryCode
            price = productRequestDto.price
            createdAt = Timestamp.from(Instant.now())
            createdBy = productRequestDto.requestedBy
        }
        try {
            val createdProduct = productRepository.save(product)
            if (createdProduct.id > 0) {
                return ProductResponseDto(
                    createdProduct.id,
                    createdProduct.productNo,
                    createdProduct.productName,
                    createdProduct.brandCode,
                    createdProduct.categoryCode,
                    createdProduct.price,
                    "INSERT",
                    "성공"
                )
            } else {
                return ProductResponseDto(
                    -1,
                    "",
                    productRequestDto.productName,
                    productRequestDto.brandCode,
                    productRequestDto.categoryCode,
                    productRequestDto.price,
                    "INSERT",
                    "실패"
                )
            }
        } catch (e: Exception) {
            throw CustomException("상품 생성 쿼리 에러", productRequestDto)
        }
    }

    @Transactional
    fun updateProduct(id: Long, productRequestDto: ProductRequestDto): ProductResponseDto {
        var product = productRepository.findByIdOrNull(id)
        // 대상이 없는 경우
        if(product == null) {
            throw CustomException("수정 대상 상품 없음", id)
        }
        product.apply {
            this.productName = productRequestDto.productName
            this.brandCode = productRequestDto.brandCode
            this.categoryCode = productRequestDto.categoryCode
            this.price = productRequestDto.price
            this.updatedAt = Timestamp.from(Instant.now())
            this.updatedBy = productRequestDto.requestedBy
        }
        try {
            val updatedProduct = productRepository.save(product)
            if (updatedProduct.id > 0) {
                return ProductResponseDto(
                    updatedProduct.id,
                    updatedProduct.productNo,
                    updatedProduct.productName,
                    updatedProduct.brandCode,
                    updatedProduct.categoryCode,
                    updatedProduct.price,
                    "UPDATE",
                    "성공"
                )
            } else {
                return ProductResponseDto(
                    id,
                    productRequestDto.productNo,
                    productRequestDto.productName,
                    productRequestDto.brandCode,
                    productRequestDto.categoryCode,
                    productRequestDto.price,
                    "UPDATE",
                    "실패"
                )
            }
        } catch (e: Exception) {
            throw CustomException("상품 수정 쿼리 에러", id)
        }
    }

    @Transactional
    fun deleteProduct(id: Long): ProductResponseDto {
        if(productRepository.existsById(id).not()) {
            throw CustomException("삭제 대상 상품 없음", id)
        }
        try {
            productRepository.deleteById(id)
            return ProductResponseDto(
                id,
                "",
                "",
                "",
                "",
                0,
                "DELETE",
                "성공"
            )
        } catch (e: Exception) {
            throw CustomException("상품 삭제 쿼리 에러", id)
        }
    }

    // 캐시에서 브랜드 정보를 가져온다
    fun fetchBrandCache(): List<Brand> {
        var brands: List<Brand>? = null
        try {
            if(useRedis) {
                brands = objectMapper.convertValue(redisService.getValue("BRAND_CACHE"), object : TypeReference<List<Brand>>() {})
            }
        } catch (e: Exception) {
            throw CustomException("브랜드 정보를 가져오는데 실패했습니다.", null)
        }

        // 캐시에 없으면 DB에서 조회
        if(brands.isNullOrEmpty()) {
            brands = brandRepository.findAll()
        }

        return brands
    }

    // 캐시에서 카테고리 정보를 가져온다
    fun fetchCategoryCache(): List<Category> {
        var categories: List<Category>? = null
        try {
            if(useRedis) {
                categories = objectMapper.convertValue(redisService.getValue("CATEGORY_CACHE"), object : TypeReference<List<Category>>() {})
            }
        } catch (e: Exception) {
            throw CustomException("카테고리 정보를 가져오는데 실패했습니다.", null)
        }

        // 캐시에 없으면 DB에서 조회
        if(categories.isNullOrEmpty()) {
            categories = categoryRepository.findAll()
        }

        return categories
    }


}