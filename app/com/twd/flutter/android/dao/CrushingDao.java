package com.twd.flutter.android.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.twd.convertismtouni.DemoConvert2;
import com.twd.flutter.android.bean.CaneYardCrushingResponse;
import com.twd.flutter.android.bean.CaneYardCrushingResponse.CaneYardCrushingData;

import com.twd.flutter.android.bean.CrushingResponse;

import com.twd.flutter.android.bean.GatWiseCrushingResponse;
import com.twd.flutter.android.bean.GatWiseCrushingResponse.GatWiseCrushingData;
import com.twd.flutter.android.bean.HangamWiseCrushingResponse;
import com.twd.flutter.android.bean.HangamWiseCrushingResponse.HangamWiseCrushingData;
import com.twd.flutter.android.bean.HourlyCrushingReponse;
import com.twd.flutter.android.bean.ServerError;


import com.twd.flutter.android.bean.ShiftwiseCrushingResponse;
import com.twd.flutter.android.bean.ShiftwiseCrushingResponse.ShiftCrushingData;
import com.twd.flutter.android.bean.VarietyWiseCrushingResponse;
import com.twd.flutter.android.bean.VarietyWiseCrushingResponse.VarietyWiseCrushingData;
import com.twd.flutter.android.bean.VehicleTypeWarCrushingResponse;
import com.twd.flutter.android.bean.VehicleTypeWarCrushingResponse.VehicleTypeWarData;
import com.twd.flutter.android.constant.Constant;
import com.twd.flutter.android.constant.ConstantVeriables;


public class CrushingDao {
	 public CrushingResponse crushing(Connection conn, String date, String yearCode) {
	        CrushingResponse crushingResponse = new CrushingResponse();

	        try {
	            String sql = "SELECT SUM(t.nnet_wt) AS TWT FROM agri.wb_t_weigh_slip t WHERE t.dwslip_date = TO_DATE(?, 'DD-MM-YYYY')" ;
	            		

	            try (PreparedStatement pst = conn.prepareStatement(sql)) {
	                int i = 1;
	                pst.setString(i++, date);
	               
	                try (ResultSet rs = pst.executeQuery()) {
	                    while (rs.next()) {
	                        double todayCrushing = rs.getDouble("TWT");
	                        
	                       

	                      //  CrushingData todayCrushingData = new CrushingData();
	                     //   todayCrushingData.setTodayCrushing(todayCrushing);
	                        crushingResponse.setTodayCrushing(todayCrushing);
	                    }
	                }
	            }

	            String sql1 = "SELECT SUM(t.nnet_wt) AS TWT FROM agri.wb_t_weigh_slip t WHERE t.dwslip_date <= TO_DATE(?, 'DD-MM-YYYY')";
	           	   try (PreparedStatement pst = conn.prepareStatement(sql1)) {
	                int i = 1;
	                pst.setString(i++, date);
	              
	                try (ResultSet rs = pst.executeQuery()) {
	                    while (rs.next()) {
	                        double uptoTodayCrushing = rs.getDouble("TWT");

	                      
	                        crushingResponse.setUptoTodayCrushing(uptoTodayCrushing);
	                    }
	                }
	            }

	            crushingResponse.setSuccess(true);

	        } catch (SQLException e) {
	            crushingResponse.setSuccess(false);
	            ServerError error = new ServerError();
	            error.setError(ConstantVeriables.ERROR_006);
	            error.setMsg("Crushing information not found: " + e.getMessage());
	            crushingResponse.setSe(error);
	            e.printStackTrace(); // Consider logging the exception
	        }

	        return crushingResponse;
	    }
	
