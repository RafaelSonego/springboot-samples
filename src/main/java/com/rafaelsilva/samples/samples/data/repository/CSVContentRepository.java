package com.rafaelsilva.samples.samples.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rafaelsilva.samples.samples.data.entity.CSVContentEntity;

public interface CSVContentRepository extends CrudRepository<CSVContentEntity, Integer> {

	List<CSVContentEntity> findByFileName(String fileName);
	
}
