package com.panticdamjan.udd.repository;

import com.panticdamjan.udd.model.IndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface BookRepository extends ElasticsearchRepository<IndexUnit, String> {

}
