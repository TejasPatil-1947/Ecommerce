package com.ecommerce.Repository;

import com.ecommerce.Entity.User;
import com.ecommerce.Entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long> {

    Optional<WishList> findByUser(User user);
}
