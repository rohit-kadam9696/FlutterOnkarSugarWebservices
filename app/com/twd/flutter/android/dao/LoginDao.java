package com.twd.flutter.android.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.twd.convertismtouni.DemoConvert2;
import com.twd.flutter.android.bean.AppUpdate;
import com.twd.flutter.android.bean.ConstantMessage;
import com.twd.flutter.android.bean.LoginResponse;
import com.twd.flutter.android.bean.MainResponse;
import com.twd.flutter.android.bean.ServerError;
import com.twd.flutter.android.constant.Constant;
import com.twd.flutter.android.constant.ConstantVeriables;
import com.twd.flutter.android.constant.RandomString;
import com.twd.flutter.android.constant.SendSMS;


public class LoginDao {

	public LoginResponse verifyLogin(String mobileno, String accessType, Connection conn) {
		LoginResponse res=new LoginResponse();
		ServerError svr=new ServerError();
	
		try
		{
				String sql="select t.nuser_name,t.vactive  from "+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_M_USER t,"+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_M_APP_ROLE t1,"+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_M_LIST t2 where t.nuser_role_id=t1.nuser_role_id and t1.napp_id=t2.napp_id and t.nmobile_no=? and t2.napp_id=?";
				try(PreparedStatement pst=conn.prepareStatement(sql))
				{
					int i=1;
					pst.setString(i++, mobileno);
					pst.setString(i++, accessType);
					
				
					try(ResultSet rs=pst.executeQuery())
					{
						if(rs.next())
						{
							
							res.setSuccess(true);
							res.setSlipboycode(rs.getString("nuser_name"));
							res.setChitBoyId(rs.getString("nuser_name"));
							res.setMobileno(mobileno);
							if(!rs.getString("vactive").equalsIgnoreCase("Y"))
							{
								res.setSuccess(false);
								svr.setError(ConstantVeriables.ERROR_003);
								svr.setMsg(ConstantMessage.deactiveUser);
								res.setSe(svr);
								return res;
							}
							/*res.setVfullName(rs.getString("vfull_name"));
							res.setDesignation(rs.getString("vsubdept_name_eng"));
							res.setHarvestingYearCode("2023-2024");
							
							
							res.setSuccess(true);
							res.setYearCode("2023-2024");
							res.setNuserRoleId(rs.getString("nuser_role_id"));*/
						}else
						{
							res.setSuccess(false);
							svr.setError(ConstantVeriables.ERROR_004);
							svr.setMsg(ConstantMessage.userNotFound);
							res.setSe(svr);
						}
					}
				}
			} catch (SQLException e) {
			res.setSuccess(false);
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("login Issue "+e.getMessage());
			res.setSe(error);
			e.printStackTrace();
		}
		
	
		return res;
	}

