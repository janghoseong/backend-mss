package product.jpa.entity

import jakarta.persistence.*
import lombok.Data
import java.sql.Timestamp

/**
 * 브랜드 테이블
 */
@Data
@Entity
@Table(name = "brand")
class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long = 0;
    @Column(name = "brand_code", nullable = false) var brandCode: String = ""
    @Column(name = "brand_name") var brandName: String? = null
    @Column(name = "created_at") var createdAt: Timestamp? = null
    @Column(name = "created_by") var createdBy: String? = null
    @Column(name = "updated_at") var updatedAt: Timestamp? = null
    @Column(name = "updated_by") var updatedBy: String? = null
}