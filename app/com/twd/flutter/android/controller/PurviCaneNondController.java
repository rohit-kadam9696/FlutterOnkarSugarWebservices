package com.twd.flutter.android.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twd.flutter.android.bean.GatHangamCurrentCaneResponse;
import com.twd.flutter.android.bean.MonthHangamCurrentCaneResponse;
import com.twd.flutter.android.bean.MonthVarietyCurrentCaneResponse;
import com.twd.flutter.android.bean.PurviCaneNondVhangamResponse;
import com.twd.flutter.android.bean.VarietyHangamCaneResponse;
import com.twd.flutter.android.constant.Constant;
import com.twd.flutter.android.service.CurrentCaneNondService;
import com.twd.flutter.android.service.PurviCaneNondService;
import com.twd.flutter.android.serviceInterface.CaneNondServiceInterface;
import com.twd.flutter.android.serviceInterface.PurviCaneNondServiceInterface;
import com.twd.flutter.both.connection.DBConnection;


@WebServlet("/PurviCaneNondController")
public class PurviCaneNondController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public PurviCaneNondController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		PurviCaneNondServiceInterface purvicanenond = new PurviCaneNondService();
		
		 response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setHeader("Access-Control-Allow-Origin", "*");
		    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		    PrintWriter pw = response.getWriter();
		    BufferedReader reader = request.getReader();
		    StringBuilder jsonBody = new StringBuilder();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        jsonBody.append(line);
		    }
		    reader.close();

		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
		        JsonNode json = objectMapper.readTree(jsonBody.toString());
		        String action = json.get("action").toString();
		       
		        String mobileNo = json.get("mobileNo").toString();
		        String imei = json.get("imei").toString();
		        String randomString = json.get("randomString").toString();
		        String versionId = json.get("versionId").toString();
		        String chitBoyId = json.get("chitBoyId").toString();
		        String accessType = request.getParameter("accessType");
		        
		        String sugarFactoryId= json.get("sugarFactoryId").toString();
		        if (sugarFactoryId != null) {
		        	sugarFactoryId = Constant.replaceAll(sugarFactoryId, "\"", "");
		        }
		      
		       
		        JSONObject reqObj = new JSONObject(jsonBody.toString());
		        
		      

		        action = Constant.replaceAll(action, "\"", "");
		        mobileNo = Constant.replaceAll(mobileNo, "\"", "");
		        imei = Constant.replaceAll(imei, "\"", "");
		        randomString = Constant.replaceAll(randomString, "\"", "");
		        versionId = Constant.replaceAll(versionId, "\"", "");
		        chitBoyId = Constant.replaceAll(chitBoyId, "\"", "");
		       

		        JSONObject result = null;
		        if (accessType == null || accessType.trim().isEmpty())
		            accessType = "1";
		        
		        DBConnection conn =new DBConnection(sugarFactoryId);
		        switch (action) {
		            case "purvvarietyhangamusnond":
		            	PurviCaneNondVhangamResponse vhResponse=new PurviCaneNondVhangamResponse();
				   vhResponse =purvicanenond.PurviVarietyHangamCaneNond(reqObj, imei, accessType, vhResponse);
					   result=new JSONObject(vhResponse);
					  
		                    try (PrintWriter out = response.getWriter()) {
					           out.print(result.toString());
					        } catch (IOException e) {
					            e.printStackTrace();
					        }
					        break;
					        
		            case "purvgathangamusnond":
		            	GatHangamCurrentCaneResponse ghResponse=new GatHangamCurrentCaneResponse();
		            	ghResponse =purvicanenond.PurviGatHangamCaneNond(reqObj, imei, accessType, ghResponse);
						   result=new JSONObject(ghResponse);
						  
			                    try (PrintWriter out = response.getWriter()) {
						           out.print(result.toString());
						        } catch (IOException e) {
						            e.printStackTrace();
						        }
						        break;     
		            case "pudhilmonthhangamusnond":
		            	MonthHangamCurrentCaneResponse mhResponse=new MonthHangamCurrentCaneResponse();
		            	mhResponse =purvicanenond.monthHangamCaneNond(reqObj, imei, accessType, mhResponse);
						   result=new JSONObject(mhResponse);
						  
			                    try (PrintWriter out = response.getWriter()) {
						           out.print(result.toString());
						        } catch (IOException e) {
						            e.printStackTrace();
						        }
						        break;   
					
		            case "pudhilmonthvarietyusnond":
		            	MonthVarietyCurrentCaneResponse mvResponse=new MonthVarietyCurrentCaneResponse();
		            	mvResponse =purvicanenond.pudhilmonthVarietyCaneNond(reqObj, imei, accessType, mvResponse);
						   result=new JSONObject(mvResponse);
						   
						
						  
			                    try (PrintWriter out = response.getWriter()) {
						           out.print(result.toString());
						        } catch (IOException e) {
						            e.printStackTrace();
						        }
						        break; 
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		
	}


