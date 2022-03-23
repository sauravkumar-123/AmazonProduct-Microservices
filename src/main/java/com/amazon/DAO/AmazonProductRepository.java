package com.amazon.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amazon.Model.AmazonProduct;

@Repository
public interface AmazonProductRepository extends JpaRepository<AmazonProduct, String> {

	public boolean existsByProductCodeOrSerialNoAndIsActive(@Param(value = "productcode") String productcode,
			@Param(value = "serialNo") String serialNo, @Param(value = "isActive") char isActive);

	@Query(value = "SELECT p FROM AmazonProduct p WHERE p.isActive =:isActive")
	public List<AmazonProduct> findAll(@Param(value = "isActive") char isActive);

	@Query(value = "SELECT p FROM AmazonProduct p WHERE p.productCode =:productcode AND p.isActive=:isActive")
	public Optional<AmazonProduct> findByProductCodeAndIsActive(@Param(value = "productcode") String productcode,
			@Param(value = "isActive") char isActive);

	@Query(value = "SELECT p FROM AmazonProduct p WHERE ( ( p.productCode =:searchKey OR UPPER(p.productName) LIKE concat('%', UPPER(:searchKey), '%') ) OR ( UPPER(p.productbrandName) LIKE concat('%', UPPER(:searchKey), '%') OR p.modelNo =:searchKey ) OR ( p.serialNo =:searchKey ) ) AND p.isActive=:isActive")
	public List<AmazonProduct> findBySearchkey(@Param(value = "searchKey") String searchKey,
			@Param(value = "isActive") char isActive);

}
