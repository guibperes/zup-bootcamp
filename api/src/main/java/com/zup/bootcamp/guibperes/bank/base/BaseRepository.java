package com.zup.bootcamp.guibperes.bank.base;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract interface BaseRepository<ENTITY extends BaseEntity> extends JpaRepository<ENTITY, UUID> {
}