	 public ShiftwiseCrushingResponse shiftWiseCrushingReport(ShiftwiseCrushingResponse crushingResp, Connection conn, String date, String yearCode) throws SQLException {
		    ShiftwiseCrushingResponse shiftwiseCrushingResponse = new ShiftwiseCrushingResponse();
		    Map<Double, ShiftwiseCrushingResponse.ShiftCrushingData> shiftWiseCrushingMap = new HashMap<>();

		    try {
		        String sql = "SELECT t.nshift_no, SUM(t.nnet_wt) AS nnet_wt FROM agri.wb_t_weigh_slip t WHERE dwslip_date = TO_DATE(?, 'DD-MON-YYYY')GROUP BY t.nshift_no";
		        try (PreparedStatement pst = conn.prepareStatement(sql)) {
		            pst.setString(1, date);

		            try (ResultSet rs = pst.executeQuery()) {
		                while (rs.next()) {
		                    String shiftNo = rs.getString("nshift_no");
		                    double todayCrushing = rs.getDouble("nnet_wt");

		                    // Extract only numeric part from shiftNo
		                    //String numericShiftNo = shiftNo.replaceAll("[^0-9.]", "");
		                  
		                    if (!shiftNo.isEmpty()) {
		                        // Extract only numeric part from shiftNo
		                        String numericShiftNo = shiftNo.replaceAll("[^0-9.]", "");

		                        if (!numericShiftNo.isEmpty()) {
		                            if (!shiftWiseCrushingMap.containsKey(Double.parseDouble(numericShiftNo))) {
		                                ShiftwiseCrushingResponse.ShiftCrushingData shiftCrushingData = shiftwiseCrushingResponse.new ShiftCrushingData();
		                                shiftCrushingData.setShiftNo(numericShiftNo);
		                                shiftWiseCrushingMap.put(Double.parseDouble(numericShiftNo), shiftCrushingData);
		                            }

		                            ShiftwiseCrushingResponse.ShiftCrushingData shiftCrushingData = shiftWiseCrushingMap.get(Double.parseDouble(numericShiftNo));
		                            shiftCrushingData.setShiftTodayCrushing(todayCrushing);
		                        } else {
		                            // Handle the case where numericShiftNo is empty (e.g., after removing non-numeric characters)
		                            // You might want to log a message or handle it according to your application's logic
		                        }
		                    } else {
		                        // Handle the case where shiftNo is empty
		                        // You might want to log a message or handle it according to your application's logic
		                    }
		                }
		            }
		        }

		        String yesterdayDate = Constant.subtractOneDay(date);
		        String sql1 = "SELECT t.nshift_no, SUM(t.nnet_wt) AS  nnet_wt FROM agri.wb_t_weigh_slip t WHERE dwslip_date = TO_DATE(?, 'DD-MON-YYYY')GROUP BY t.nshift_no";
		        		
		        try (PreparedStatement pst = conn.prepareStatement(sql1)) {
		            pst.setString(1, yesterdayDate);

		            try (ResultSet rs = pst.executeQuery()) {
		                while (rs.next()) {
		                    String shiftNo = rs.getString("nshift_no");
		                    double yesterdayCrushing = rs.getDouble("nnet_wt");

		                    // Extract only numeric part from shiftNo
		                    if (!shiftNo.isEmpty()) {
		                        // Extract only numeric part from shiftNo
		                        String numericShiftNo = shiftNo.replaceAll("[^0-9.]", "");

		                        if (!numericShiftNo.isEmpty()) {
		                            if (!shiftWiseCrushingMap.containsKey(Double.parseDouble(numericShiftNo))) {
		                                ShiftwiseCrushingResponse.ShiftCrushingData shiftCrushingData = shiftwiseCrushingResponse.new ShiftCrushingData();
		                                shiftCrushingData.setShiftNo(numericShiftNo);
		                                shiftWiseCrushingMap.put(Double.parseDouble(numericShiftNo), shiftCrushingData);
		                            }

		                            ShiftwiseCrushingResponse.ShiftCrushingData shiftCrushingData = shiftWiseCrushingMap.get(Double.parseDouble(numericShiftNo));
		                            shiftCrushingData.setShiftYeastrdayCrushing(yesterdayCrushing);
		                        } else {
		                            // Handle the case where numericShiftNo is empty (e.g., after removing non-numeric characters)
		                            // You might want to log a message or handle it according to your application's logic
		                        }
		                    } else {
		                        // Handle the case where shiftNo is empty
		                        // You might want to log a message or handle it according to your application's logic
		                    }		                }
		            }
		        }

		        shiftwiseCrushingResponse.setShiftWiseCrushingBeanMap(shiftWiseCrushingMap);
		        shiftwiseCrushingResponse.setSuccess(true);

		    } catch (SQLException e) {
		        crushingResp.setSuccess(false);
		        ServerError error = new ServerError();
		        error.setError(ConstantVeriables.ERROR_006);
		        error.setMsg("Crushing information not found: " + e.getMessage());
		        crushingResp.setSe(error);
		        e.printStackTrace(); // Consider logging the exception
		    }

		    return shiftwiseCrushingResponse;
		}

