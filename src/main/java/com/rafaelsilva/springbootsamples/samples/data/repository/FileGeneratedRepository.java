package com.rafaelsilva.springbootsamples.samples.data.repository;

import org.springframework.data.repository.CrudRepository;

import com.rafaelsilva.springbootsamples.samples.data.entity.FileGeneratedEntity;

public interface FileGeneratedRepository extends CrudRepository<FileGeneratedEntity, Integer>{

}
