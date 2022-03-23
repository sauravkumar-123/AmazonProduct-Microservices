package com.amazon.Model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

	@CreationTimestamp
	@Column(name = "Created_Date", updatable = false)
	private Timestamp createdDate;

	@UpdateTimestamp
	@Column(name = "Updated_Date", insertable = false)
	private Timestamp updatedDate;
}
