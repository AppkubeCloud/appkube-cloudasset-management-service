package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.domain.Vault;

/**
 * Spring Data SQL repository for the Vault entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VaultRepository extends JpaRepository<Vault, Long> {}
