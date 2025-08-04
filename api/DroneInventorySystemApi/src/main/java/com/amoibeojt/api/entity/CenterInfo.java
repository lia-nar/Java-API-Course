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
 * センター情報Entity
 * 
 * @author your name
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "center_info")
public class CenterInfo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id")
    private Integer centerId;

    @Column(name = "center_name", length = 200, nullable = false)
    private String centerName;

    @Column(name = "post_code", length = 20)
    private String postCode;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "manager_name", length = 100)
    private String managerName;

    @Column(name = "operational_status", length = 50)
    private String operationalStatus;

    @Column(name = "max_storage_capacity")
    private int maxStorageCapacity;

    @Column(name = "current_storage_capacity")
    private int currentStorageCapacity;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "delete_flag", nullable = false)
    private Boolean deleteFlag;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;
	
}
