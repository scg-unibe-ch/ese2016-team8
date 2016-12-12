package ch.unibe.ese.team8.model.dao;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.Visit;

public interface VisitDao extends CrudRepository<Visit, Long> {
	public Iterable<Visit> findByAd(Ad ad);
}