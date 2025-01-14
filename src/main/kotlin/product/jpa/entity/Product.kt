package product.jpa.entity

import jakarta.persistence.*
import lombok.Data
import java.sql.Timestamp

/**
 * 상품 테이블
 */
@Data
@Entity
@Table(name = "product")
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long = 0;
    @Column(name = "product_no", nullable = false) var productNo: String = ""
    @Column(name = "product_name") var productName: String? = null
    @Column(name = "brand_code", nullable = false) var brandCode: String = ""
    @Column(name = "category_code", nullable = false) var categoryCode: String = ""
    @Column(name = "price", nullable = false) var price: Long = -1  // 필수값으로 처리하되 가격이 셋팅되지 않는 경우를 찾기 위해 초기값은 -1로 설정
    @Column(name = "created_at") var createdAt: Timestamp? = null
    @Column(name = "created_by") var createdBy: String? = null
    @Column(name = "updated_at") var updatedAt: Timestamp? = null
    @Column(name = "updated_by") var updatedBy: String? = null
}