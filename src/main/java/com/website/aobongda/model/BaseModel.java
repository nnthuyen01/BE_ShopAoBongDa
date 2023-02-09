package com.website.aobongda.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "createdby", length = 50)
	@CreatedBy
	private String createdBy;

	@Column(name = "createdate")
	@CreatedDate
	private Date createdDate;

	@Column(name = "modifiedby", length = 50)
	@LastModifiedBy
	private String modifiedBy;

	@Column(name = "modifieddate")
	@LastModifiedDate
	private Date modifiedDate;
}
