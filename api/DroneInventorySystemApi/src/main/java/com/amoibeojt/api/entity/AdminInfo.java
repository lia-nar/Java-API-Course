package com.amoibeojt.api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理者情報Entity
 * 
 * @author your name
 *
 */

@Entity
@Table(name = "admin_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminInfo {
	
    @Id
    @Column(name = "admin_id", length = 100)
    private String adminId;

    @Column(name = "admin_name", length = 100, nullable = false)
    private String adminName;

    @Column(name = "mail", length = 320, nullable = false)
    private String mail;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "delete_flag", nullable = false)
    private Boolean deleteFlag;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;
}