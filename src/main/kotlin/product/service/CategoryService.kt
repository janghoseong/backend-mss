package product.service

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import product.jpa.repo.BrandRepository
import product.jpa.repo.CategoryRepository

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val redisService: RedisService,
) {

    /**
     * 카테고리 명칭을 가져오기 위해 초기에 redis에 저장합니다.
     * 카테고리는 일반적으로 코드형태로 존재한다고 가정하고, 자주 조회가 필요한 값이므로 캐시로 저장해둡니다.
     *
     */
    @PostConstruct
    fun init() {
        val categories = categoryRepository.findAll()
        redisService.setValue("CATEGORY_CACHE", categories)
    }
}