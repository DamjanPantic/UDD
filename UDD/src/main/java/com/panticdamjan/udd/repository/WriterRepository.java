package com.panticdamjan.udd.repository;

import com.panticdamjan.udd.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterRepository extends JpaRepository<Writer, String> {
}