	public LoginResponse checkUserHistory(String accessType,LoginResponse res, Connection conn) {
		try(PreparedStatement pst=conn.prepareStatement("select t.nuserid from "+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_APP_T_USER_TRACKER t where t.nuserid=? and t.napp_id=? "))
		{
			int i=1;
			pst.setString(i++, res.getChitBoyId());
			pst.setString(i++, accessType);
			try(ResultSet rs=pst.executeQuery())
			{
				if(rs.next())
				{
					res.setSuccess(true);
				}else
				{
					res.setSuccess(false);
				}
			}
		} catch (SQLException e) {
			res.setSuccess(false);
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("login Issue "+e.getMessage());
			res.setSe(error);
			e.printStackTrace();
		}
		return res;
	}

	public LoginResponse saveimeiAndRandamString(String accessType, LoginResponse res, String imei,String mobileno, Connection conn) {
		try(PreparedStatement pst=conn.prepareStatement("insert into "+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_APP_T_USER_TRACKER(nuserid,vimei,vuniquestring,vuniquestring2,napp_id,nmobileno)VALUES(?,?,?,?,?,?)"))
		{
			int i=1;
			pst.setString(i++, res.getChitBoyId());
			pst.setString(i++, imei);
			pst.setString(i++, res.getUniquestring());
			pst.setString(i++, res.getUniquestring());
			pst.setString(i++, accessType);
			pst.setString(i++, mobileno);
			int r=pst.executeUpdate();
			if(r>0)
				res.setSuccess(true);
			else
				res.setSuccess(false);
		} catch (SQLException e) {
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("IMEI Save "+e.getMessage());
			res.setSe(error);
			e.printStackTrace();
		}
		return res;
	}

	public LoginResponse updateImeiAndRandamString(String accessType, LoginResponse res, String imei,String mobileno, Connection conn) {
		try(PreparedStatement pst=conn.prepareStatement("UPDATE "+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_APP_T_USER_TRACKER SET vimei=?,vuniquestring=? ,vuniquestring2=vuniquestring, nmobileno=? WHERE nuserid=? and napp_id=? "))
		{
			int i=1;
			pst.setString(i++, imei);
			pst.setString(i++, res.getUniquestring());
			pst.setString(i++, mobileno);
			pst.setString(i++, res.getChitBoyId());
			pst.setString(i++, accessType);
			
			int r=pst.executeUpdate();
			if(r>0)
				res.setSuccess(true);
			else
				res.setSuccess(false);
		} catch (SQLException e) {
			res.setSuccess(false);
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("IMEI Save "+e.getMessage());
			res.setSe(error);
			e.printStackTrace();
		}
		return res;
	}
	
	public LoginResponse generateOTP(String accessType, LoginResponse res, Connection conn) {

		try{
			try(PreparedStatement pst=conn.prepareStatement("update "+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_APP_T_OTP set vstatus='E' WHERE vstatus='Y' AND nmobile_no=? AND napp_id=?")){
				int i = 1;
				pst.setString(i++, res.getMobileno());
				pst.setString(i++, accessType);
				pst.executeUpdate();
			}
			String otp = RandomString.generateRandomNumber();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			try(PreparedStatement pst=conn.prepareStatement("insert into "+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_APP_T_OTP(notp_id,votp,nmobile_no,dgenrated_date,dexprie_date,vstatus,napp_id) values("+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_APP_OTP_SEQ.nextval,?,?,TO_DATE(?,'dd-Mon-yyyy HH24:mi:ss'),TO_DATE(?,'dd-Mon-yyyy HH24:mi:ss'),?,?)")){
				int i = 1;
				pst.setString(i++, otp);
				pst.setString(i++, res.getMobileno());
				pst.setString(i++, df.format(cal.getTime()));
				cal.add(Calendar.DAY_OF_MONTH, ConstantVeriables.otpexpiredate);
				pst.setString(i++, df.format(cal.getTime()));
				pst.setString(i++, "Y");
				pst.setString(i++, accessType);
				int r=pst.executeUpdate();
				if(r>0)
				{
					res.setSuccess(true);
				}
			}
//			Runnable r = new Runnable(){
//				@Override
//				public void run() {
//					try
//					{
//						SendSMS.sendTxtSMS(res.getMobileno(), otp + ConstantMessage.message2);
//					}catch (Exception e) {
//						e.printStackTrace();
//					}
//					
//					//MySMS.sendSMS(obj.getString("mobileno"), otp + App_Constant.message2);
//				}
//			};
//			Thread t= new Thread(r);  //Passing the object of anonymous class to the constructor of Thread
//			t.start();
//			
		} catch (Exception e) {
			res.setSuccess(false);
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("OTP "+e.getMessage());
			res.setSe(error);
			e.printStackTrace();
		}
		return res;
	
	}

	public MainResponse verifyUser(MainResponse res,String chit_boy_id, String ramdomstring, String imei, String accessType, Connection conn) {
		ServerError svr=new ServerError();
		try
		{
			res.setCurrentDateTime(Constant.getCurrentDateTime());
			boolean isDateError=Constant.errorDate();
			if(!isDateError)
			{
				res.setSuccess(false);
				svr.setError(ConstantVeriables.ERROR_006);
				svr.setMsg(ConstantMessage.dateError);
				res.setSe(svr);
			}else
			{
				String sql="select t.nmobile_no,TO_NCHAR(t.vfull_name_local)as vfull_name,t.nuser_name,t.nuser_role_id,t.vactive,t2.vimei,t2.vuniquestring,t2.vuniquestring2 from "+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_M_USER t,"+ConstantVeriables.DATABASERUSER_TWDERP+"erp_app_t_user_tracker t2 where  t.nuser_name=t2.nuserid AND t.nuser_name=? and t2.napp_id=?";//,t1.vdept_desc //t.ndept_id=t1.ndept_code AND // +ConstantVeriables.DATABASERUSER_INV+"inv_m_dept t1,"
				try(PreparedStatement pst=conn.prepareStatement(sql))
				{
					int i=1;
					pst.setString(i++, chit_boy_id);
					pst.setString(i++, accessType);
					
					try(ResultSet rs=pst.executeQuery())
					{
						if(rs.next())
						{
							if(!rs.getString("vactive").equalsIgnoreCase("Y"))
							{
								res.setSuccess(false);
								svr.setError(ConstantVeriables.ERROR_003);
								svr.setMsg(ConstantMessage.deactiveUser);
								res.setSe(svr);
								return res;
							}else if(!rs.getString("vimei").equalsIgnoreCase(imei))
							{
								res.setSuccess(false);
								svr.setError(ConstantVeriables.ERROR_002);
								svr.setMsg(ConstantMessage.imeiChangemsg);
								res.setSe(svr);
								return res;
							}
								else if(!ramdomstring.trim().equals(rs.getString("vuniquestring")) && !ramdomstring.trim().equals(rs.getString("vuniquestring2")))
							{
								res.setSuccess(false);
								svr.setError(ConstantVeriables.ERROR_001);
								svr.setMsg(ConstantMessage.uniqueChangemsg);
								res.setSe(svr);
								return res;
							}else
							{
								res.setSuccess(true);
								//res.setDesignation(rs.getString("vdept_desc"));
								res.setMobileno(rs.getString("nmobile_no"));
								res.setNuserRoleId(rs.getString("nuser_role_id"));
								res.setSlipboycode(rs.getString("nuser_name"));
								res.setFromTimeRawana(ConstantVeriables.fromTimeRawana);
								res.setToTimeRawana(ConstantVeriables.toTimeRawana);
								res.setVfullName(DemoConvert2.ism_to_uni(rs.getString("vfull_name")));
								res=permissionById(res, chit_boy_id, conn);
								if(res.getNlocationId()>0) {
									res=getLocationName(res, conn);
								}
							}
						}else
						{
							res.setSuccess(false);
							svr.setError(ConstantVeriables.ERROR_004);
							svr.setMsg(ConstantMessage.userNotFound);
							res.setSe(svr);
						}
						res.setUniquestring(ramdomstring);
						res=updateRandamString(res,chit_boy_id, accessType,conn);
					}
				}
				
				try(PreparedStatement pst=conn.prepareStatement("select t.cseason_year as vseason_year, t.vactive_wb from  twderp.erp_m_year_master t where (  t.vactive_wb='Y' )  order by t.cseason_year desc"))
				{
					try(ResultSet rs=pst.executeQuery())
					{
						while(rs.next())
						{
							
							if(rs.getString("vactive_wb").equals("Y")) {
								String vyearcode = (res.getHarvestingYearCode()!=null && !res.getHarvestingYearCode().equals(""))?res.getHarvestingYearCode()+",":"";
								vyearcode+=rs.getString("vseason_year");
								res.setHarvestingYearCode(vyearcode);	
							}
							
							
							//res.setHarvestingYearCode(rs.getString("vseason_back_year"));
						}
					}
				}
			}
		} catch (SQLException e) {
			res.setSuccess(false);
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("login Issue "+e.getMessage());
			res.setSe(error);
			e.printStackTrace();
		}
		return res;
	
	}

	private MainResponse getLocationName(MainResponse res, Connection conn) {
		try (PreparedStatement pst = conn.prepareStatement("select To_NCHAR(t.vlocation) as vlocation from MS_M_SUG_CARD_LOCATION t where t.nlocation_id = ?")) {
			int k = 1;
			pst.setInt(k++, res.getNlocationId());
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					res.setLocationName(DemoConvert2.ism_to_uni(rs.getString("vlocation")));
				}
			}
		} catch (SQLException e) {
			/*res.setSuccess(false);
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("permission "+e.getMessage());
			res.setSe(error);*/
			e.printStackTrace();
		}
		return res;
	}

	public MainResponse updateRandamString(MainResponse res, String chit_boy_id, String accessType, Connection conn) {
		try(PreparedStatement pst=conn.prepareStatement("UPDATE "+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_APP_T_USER_TRACKER SET vuniquestring2=vuniquestring,vuniquestring=? WHERE nuserid=? and napp_id=?"))
		{
			String randamstring=RandomString.generateRandomString();
			int i=1;
			pst.setString(i++, randamstring);
			pst.setString(i++, chit_boy_id);
			pst.setString(i++, accessType);
			int r=pst.executeUpdate();
			if(r>0)
			{
				res.setSuccess(true);
				res.setUniquestring(randamstring);
			}
			else
				res.setSuccess(false);
		} catch (SQLException e) {
			res.setSuccess(false);
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("IMEI Save "+e.getMessage());
			res.setSe(error);
			e.printStackTrace();
		}
		return res;
	}

	public MainResponse permissionById(MainResponse res, String chit_boy_id, Connection conn) {
		String sql="select t3.napp_id, t.vimg_name,t.ngroupid,t.nid,TO_NCHAR(t.vename) as vname from "+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_M_SUBMENU t,"+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_m_submenu_role t1,"+ConstantVeriables.DATABASERUSER_TWDERP+"erp_m_menu t3  where t.nid=t1.nid and t.ngroupid=t3.ngroupid and t1.nuser_role_id=? and t.ngroupid is not null and t.vactive='A' and t3.napp_id=6 order by t.ngroupid, t.norder_android";
			try (PreparedStatement pst = conn.prepareStatement(sql)) {
			int k = 1;
			pst.setString(k++, res.getNuserRoleId());
			try (ResultSet rs = pst.executeQuery()) {
				HashMap<String, ArrayList<HashMap<String, String>>> permisions = new HashMap<>();
				Set<String> pggroup = new HashSet<String>();
				while (rs.next()) {
					String pgroupid = rs.getString("ngroupid");
					String pid = rs.getString("nid");
					ArrayList<HashMap<String, String>> jar = null;
					if (permisions.containsKey(pgroupid)) {
						jar = permisions.get(pgroupid);
					} else {
						jar = new ArrayList<>();
					}
					HashMap<String, String> permObj = new HashMap<>();
					permObj.put("mnu_id", pid);
					permObj.put("mnu_name", DemoConvert2.ism_to_uni(rs.getString("vname")));
					permObj.put("vimg_name", rs.getString("vimg_name"));
					jar.add(permObj);
					permisions.put(pgroupid, jar);
					pggroup.add(pgroupid);
				}
				res.setPggroups(String.join(",", pggroup));
				res.setPerbygroup((new JSONObject(permisions)).toString());
			}
		} catch (SQLException e) {
			res.setSuccess(false);
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("permission "+e.getMessage());
			res.setSe(error);
			e.printStackTrace();
		}
		return res;
	}

	public MainResponse verifyOTP(MainResponse res, String otp, Connection conn) {
		try{
			try(PreparedStatement pst=conn.prepareStatement("update "+ConstantVeriables.DATABASERUSER_TWDERP+"ERP_APP_T_OTP set vstatus='U' WHERE vstatus='Y' AND nmobile_no=? and votp=?")){
				int i = 1;
				pst.setString(i++, res.getMobileno());
				pst.setString(i++, otp);
				int r=pst.executeUpdate();
				if(r>0)
				{
					res.setSuccess(true);
				}
				else
				{
					res.setSuccess(false);
					ServerError error=new ServerError();
					error.setError(ConstantVeriables.ERROR_006);
					error.setMsg("Wrong OTP");
					res.setSe(error);
				}
					
			}
		} catch (Exception e) {
			res.setSuccess(false);
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("OTP "+e.getMessage());
			res.setSe(error);
			e.printStackTrace();
		}
		return res;
	}

	public MainResponse checkAppUpdate(String chit_boy_id,MainResponse appversionRes, String versionId, String accessType, Connection conn) {
		//appversionRes.setUpdate(false);
		try {
			String nuserRoldId = "-1";
			try(PreparedStatement pst=conn.prepareStatement("select t.nuser_role_id from twderp.erp_m_user t where t.nuser_name=?"))
			{
				pst.setString(1, chit_boy_id);
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						nuserRoldId = rs.getString("nuser_role_id");
					}
				}
			}
		try(PreparedStatement pst=conn.prepareStatement(" select t.vversionname,t.vupdate,t.vcomplusory,t.vhead,t.vmessage from twderp.ERP_APP_T_VERSION t where t.nversionid=?  and t.napp_id=? and (t.nrole_id=-1 or t.nrole_id=?)"))
		{
			pst.setString(1, versionId);
			pst.setString(2, accessType);
			pst.setString(3, nuserRoldId);
			try(ResultSet rs=pst.executeQuery())
			{
				if(rs.next())
				{
					AppUpdate update=new AppUpdate();
					update.setForceUpdate(rs.getString("vcomplusory").equalsIgnoreCase("Y"));
					update.setHead(rs.getString("vhead"));
					update.setMessage(rs.getString("vmessage"));
					try(FileInputStream inputStream = new FileInputStream("D:\\3WD_Software\\app\\appurl.txt")) {     
					    String everything = IOUtils.toString(inputStream);
					    update.setUpdateUrl(everything);
					}
					appversionRes.setUpdateResponse(update);
					appversionRes.setUpdate(rs.getString("vcomplusory").equalsIgnoreCase("Y") || rs.getString("vupdate").equalsIgnoreCase("Y"));
				}else
				{
					appversionRes.setUpdate(false);
				}
			}
		}
		} catch (Exception e) {
			appversionRes.setSuccess(false);
			ServerError error=new ServerError();
			error.setError(ConstantVeriables.ERROR_006);
			error.setMsg("Check Update "+e.getMessage());
			appversionRes.setSe(error);
			e.printStackTrace();
		}
		return appversionRes;
	}

	

}
