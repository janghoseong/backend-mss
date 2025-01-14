package product.service

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import product.dto.request.BrandRequestDto
import product.dto.response.BrandResponseDto
import product.dto.shared.CustomException
import product.jpa.entity.Brand
import product.jpa.repo.BrandRepository
import java.sql.Timestamp
import java.time.Instant

@Service
class BrandService(
    private val brandRepository: BrandRepository,
    private val redisService: RedisService,
    @Value("\${use-embedded-redis:false}") val useRedis: Boolean
) {

    /**
     * 브랜드 명칭을 가져오기 위해 초기에 redis에 저장합니다.
     * 브랜드는 일반적으로 코드형태로 존재한다고 가정하고, 자주 조회가 필요한 값이므로 캐시로 저장해둡니다.
     *
     */
    @PostConstruct
    fun init() {
        val brands = brandRepository.findAll()
        redisService.setValue("BRAND_CACHE", brands)
    }

    @Transactional
    fun insertBrand(brandRequestDto: BrandRequestDto): BrandResponseDto {
        val brand = Brand().apply {
            brandCode = brandRequestDto.brandCode
            brandName = brandRequestDto.brandName
            createdAt = Timestamp.from(Instant.now())
            createdBy = brandRequestDto.requestedBy
        }
        try {
            val createdBrand = brandRepository.save(brand)
            if (createdBrand.id > 0) {
                updateBrandCache() // 캐시 업데이트
                return BrandResponseDto(
                    createdBrand.id,
                    createdBrand.brandCode,
                    createdBrand.brandName,
                    "INSERT",
                    "성공"
                )
            } else {
                return BrandResponseDto(
                    createdBrand.id,
                    brandRequestDto.brandCode,
                    brandRequestDto.brandName,
                    "INSERT",
                    "실패"
                )
            }
        } catch (e: Exception) {
            throw CustomException(e.message.orEmpty(), brandRequestDto)
        }
    }

    @Transactional
    fun updateBrand(id: Long, brandRequestDto: BrandRequestDto): BrandResponseDto {
        var brand = brandRepository.findByIdOrNull(id)
        if(brand == null) {
            throw CustomException("수정 대상 브랜드 없음", id)
        }
        brand.apply {
            this.brandCode = brandRequestDto.brandCode
            this.brandName = brandRequestDto.brandName
            this.updatedAt = Timestamp.from(Instant.now())
            this.updatedBy = brandRequestDto.requestedBy
        }
        try {
            val updatedBrand = brandRepository.save(brand)
            if (updatedBrand.id > 0) {
                updateBrandCache() // 캐시 업데이트
                return BrandResponseDto(
                    updatedBrand.id,
                    updatedBrand.brandCode,
                    updatedBrand.brandName,
                    "UPDATE",
                    "성공"
                )
            } else {
                return BrandResponseDto(
                    updatedBrand.id,
                    brandRequestDto.brandCode,
                    brandRequestDto.brandName,
                    "UPDATE",
                    "실패"
                )
            }
        } catch (e: Exception) {
            throw CustomException(e.message.orEmpty(), brandRequestDto)
        }
    }

    @Transactional
    fun deleteBrand(id: Long): BrandResponseDto {
        if(brandRepository.existsById(id).not()) {
            throw CustomException("삭제 대상 브랜드가 없습니다.", id)
        }
        try {
            brandRepository.deleteById(id)
            updateBrandCache() // 캐시 업데이트
            return BrandResponseDto(
                id,
                "",
                "",
                "DELETE",
                "성공"
            )
        } catch (e: Exception) {
            throw CustomException(e.message.orEmpty(), id)
        }
    }

    private fun updateBrandCache() {
        if(useRedis) {
            val brands = brandRepository.findAll()
            redisService.setValue("BRAND_CACHE", brands)
        }
    }
}