	public CaneYardCrushingResponse caneYardShilak(CaneYardCrushingResponse caneyardres, Connection conn,String date,String yearCode)throws SQLException {
	      
	       
		
		CaneYardCrushingResponse caneYardCrushingResponse= new CaneYardCrushingResponse();
		    Map<String, CaneYardCrushingData> caneYardCrushingMap = new HashMap<>();

		    try {
		        String sql = "SELECT t.vehicle,t.cnt,t.avg_ton FROM androidapp.web_yard_balance t ORDER BY t.vehicle ASC";
		        try (PreparedStatement pst = conn.prepareStatement(sql)) {
		        
		          

		            try (ResultSet rs = pst.executeQuery(sql)) {
		                while (rs.next()) {
		                	String vehicleType = Constant.convertToUnicode(rs.getString("VEHICLE"));
		                	
		                    double cnt = rs.getDouble("CNT");

		                    // Check if yesterday's data is available
		                    CaneYardCrushingResponse.CaneYardCrushingData caneYardCrushingData = caneYardCrushingMap.get(vehicleType);
		                    if (caneYardCrushingData == null) {
		                        // If not present, create a new ShiftCrushingData object
		                    	caneYardCrushingData = caneYardCrushingResponse.new CaneYardCrushingData ();
		                    	caneYardCrushingData.setVehicleType(vehicleType);
		                    	caneYardCrushingMap.put(vehicleType, caneYardCrushingData);
		                    }

		                    // Set today's crushing data
		                    caneYardCrushingData.setCnt(cnt);
		                }
		            }
		        }
		        
		        // Second SQL query
		        String sql1 = "SELECT t.vehicle,t.cnt,t.avg_ton FROM androidapp.web_yard_balance t ORDER BY t.vehicle ASC";
		        try (PreparedStatement pst = conn.prepareStatement(sql1)) {
		          
		         
		            try (ResultSet rs = pst.executeQuery(sql1)) {
		                while (rs.next()) {
		                	String vehicleType = Constant.convertToUnicode(rs.getString("VEHICLE"));
		                    
		                    double avgton = rs.getDouble("AVG_TON");
		                    CaneYardCrushingData caneYardCrushingData = caneYardCrushingMap.get(vehicleType);
		                    // Check if today's data is available
		                    if (caneYardCrushingData == null) {
		                    	caneYardCrushingData = caneYardCrushingResponse.new CaneYardCrushingData ();
		                    	caneYardCrushingData.setVehicleType(vehicleType);
		                    	caneYardCrushingMap.put(vehicleType, caneYardCrushingData);

		                    	
		                    
		                      
		                    }
		                    
		                    
		                    caneYardCrushingData.setAvgTonnage(avgton);;
		                }
		            }
		        }

		        // Set the modified shift-wise crushing map in the response
		        caneYardCrushingResponse.setCaneYardCrushingBeanMap(caneYardCrushingMap);;
		        caneYardCrushingResponse.setSuccess(true);

		    } catch (SQLException e) {
		    	caneyardres.setSuccess(false);
		        ServerError error = new ServerError();
		        error.setError(ConstantVeriables.ERROR_006);
		        error.setMsg("Crushing information not found: " + e.getMessage());
		        caneyardres.setSe(error);
		        e.printStackTrace(); // Consider logging the exception
		    }

		    return caneYardCrushingResponse;

}
	
