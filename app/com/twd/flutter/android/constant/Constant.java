package com.twd.flutter.android.constant;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.print.attribute.standard.Severity;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.jdbc.PreparedStatement;
import com.twd.flutter.android.bean.MainResponse;

public class Constant {
	public static String alertclasss_success="alert-success";
	public static String alertclasss_faild="alert-danger";
	public static String alertclasss_warning="alert-warning";

	public static String message_save_successfull="Record has been Successfully Saved..!";
	public static String message_save_failed="Record has been NOT Successfully Saved..! Please Check and Try Again...!";
	public static String message_update_successfull="Record has been Successfully Updated..!";
	public static String message_update_failed="Record has been NOT Successfully Updated..! Please Check and Try Again...!";
	public static String message_delete_successfull="Successfully..!";
	public static String message_dalete_failed="Faild..! Please Check and Try Again...!";
	public static String message_url_notaccess="Access Denied.!You have not permission to access this system please contact to system admin...!";
	public static String message_wrong_input="Wrong Input.!You have enter Wrong Input please try again...!";

	public static String btnsave="SAVE";
	public static String btncancle="CANCEL";
	public static String btnupdate="EDIT";
	public static String btndelete="DELETE";
	public static String btnaddnew="ADD NEW";
	public static String btnview="VIEW";
	public static String btnprint="PRINT";

	public static String btnsaveclass="btn btn-primary";
	public static String btncancleclass="btn btn-info";
	public static String btnupdateclass="badge badge-warning";
	public static String btnwarningclass="btn btn-warning";
	public static String btndangerclass="btn btn-danger";
	public static String btndeleteclass="badge badge-danger";
	public static String btnaddnewclass="badge badge-primary";
	public static String btnviewclass="badge badge-success";
	public static String btnviewclassinpt="btn btn-success";

	public static String btniconcolorwhite="style='color: white;'";

