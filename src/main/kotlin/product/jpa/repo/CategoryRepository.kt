package product.jpa.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import product.jpa.entity.Category

interface CategoryRepository: JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    fun findCategoryCodeByCategoryName(categoryName: String): Category?
    fun findCategoryNameByCategoryCode(categoryCode: Category): Category?
}