	public HourlyCrushingReponse hourlyCrushing(HourlyCrushingReponse hourlyRes, Connection conn, String date,String yearCode) 
	{
		HourlyCrushingReponse hourlyCrushingResponse = new HourlyCrushingReponse();
		    Map<Double, HourlyCrushingReponse.HourlyCrushingData> hourlyCrushingMap = new HashMap<>();

		    try {
		        String sql = "select t.HR,t.WT from agri.vw_app_web_hourly_crushing t ORDER BY t.HR ASC";

		        try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        Date currentDate = Date.valueOf(LocalDate.now());
                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

                   String formattedDate = dateFormat.format(currentDate);

		          //  pst.setString(1,formattedDate);
		           
		            try (ResultSet rs = pst.executeQuery()) {
		                while (rs.next()) {
		                    double hour = rs.getDouble("HR");
		                    double crushingWt= rs.getDouble("WT");

		                    // Check if yesterday's data is available
		                    HourlyCrushingReponse.HourlyCrushingData hourlyCrushingData = hourlyCrushingMap.get(hour);
		                    if (hourlyCrushingData == null) {
		                        // If not present, create a new ShiftCrushingData object
		                    	hourlyCrushingData = hourlyCrushingResponse .new HourlyCrushingData();
		                    	hourlyCrushingData.setHour(hour);
		                       hourlyCrushingMap.put(hour, hourlyCrushingData);
		                    }

		                    // Set today's crushing data
		                    hourlyCrushingData.setCrushingWt(crushingWt);
		                }
		            }
		        }
		      
		      

		        // Set the modified shift-wise crushing map in the response
		        hourlyCrushingResponse.setHourlyCrushingBeanMap(hourlyCrushingMap);
		        hourlyCrushingResponse.setSuccess(true);

		    } catch (SQLException e) {
		       hourlyRes.setSuccess(false);
		        ServerError error = new ServerError();
		        error.setError(ConstantVeriables.ERROR_006);
		        error.setMsg("Crushing information not found: " + e.getMessage());
		       hourlyRes.setSe(error);
		        e.printStackTrace(); // Consider logging the exception
		    }

