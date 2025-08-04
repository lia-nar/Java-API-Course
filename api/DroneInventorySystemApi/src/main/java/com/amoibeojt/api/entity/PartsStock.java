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
 * 部品在庫Entity
 * 
 * @author your name
 *
 */
@Entity
@Table(name = "parts_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartsStock {
	
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
    private Integer amount;

    @Column(name = "delete_flag", nullable = false)
    private boolean deleteFlag;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

}
