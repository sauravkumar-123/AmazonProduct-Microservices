package com.amazon.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "AmazonProduct")
public class AmazonProduct extends BaseEntity {

	@Id
	@NotBlank(message = "Invalid ProductCode")
	@NotNull(message = "Productcode shouldn't be null")
	@Size(min = 5, max = 20, message = "min 5 and max 20 character allowed for Productcode")
	@Column(name = "productCode", length = 20)
	private String productCode;

	@NotBlank(message = "Invalid ProductName")
	@NotNull(message = "ProductName shouldn't be null")
	@Size(min = 5, max = 100, message = "min 5 and max 100 character allowed for ProductName")
	@Column(name = "productName", length = 100)
	private String productName;

	@NotBlank(message = "Invalid BrandName")
	@NotNull(message = "BrandName shouldn't be null")
	@Size(min = 5, max = 70, message = "min 5 and max 70 character allowed for BrandName")
	@Column(name = "productbrandName", length = 70)
	private String productbrandName;

	@NotBlank(message = "Invalid ModelNo")
	@NotNull(message = "ModelNo shouldn't be null")
	@Size(min = 10, max = 10, message = "Only 10 character allowed for ModelNo")
	@Column(name = "modelNo", length = 10)
	private String modelNo;

	@NotBlank(message = "Invalid serialNo")
	@NotNull(message = "serialNo shouldn't be null")
	@Size(min = 15, max = 15, message = "Only 15 character allowed for serialNo")
	@Column(name = "serialNo", length = 15)
	private String serialNo;

	@NotNull(message = "TotalStock shouldn't be null")
	@PositiveOrZero(message = "Invalid Quantity")
	@Column(name = "TotalStock")
	private Integer totalStock;

	@NotNull(message = "AvaliableStock shouldn't be null")
	@Positive(message = "Invalid Quantity")
	@Column(name = "AvaliableStock")
	private Integer avaliableStock;

	@Column(name = "IsActive")
	private char isActive;

	@NotNull(message = "Manufactuer Date shouldn't be null")
	// @Temporal(TemporalType.DATE)
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "ManufactureDate")
	private Date MfgDate;

	@NotNull(message = "Expire Date shouldn't be null")
	// @Temporal(TemporalType.DATE)
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "ExpireDate")
	private Date expDate;

	@NotNull(message = "Price Details shouldn't be null")
	@Digits(integer = 6, fraction = 2, message = "Invalid Price Details")
	@Column(name = "ProductPrice")
	private Double productprice;

	@NotNull(message = "Amazon Price Details shouldn't be null")
	@Digits(integer = 6, fraction = 2, message = "Invalid Amazon Price Details")
	@Column(name = "AmazonProductPrice")
	private Double amazonProductprice;

	@NotBlank(message = "Invalid Description")
	@NotNull(message = "Description shouldn't be null")
	@Size(min = 15, max = 200, message = "Min 15 Or Max 200 character are allowed")
	@Column(name = "Description", length = 200)
	private String description;

}
