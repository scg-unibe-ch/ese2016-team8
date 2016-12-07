<%@page import="java.util.Iterator"%>
<%@page import="ch.unibe.ese.team8.model.Location"%>
<%@page import="java.util.List"%>
<%@page import="ch.unibe.ese.team8.controller.service.GeoDataService"%>
<%@page
	import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	ApplicationContext ac = RequestContextUtils
	.getWebApplicationContext(request);
	GeoDataService geoDataService = (GeoDataService) ac
	.getBean(GeoDataService.class);
	
	List<Location> zipCodes = geoDataService.getAllLocations();
	Iterator<Location> iterator = zipCodes.iterator();
	out.print("[");
	Location zip = iterator.next();
	out.print("\"" + zip.getZip() + " - " + zip.getCity() + "\"");
	while(iterator.hasNext()){
		zip = iterator.next();
		out.print(",\"" + zip.getZip() + " - " + zip.getCity() + "\"");
	}
	out.print("]");
%>