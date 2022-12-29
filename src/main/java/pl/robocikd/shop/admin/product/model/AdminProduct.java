package pl.robocikd.shop.admin.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private String fullDescription;
    private BigDecimal price;
    private BigDecimal salePrice;
    @Enumerated(EnumType.STRING)
    private AdminProductCurrency currency;
    private String image;
    private String slug;
}
