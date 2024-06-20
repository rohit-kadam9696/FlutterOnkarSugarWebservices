package com.twd.flutter.android.constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SendSMS {
	public static void sendTxtSMS(String m, String text) throws IOException{
		
		
		HttpURLConnection connection = null;
		String msg=java.net.URLEncoder.encode(text, "UTF-8");
		String formattedMsg = msg.replace("+", " ");

     Pattern pattern = Pattern.compile("\\d+");
     String otp = null; 
      
        Matcher matcher = pattern.matcher(formattedMsg);

   	
        if (matcher.find()) {
            
             otp = matcher.group();
       
        } 
		String msg1=otp+" is your OTP.  Thanks JSMPLS";
		String targetURL="http://aquicksms.com/API/sms-api.php?auth=D!~9361ok3Zhhr09O&senderid=JSMPLS&msisdn="+m+"&message="+msg1;
		URL url = new URL(targetURL);
		    connection = (HttpURLConnection)url.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setRequestProperty("Content-Type", 
		        "application/x-www-form-urlencoded");
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
		  BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
}
	
	public static void main(String[] args) throws Exception {
		try {
			String str1="959595 is your OTP. " + 
					"Thanks" + 
					"JSMPLS";
			sendTxtSMS("8669138188,9730809106",str1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
