package com.aktog.yusuf.veteriner.repository;

import com.aktog.yusuf.veteriner.entity.Role;
import com.aktog.yusuf.veteriner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;


public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);

    @Query(
            value = "select roles from public.user as u,public.user_roles as r where u.id = r.user_id and u.username =:name ",
            nativeQuery = true)
    Set<Role> findRolesByUsername(@Param("name") String username);
}

