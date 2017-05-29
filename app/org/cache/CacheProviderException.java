package org.cache;


public class CacheProviderException extends Exception{

	private static final long serialVersionUID = 1L;

	public  CacheProviderException(){
        super();
    }

    public CacheProviderException(String message){
        super(message);
    }

    public CacheProviderException(Throwable cause){
        super(cause);
    }

    public CacheProviderException(String message, Throwable cause){
        super(message,cause);
    }

}