package ch.unibe.ese.team8.model.dao;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.VisitEnquiry;

public interface VisitEnquiryDao extends CrudRepository<VisitEnquiry, Long> {
	public Iterable<VisitEnquiry> findBySender(User sender);
	
}