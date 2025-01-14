package product.jpa.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import product.jpa.entity.Brand

interface BrandRepository: JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

    fun findByBrandCode(name: String): Brand?
    fun deleteByBrandCode(brandCode: String): Long
}