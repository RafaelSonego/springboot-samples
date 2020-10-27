package com.rafaelsilva.springbootsamples.samples.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rafaelsilva.springbootsamples.samples.data.entity.CSVContentEntity;

public interface CSVContentRepository extends CrudRepository<CSVContentEntity, Integer> {

	List<CSVContentEntity> findByCsvFileName(String csvFileName);
	
}
