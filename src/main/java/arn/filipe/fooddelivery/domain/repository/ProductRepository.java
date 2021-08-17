package arn.filipe.fooddelivery.domain.repository;

import arn.filipe.fooddelivery.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CustomizedJpaRepository<Product, Long> {
}
