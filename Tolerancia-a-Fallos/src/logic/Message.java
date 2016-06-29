package logic;
import java.io.*;
import java.text.SimpleDateFormat;

import java.util.*;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private String hour;

	public Message(String message){
		this.message = message;
		this.hour = this.getValueHour();
	}

	private String getValueHour(){
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime()).toString();
	}

	public String getMessage(){
		return this.message;
	}

	public String getHour(){
		return this.hour;
	}

	public String getMessageAll(){
		return  "Mensaje: "+this.message+". Hora: "+this.hour;
	}
}