	public static String btniconsave="glyphicon glyphicon-floppy-disk";
	public static String btniconupdate="glyphicon glyphicon-edit";
	public static String btnicondelete="glyphicon glyphicon-trash";
	public static String btniconcancle="glyphicon glyphicon-remove-sign";
	public static String btniconaddnew="glyphicon glyphicon-plus-sign";
	public static String btniconview="glyphicon glyphicon-eye-open";
	public static String btniconprint="glyphicon glyphicon-print";
	public static String btniconfile="glyphicon glyphicon-file";
	public static String btnicondownloadalt="glyphicon glyphicon-download-alt";
	public static String btniconthumbsup="glyphicon glyphicon-thumbs-up";
	public static String btniconmoney="glyphicon glyphicon-money";
	public static String btniconupload="glyphicon glyphicon-cloud-upload";
	
	
	public static boolean errorDate()
	{
		boolean res=false;
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.YEAR, 2024);
		Date currentDate=new Date();
		try
		{
			if(currentDate.after(cal.getTime()))
			{
				 res=false;
			}else
			{
				res=true;
			}
		}catch (Exception e) {
				 res=false;
			}
		return res;
	}

	public static String AppDateDbDate(String dentryDate) throws ParseException {
		SimpleDateFormat userDate=new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dbDate=new SimpleDateFormat("dd-MMM-yyyy");
		Date date=null;
		date=userDate.parse(dentryDate);
		return dbDate.format(date);
	}
	
	public static String AppDateMinus1DbDate(String dentryDate) throws ParseException {
		SimpleDateFormat userDate=new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dbDate=new SimpleDateFormat("dd-MMM-yyyy");
		Date date=null;
		date=userDate.parse(dentryDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return dbDate.format(cal.getTime());
	}
	
	public static String DbDateToAppDate(Date dbDate) throws ParseException {
		SimpleDateFormat userDate=new SimpleDateFormat("dd/MM/yyyy");
		return userDate.format(dbDate);
	}
	public static String DbDateTimeToAppDateTime(Date dbDate) throws ParseException {
		SimpleDateFormat userDate=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
		return userDate.format(dbDate);
	}
	public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
	
	public static String getCurrentDate() throws ParseException {
		SimpleDateFormat dbDate=new SimpleDateFormat("dd-MMM-yyyy");
		Date date=new Date();
		return dbDate.format(date);
	}
	
	public static String getCurrentAppDate() throws ParseException {
		SimpleDateFormat dbDate=new SimpleDateFormat("dd/MM/yyyy");
		Date date=new Date();
		return dbDate.format(date);
	}
	
	public static String decimalFormat(double number,String str){
		DecimalFormat df=new DecimalFormat("#0."+str);
		return df.format(number);
	}
	public static String getVyearcode(Date slipdate, int wsstartdate, int wsenddate) {
		String vyearcode = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(slipdate);
		int month = cal.get(Calendar.MONTH) + 1 ;
		int year = cal.get(Calendar.YEAR);
		if(month>=wsstartdate){
			vyearcode = year + "-" + (year + 1);
		}else{
			vyearcode = (year - 1 ) + "-" + year;
		}
		return vyearcode;
	}
	public static String getCurrentDateTime() {
		SimpleDateFormat dbDate=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dbDate.format(new Date());
	}
	public static int getSeasonNo(String str,Connection conn){
		String seasonYeararya[]=str.split("-");
		String season_year=seasonYeararya[0];
		int season_no=0;
		try(java.sql.PreparedStatement pst=conn.prepareStatement("select t.nyear_id from twderp.erp_m_year_master t where t.cseason_year=?"))
		{
			int i=1;
			pst.setString(i++,season_year);
			try(ResultSet rs=pst.executeQuery())
			{
				while(rs.next())
				{
					season_no=rs.getInt("nyear_id");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
	}
			
		return season_no;
		
	}

	public double getExpTonnage(int nseasonCode, int nvarietyCode, Connection conn) {
		
		double exptonnage = 0;

		try(java.sql.PreparedStatement pst=conn.prepareStatement("select t.nexp_tonnage from agri.agri_m_expected_tonnage t where t.nseason_code=? and t.ncane_type_code=1 and t.ncane_variety_code=?")) {
			int i = 1;
			pst.setInt(i++, nseasonCode);
			pst.setInt(i++, nvarietyCode);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					exptonnage= rs.getDouble("nexp_tonnage");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exptonnage;
	}
		
		public static HashMap<String, String> extractQR(String qrurl) throws MalformedURLException, UnsupportedEncodingException {
			HashMap<String, String> data=new HashMap<>();
			String qrcode = "";
				URL url = new URL(qrurl);
				String query = url.getQuery();
				String[] pairs = query.split("&");
				for (String pair : pairs) {
					int idx = pair.indexOf("=");
					String pairP1 = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
					String pairP2 = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
					String urkWithOutDecode = pair.substring(idx + 1);
					if (pairP1.equalsIgnoreCase("p1")) {
						String key = "3wdsoftware@137.";
						qrcode = SecurityUtil.dencrypt(pairP2, key);
						data.put("qrcode", qrcode);
						data.put("qrurl", urkWithOutDecode);
						break;
					}

				}
	return data;
		}
		 public static String replaceAll(String input, String target, String replacement) {
		        if (input == null || target == null || replacement == null) {
		            throw new IllegalArgumentException("Input, target, and replacement cannot be null");
		        }
		        return input.replaceAll(target, replacement);
		    }
		 
		 
		 public static String subtractOneDay(String dateString) {
		        try {
		           
		            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		            Date date = sdf.parse(dateString);
		            Calendar calendar = Calendar.getInstance();
		            calendar.setTime(date);
		            calendar.add(Calendar.DAY_OF_MONTH, -1);
		            String result = sdf.format(calendar.getTime());
		            
		            return result;
		        } catch (ParseException e) {
		            // Handle parsing exception
		            e.printStackTrace();
		            return null;
		        }

		 }
		 public static String convertToUnicode(String marathiText) {
			 return new String(marathiText.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
		    }
		 
		 
		 public static double parseShiftNumber(String shiftNo) {
			    try {
			        String[] parts = shiftNo.split(":");
			     
			        if (parts.length == 2) {
			            int hours = Integer.parseInt(parts[0]);
			            int minutes = Integer.parseInt(parts[1]);
			            return hours + minutes / 60.0;
			        }
			    } catch (NumberFormatException ignored) {
			    }

			    return 0-0; // Default value if parsing fails
			}
		
	
	 

}
