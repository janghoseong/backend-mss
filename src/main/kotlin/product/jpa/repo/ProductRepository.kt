package product.jpa.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import product.jpa.entity.Product

interface ProductRepository: JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "SELECT p.* " +
            "FROM product p INNER JOIN ( " +
            "    SELECT category_code, MIN(price) AS min_price " +
            "    FROM product " +
            "    GROUP BY category_code " +
            ") sub " +
            "ON p.category_code = sub.category_code AND p.price = sub.min_price", nativeQuery = true)
    fun findLowestPriceProductsByCategoryCodes(): List<Product>

    @Query(value = "select p " +
            "from Product p " +
            "where p.brandCode = (select p2.brandCode from Product p2 " +
            "group by p2.brandCode " +
            "order by sum(p2.price) asc, p2.brandCode " +
            "limit 1 )")
    fun findLowestPriceProductsBySingleBrand(): List<Product>

    @Query(value = "select p " +
            "from Product p " +
            "where p.categoryCode = :category " +
            "and p.price = (select p2.price from Product p2 where p2.categoryCode = :category " +
            "order by p2.price asc " +
            "limit 1)")
    fun findMinPriceProductByCategory(@Param("category") category: String): List<Product>

    @Query(value = "select p " +
            "from Product p " +
            "where p.categoryCode = :category " +
            "and p.price = (select p2.price from Product p2 where p2.categoryCode = :category " +
            "order by p2.price desc " +
            "limit 1)")
    fun findMaxPriceProductByCategory(@Param("category") category: String): List<Product>

    fun findByProductNo(productNo: String): Product?

    @Query(value = "select p from Product p where p.brandCode = :brandCode and p.categoryCode = :categoryCode " +
            "order by p.id desc limit 1")
    fun findLastByBrandCodeAndCategoryCode(brandCode: String, categoryCode: String): Product?
}