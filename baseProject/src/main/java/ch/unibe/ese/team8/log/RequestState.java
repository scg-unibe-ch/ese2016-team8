package ch.unibe.ese.team8.log;

public enum RequestState {
	CALLING, // The method just has been called
	SUCCESS, // successful Request
	ERROR01, // Error 1, TODO: Define what is meant by it, what causes it (i.e. invalid DB-states etc.)
	ERROR02  // Error 2, TODO: Define what it is caused by.

}
