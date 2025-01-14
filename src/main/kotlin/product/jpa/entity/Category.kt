package product.jpa.entity

import jakarta.persistence.*
import lombok.Data
import java.sql.Timestamp

/**
 * 카테고리 테이블
 */
@Data
@Entity
@Table(name = "category")
class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long = 0;
    @Column(name = "category_code", nullable = false) var categoryCode: String = ""
    @Column(name = "category_name") var categoryName: String? = null
    @Column(name = "created_at") var createdAt: Timestamp? = null
    @Column(name = "created_by") var createdBy: String? = null
}