package com.supermarket.api.exception;



public class NullObjectException extends RuntimeException {
	
	private String entityName;

	public NullObjectException() {
		super("NullObject Exception occured");
		// TODO Auto-generated constructor stub
	}

	public NullObjectException(String entityName) {
		super(entityName + "is null");
		this.entityName = entityName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	

}
