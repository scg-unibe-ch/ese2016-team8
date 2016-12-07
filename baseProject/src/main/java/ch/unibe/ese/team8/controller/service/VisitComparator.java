package ch.unibe.ese.team8.controller.service;

import java.util.Comparator;

import ch.unibe.ese.team8.model.Visit;

public class VisitComparator implements Comparator<Visit> {

	@Override
	public int compare(Visit o1, Visit o2) {
		if(o1.getStartTimestamp().before(o2.getStartTimestamp())){
			return -1;
		}else{
			return 1;
		}
	}

}