		    return hourlyCrushingResponse;
	}
	public GatWiseCrushingResponse gatWiseCrushingReport(GatWiseCrushingResponse gatResponse, Connection conn,
			String date, String yearCode) {
		
		GatWiseCrushingResponse gatWiseCrushingResponse= new GatWiseCrushingResponse();
	    Map<String, GatWiseCrushingData> gatWiseCrushingMap = new HashMap<>();

	    try {
	        String sql = "SELECT t.nsection_code, t.csection_mname AS csection_mname, SUM(w.nnet_wt) AS nnet_wt FROM agri_m_section t, agri.wb_t_weigh_slip w, agri_m_village v WHERE t.nsection_code=v.nsection_code AND w.nvil_code=v.nvil_code AND w.dwslip_date= TO_DATE(?, 'DD-MM-YYYY') AND t.vactive='A' GROUP BY t.csection_mname, t.nsection_code ORDER BY t.nsection_code" ; 
	        	
	        try (PreparedStatement pst = conn.prepareStatement(sql)) {
	        	 pst.setString(1, date);
		        
	          

	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
	                	String department= Constant.convertToUnicode(rs.getString("csection_mname"));
	                	
	                    double todayCrushing= rs.getDouble("nnet_wt");

	                    // Check if yesterday's data is available
	                   GatWiseCrushingResponse. GatWiseCrushingData gatWiseCrushingData = gatWiseCrushingMap.get(department);
	                    if (gatWiseCrushingData  == null) {
	                      
	                    	gatWiseCrushingData  = gatWiseCrushingResponse.new GatWiseCrushingData ();
	                    	gatWiseCrushingData.setDepartment(department);
	                    	gatWiseCrushingMap.put(department, gatWiseCrushingData);
	                    }

	                    
	                    gatWiseCrushingData.setTodayCrushing(todayCrushing);

	                }
	            }
	        }
	        
	  
	        String sql1 = "SELECT t.nsection_code, t.csection_mname AS csection_mname, SUM(w.nnet_wt) AS nnet_wt FROM agri_m_section t, agri.wb_t_weigh_slip w, agri_m_village v WHERE t.nsection_code=v.nsection_code AND w.nvil_code=v.nvil_code AND w.dwslip_date<= TO_DATE(?, 'DD-MM-YYYY') AND t.vactive='A' GROUP BY t.csection_mname, t.nsection_code ORDER BY t.nsection_code" ; 
	        		  
	        		
	       
	        try (PreparedStatement pst = conn.prepareStatement(sql1)) {
	          int i=1;
	           pst.setString(i++,date);
	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
	                		String department= Constant.convertToUnicode(rs.getString("csection_mname"));
	                    
	                    double uptoTodayCrushingngWt = rs.getDouble("nnet_wt");
	                    GatWiseCrushingData gatWiseCrushingData =gatWiseCrushingMap.get(department);
	                    // Check if today's data is available
	                    if (gatWiseCrushingData  == null) {
	                    	gatWiseCrushingData =gatWiseCrushingResponse.new GatWiseCrushingData();
	                    	gatWiseCrushingData.setDepartment(department);
	                    	gatWiseCrushingMap.put(department,gatWiseCrushingData);

	                    	
	                    
	                      
	                    }
	                    
	                    
	                  gatWiseCrushingData.setUptoTodayCrushingngWt(uptoTodayCrushingngWt);;
	                }
	            }
	        }

	        // Set the modified shift-wise crushing map in the response
	        gatWiseCrushingResponse.setGatWiseCrushingBeanMap(gatWiseCrushingMap);;
	         gatWiseCrushingResponse.setSuccess(true);

	    } catch (SQLException e) {
	    	gatResponse.setSuccess(false);
	        ServerError error = new ServerError();
	        error.setError(ConstantVeriables.ERROR_006);
	        error.setMsg("Crushing information not found: " + e.getMessage());
	       gatResponse.setSe(error);
	        e.printStackTrace(); // Consider logging the exception
	    }

	    return gatWiseCrushingResponse;

}
	
	public HangamWiseCrushingResponse hangamWiseCrushingReport(HangamWiseCrushingResponse hangamResponse,
	        Connection conn, String date, String yearCode) {
	  
		
		  HangamWiseCrushingResponse hangamWiseCrushingResponse = new HangamWiseCrushingResponse();
	        Map<String, HangamWiseCrushingData> hangamWiseCrushingMap = new HashMap<>();
            int i=1;
	    //String sql = "select t.vhangam_name,t.today_wt,t.todate_wt from androidapp.mis_crushing_hangam t ORDER BY t.vhangam_name";
	    try {
	        String sql = "SELECT t2.nseason_code, t2.cseason_mname AS cseason_mname, t2.cseason_ename, SUM(t.nnet_wt) AS nnet_wt FROM agri.wb_t_weigh_slip t, agri.agri_t_plantation t1, agri_m_season t2 WHERE t.cseason_year = t1.cseason_year AND t.nplot_no = t1.nplot_no AND t1.nseason_code = t2.nseason_code AND t.dwslip_date = TO_DATE(?, 'DD-MM-YYYY') AND t1.cseason_year = ? GROUP BY t2.nseason_code, t2.cseason_mname, t2.cseason_ename ORDER BY t2.nseason_code"; 
	        		
	        		
	        try (PreparedStatement pst = conn.prepareStatement(sql)) {
	            
	          pst.setString(1, date);
	          pst.setString(2, yearCode);

	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
	                	String hangam= Constant.convertToUnicode(rs.getString("cseason_mname"));
	                	
	                    double todayCrushing= rs.getDouble("nnet_wt");

	                    // Check if yesterday's data is available
	                   HangamWiseCrushingResponse.HangamWiseCrushingData hangamWiseCrushingData = hangamWiseCrushingMap.get(hangam);
	                    if (hangamWiseCrushingData == null) {
	                      
	                    	hangamWiseCrushingData =  new HangamWiseCrushingData ();
	                    	hangamWiseCrushingData.setHangam(hangam);
	                    	hangamWiseCrushingMap.put(hangam, hangamWiseCrushingData);
	                    }

	                    
	                   hangamWiseCrushingData .setTodayCrushing(todayCrushing);

	                }
	            }
	        }
	   


	        String sql1 = "SELECT t2.nseason_code, t2.cseason_mname AS cseason_mname, t2.cseason_ename, SUM(t.nnet_wt) AS nnet_wt FROM agri.wb_t_weigh_slip t, agri.agri_t_plantation t1, agri_m_season t2 WHERE t.cseason_year = t1.cseason_year AND t.nplot_no = t1.nplot_no AND t1.nseason_code = t2.nseason_code AND t.dwslip_date <= TO_DATE(?, 'DD-MM-YYYY') AND t1.cseason_year = ? GROUP BY t2.nseason_code, t2.cseason_mname, t2.cseason_ename ORDER BY t2.nseason_code"; 
	        		
	      
	        try (PreparedStatement pst = conn.prepareStatement(sql1)) {
	                pst.setString(1, date);
	                pst.setString(2, yearCode);
	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
	                String hangam= Constant.convertToUnicode(rs.getString("cseason_mname"));
	                	
	                  double uptoTodayCrushingngWt1 = rs.getDouble("nnet_wt");

	                    HangamWiseCrushingData  hangamWiseCrushingData =hangamWiseCrushingMap.get(hangam);
	                    
	                    if (hangamWiseCrushingData == null) {
	                    	hangamWiseCrushingData =new HangamWiseCrushingData ();
	                    	hangamWiseCrushingData.setHangam(hangam);
	                    	hangamWiseCrushingMap.put(hangam, hangamWiseCrushingData);

	                    	
	                    
	                      
	                    }
	                    
	                    
	                  hangamWiseCrushingData.setUptoTodayCrushingngWt1(uptoTodayCrushingngWt1);
	                }
	            }
	        }

	        // Set the modified shift-wise crushing map in the response
	        hangamWiseCrushingResponse.setHangamWiseCrushingBeanMap(hangamWiseCrushingMap);
	         hangamWiseCrushingResponse.setSuccess(true);

	    } catch (SQLException e) {
	    	hangamResponse.setSuccess(false);
	        ServerError error = new ServerError();
	        error.setError(ConstantVeriables.ERROR_006);
	        error.setMsg("Crushing information not found: " + e.getMessage());
	       hangamResponse.setSe(error);
	        e.printStackTrace(); // Consider logging the exception
	    }

	    return hangamWiseCrushingResponse;


	}
	public VarietyWiseCrushingResponse varietyWiseCrushingReport(VarietyWiseCrushingResponse varietyResponse,
			Connection conn, String date, String yearCode) {
		
		  VarietyWiseCrushingResponse varietyWiseCrushingResponse = new VarietyWiseCrushingResponse ();
	        Map<String, VarietyWiseCrushingData> varietyWiseCrushingMap = new HashMap<>();
          int i=1;
	    String sql = "select t2.nvariety_code,t2.cvariety_mname AS cvariety_mname ,t2.cvariety_ename,sum(nvl(t.nnet_wt,0))as net_wt from agri.wb_t_weigh_slip t,agri.agri_t_plantation t1,agri.agri_m_cane_variety t2 where t.cseason_year=t1.cseason_year and t.nplot_no=t1.nplot_no  and t1.nvariety_code=t2.nvariety_code(+) and t.vactive='A' and nvl(t.nnet_wt,0)>0  and t.nfact_code=1 and  t.dwslip_date = TO_DATE(?,'dd-Mon-yyyy')  and t.cseason_year=? group by t2.nvariety_code,t2.cvariety_ename,t2.cvariety_mname order by t2.nvariety_code";
	    try {
	        try (PreparedStatement pst = conn.prepareStatement(sql)) {
	            
	          pst.setString(1, date);
	          pst.setString(2, yearCode);

	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
	                	String varietyName= Constant.convertToUnicode(rs.getString("cvariety_mname"));
	                	
	                    double todayCrushingWt= rs.getDouble("net_wt");

	                    // Check if yesterday's data is available
	                   VarietyWiseCrushingResponse.VarietyWiseCrushingData varietyWiseCrushingData = varietyWiseCrushingMap.get(varietyName);
	                    if (varietyWiseCrushingData  == null) {
	                      
	                    	varietyWiseCrushingData  =  new VarietyWiseCrushingData ();
	                    	varietyWiseCrushingData.setVarietyName(varietyName);
	                    	varietyWiseCrushingMap.put(varietyName, varietyWiseCrushingData);
	                    }

	                    
	                varietyWiseCrushingData.setTodayCrushingWt(todayCrushingWt);

	                }
	            }
	        }
	   
   String sql1 ="select t2.nvariety_code,t2.cvariety_mname AS cvariety_mname,t2 .cvariety_ename,sum(nvl(t.nnet_wt,0))as net_wt from agri.wb_t_weigh_slip t,agri.agri_t_plantation t1,agri.agri_m_cane_variety t2 where t.cseason_year=t1.cseason_year and t.nplot_no=t1.nplot_no  and t1.nvariety_code=t2.nvariety_code(+) and t.vactive='A' and nvl(t.nnet_wt,0)>0  and t.nfact_code=1 and  t.dwslip_date <= TO_DATE(?,'dd-Mon-yyyy')  and t.cseason_year=? group by t2.nvariety_code,t2.cvariety_ename,t2.cvariety_mname order by t2.nvariety_code";

	        try (PreparedStatement pst = conn.prepareStatement(sql1)) {
	                pst.setString(1, date);
	                pst.setString(2, yearCode);
	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
               String varietyName= Constant.convertToUnicode(rs.getString("cvariety_mname"));
	                	
                double uptoTodayCrushingWt = rs.getDouble("net_wt");

	                        VarietyWiseCrushingResponse.VarietyWiseCrushingData varietyWiseCrushingData = varietyWiseCrushingMap.get(varietyName);
	                    
	                    if (varietyWiseCrushingData  == null) {
	                    	varietyWiseCrushingData  =new VarietyWiseCrushingData ();
	                         varietyWiseCrushingData.setVarietyName(varietyName);
	                    varietyWiseCrushingMap.put(varietyName, varietyWiseCrushingData);

	                    	
	                    
	}
	                    
	               varietyWiseCrushingData.setUptoTodayCrushingWt(uptoTodayCrushingWt);
	                }
	            }
	        }

	        // Set the modified shift-wise crushing map in the response
	        varietyWiseCrushingResponse.setVarietyWiseCrushingBeanMap(varietyWiseCrushingMap);
	        varietyWiseCrushingResponse.setSuccess(true);

	    } catch (SQLException e) {
	    	varietyResponse.setSuccess(false);
	        ServerError error = new ServerError();
	        error.setError(ConstantVeriables.ERROR_006);
	        error.setMsg("Crushing information not found: " + e.getMessage());
	       varietyResponse.setSe(error);
	        e.printStackTrace(); // Consider logging the exception
	    }

	    return varietyWiseCrushingResponse;
	}

	public VehicleTypeWarCrushingResponse vehicleWarcReport(Connection conn, String date,VehicleTypeWarCrushingResponse vehicleResponse) {
		VehicleTypeWarCrushingResponse vehicleWarCrushingResponse= new VehicleTypeWarCrushingResponse();
	    Map<String, VehicleTypeWarData> vehicleTypeCrushingMap = new HashMap<>();
                   
	    try {
	        String sql = "select t.nvehicle_type_code,t.cvehicle_type_mname AS VVEHICLE_TYPE,sum(t1.nnet_wt) AS nnet_wt from ht_m_vehicle_type t,agri.wb_t_weigh_slip t1 where t.nvehicle_type_code=t1.nveh_type_code and t1.dwslip_date=TO_DATE(?, 'DD-MM-YYYY') group by t.cvehicle_type_mname,t.nvehicle_type_code order by t.nvehicle_type_code";
	        try (PreparedStatement pst = conn.prepareStatement(sql)) {
	        	 int i=1;
	          pst.setString(i++,date);

	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
	                	String vvehicleType = Constant.convertToUnicode(rs.getString("VVEHICLE_TYPE"));
	                
	                     double todayWt= rs.getDouble("nnet_wt");

	                     
	                    VehicleTypeWarCrushingResponse .VehicleTypeWarData vehicleWarCrushingData = vehicleTypeCrushingMap.get(vvehicleType);
	                    if (vehicleWarCrushingData == null) {
	                        
	                    	vehicleWarCrushingData = vehicleWarCrushingResponse.new VehicleTypeWarData ();
	                    	vehicleWarCrushingData.setVvehicleType(vvehicleType);
	                    	 vehicleTypeCrushingMap.put(vvehicleType, vehicleWarCrushingData);
	                    }

	                    
	                    vehicleWarCrushingData.setTodayWt(todayWt);
	                }
	            }
	        }
	        
	        // Second SQL query
	        String sql1 = "select t.nvehicle_type_code,t.cvehicle_type_mname AS VVEHICLE_TYPE,sum(t1.nnet_wt) AS nnet_wt from ht_m_vehicle_type t,agri.wb_t_weigh_slip t1 where t.nvehicle_type_code=t1.nveh_type_code and t1.dwslip_date<=TO_DATE(?, 'DD-MM-YYYY') group by t.cvehicle_type_mname,t.nvehicle_type_code order by t.nvehicle_type_code";
	        try (PreparedStatement pst = conn.prepareStatement(sql1)) {
	        	int i=1;
	              pst.setString(i++,date);
	         
	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
	                	String vvehicleType = Constant.convertToUnicode(rs.getString("VVEHICLE_TYPE"));
	                
	                        double todateWt= rs.getDouble("nnet_wt");

	                    VehicleTypeWarCrushingResponse .VehicleTypeWarData vehicleWarCrushingData = vehicleTypeCrushingMap.get(vvehicleType);
	                    
	                    if (vehicleWarCrushingData == null) {
	                    	vehicleWarCrushingData = vehicleWarCrushingResponse.new VehicleTypeWarData ();
	                    	vehicleWarCrushingData.setVvehicleType(vvehicleType);
	                    	 vehicleTypeCrushingMap.put(vvehicleType, vehicleWarCrushingData);
	                    	
	                    
	                      
	                    }
	                    
	                    
	                    vehicleWarCrushingData.setTodateWt(todateWt);;
	                }
	            }
	        }

	      
	       vehicleWarCrushingResponse.setVehicleCrushingBeanMap(vehicleTypeCrushingMap);
	        vehicleWarCrushingResponse.setSuccess(true);

	    } catch (SQLException e) {
	    	vehicleResponse.setSuccess(false);
	        ServerError error = new ServerError();
	        error.setError(ConstantVeriables.ERROR_006);
	        error.setMsg("Crushing information not found: " + e.getMessage());
	        vehicleResponse.setSe(error);
	        e.printStackTrace(); // Consider logging the exception
	    }

	    return vehicleWarCrushingResponse;

	}

	
	
}



	 