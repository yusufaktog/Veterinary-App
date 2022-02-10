package com.aktog.yusuf.veteriner.repository;

import com.aktog.yusuf.veteriner.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal,String> {

}
