package br.com.cs.desafio.validators;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
	
	public static final int ERROR = 0;
	public static final int SUCCESS = 1;
	
	private int status;

	
	private T result;
	
	private List<T> resultList;
	
	
	/**
	 * Lista que representa os erros de validações dos objetos  
	 **/
	private List< Validator > validators;

	
	
	public Result (){
        this.status = SUCCESS;
	}
	public Result ( T result ){
		this.status = SUCCESS;
		this.result = result;
	}
	
	public Result ( List<T> result ){
		this.status = SUCCESS;
		this.resultList = result;
	}
	
	
	public Result ( Exception exception ){
		this.status = ERROR;
		this.validators = new ArrayList<Validator>();
		this.validators.add(new Validator(exception.getMessage()));
	}
	
    public Result ( Validator validator ){
        this.status = Result.ERROR;
        this.validators = new ArrayList<Validator>();
        this.validators.add(validator);
        
    }
    


	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
	
	

}
