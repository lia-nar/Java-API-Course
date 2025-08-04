package com.amoibeojt.api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 製品在庫Entity
 * 
 * @author your name
 *
 */
@Entity
@Table(name = "products_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsStock {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Integer stockId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "center_id", nullable = false)
    private Integer centerId;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "delete_flag", nullable = false)
    private Boolean deleteFlag;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

}
