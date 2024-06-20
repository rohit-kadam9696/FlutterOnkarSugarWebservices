package com.twd.flutter.android.constant;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class ConstantVeriables {
	public static final String MS_T_SUG_CARD_HEADER = "MS_T_SUG_CARD_HEADER";//APP_
	public static final String DATABASERUSER_TWDERP = "twderp.";//APP_	
	public static final String DATABASERUSER_INV = "inv.";
	public static String DATABASERUSER_AGRI="agri.";
	public static final String lblSugar = "साखर";
	public static final String lblSugarFor = "करीता";
	public static final String lblUserName = "कर्मचारी";
	public static final String lblAmount = "रक्कम";
	public static final String lblSugarKg = "साखर किलो";
	public static final String lblFarmerCodeAndName = "कोड व नांव";
	public static final String lblLocation = "ठिकाण";
	public static final String lblSugarBag = "साखर पोती";
	public static final String sugarNotReceived = "आपण साखर घेतली नाही. किंवा प्रिंटला माहिती सापडत नाही ";
	public static int otpexpiredate=1;
	public static int ERROR_001=1;
	public static int ERROR_002=2;
	public static int ERROR_003=3;
	public static int ERROR_004=4;
	public static int ERROR_005=5;
	public static int ERROR_006=6;
	public static String baseFilePath="D:\\app images/";
	
	public static String statusSubmit="विनंती";
	public static String statusApproved="मंजूर";
	public static String statusReject="नाकारले";
	
	public static String tabeAgriPlantaton="agri.app_t_plantation"; //"APP_CR_T_PLANTATION";
	public static String tabeConfirmAgriPlantaton="CR_T_PLANTATION"; //CR_T_PLANTATION
	public static String plantation="plantation/";
	public static String farmerInfo="farmerinfo/";
	public static String gadi="गाडी";
	public static String doki="डोकी ";
	public static String weightSlipTableName="agri.wb_t_weigh_slip"; //WB_T_WEIGHT_SLIP
	public static String wire="wire";
	public static String front="front";
	public static String back="back";
	public static String lblDate="तारीख";
	public static String lblPrintDate="प्रिंट दिनांक";
	public static String lblSLipNo="स्लिप नंबर";
	public static String lblPlotNo="प्लॉट नंबर";
	public static String lblFarmerName="शेतकारी नांव";
	public static String lblVilleage="गांव ";
	public static String lblVehicleType="वाहन प्रकार";
	public static String lblHarvestorName="तोडणीदार";
	public static String lblTranspoterName="वाहतूकदार";
	public static String lblVehicleNo="वाहन नंबर";
	public static String lblDistance="अंतर ";
	public static String lblShera="शेरा";
	public static String lblNetWeight="निव्वळ वजन";
	public static String lblMukadamName="मुकादम";
	public static String lblGadiwan="गाडीवान";
	public static String statusName="चालू";
	public static String confirmRegister="प्लॉट बंद झाला आणि पुढील वर्षासाठी नोंद घेतली. नवीन प्लॉट नं. ";
	public static String confirm="प्लॉट बंद झाला";
	
	// Report Field Start
	public static String vehicalType = "वाहन प्रकार";
	public static String s4to12 = "४ ते १२";
	public static String s12to8 = "१२ ते ८";
	public static String s8to4 = "८ ते ४";
	public static String total = "एकूण";
	public static String today = "आज";
	public static String yesterday = "काल";
	public static String defaultTon = "0.000";
	public static String srno = "अ.क्र.";
	public static String details = "तपशील";
	public static String todate = "आज अखेर";
	public static String reportnotfilled = "अहवाल तयार झालेला नाही";
	public static String crushing = "गाळप";
	public static String sugarBags = "साखर पोती";
	public static String recoveryPer = "उतारा % ऊस";
	public static String polPer = "पोल % ऊस";
	public static String losses = "लॉसेस";
	public static String canewaterPer = "पाणी % ऊस";
	public static String fibreWaterPer = "पाणी % फायबर";
	public static String bagassePol = "बगॅस पोल";
	public static String moisturePol = "बगॅस मोईश्चर";
	public static String workingHrs = "मिल चालू तास";
	public static String shift = "शिफ्ट";
	public static String rightArrow = " ->";
	public static String hangam = "हंगाम";
	public static String variety = "ऊस जात";
	public static String section = "गट";
	public static String croptype = "ऊस प्रकार";
	public static String registerArea = "नोंद क्षेत्र";
	public static String expectedTonnage = "अंदाजे टनेज";
	public static String harvestedArea = "तुटलेले क्षेत्र";
	public static String receivedTonnage = "आलेले टनेज";
	public static String remainingArea = "शिल्लक क्षेत्र";
	public static String expectedreceivedTonnage = "येणारे अं. टनेज";
	public static String valaka = "वाळका";
	public static String numbers = "संख्या";
	public static String regfarmer = "नोंदणीकृत शेतकरी";
	public static String regplot = "नोंदणीकृत प्लॉट्स ";
	public static String confirmplot = "रूजुवात झालेले प्लॉट्स";
	public static String nonconfirmplot = "रूजुवात पेंडिंग प्लॉट्स";
	public static String regarea = "नोंदणीकृत क्षेत्र";
	public static String confirmarea = "रूजुवातीचे क्षेत्र";
	public static String cancelarea = "मोडलेले क्षेत्र";
	public static String pendingarea = "रूजुवात पेंडिंग क्षेत्र";
	public static String nondarea = "नोंदणीचे क्षेत्र";
	public static String confirmpending = "रूजुवात पेंडिंग";
	public static String increasedarea = "वाढलेले क्षेत्र";
	public static String actualarea = "प्रत्यक्षात क्षेत्र";
	public static String area = "क्षेत्र";
	public static String expTonnage = "अं. टनेज";
	public static String village="गांव";
	public static String actualnond = "प्रत्यक्षात नोंदलेले";
	public static String crushingDone = "गाळप झालेले";
	public static String otherFactoryCrushing = "इतरत्र गेलेले";
	public static String forage = "चाऱ्यास";
	public static String sugarBeans = "ऊसबेणेस";
	public static String totalRemaing = "एकूण शिल्लक";
	public static String tonnage = "टनेज";
	public static String lblFrontTailer = "पुढील";
	public static String lblBackTailer = "मागील";
	public static String lblWireRope = "वायररोप";
	public static String fromTimeRawana="00:00";
	public static String toTimeRawana="23:59";
	public static String fdate="दिनांक पासून ";
	public static String tdate="दिनांक पर्यंत";
	public static String status="स्थिती";
	public static String active="सक्रिय";
	public static String deactive="निष्क्रिय";
	public static String yantrana = "यंत्रणा";
	public static String dileli = "दिलेली";
	public static String before_4_ravana = "४ पूर्वी";
	public static String after_4_ravana = "४ नंतर";
	public static String band_yantrana = "बंद यंत्रणा";
	
	public static String rawanaTractor = "रवाना ट्रॅक्टर";
	public static String rawanaBajat = "रवाना बजाट";
	public static String rawanaTodniYantra = "रवाना तोडणी यंत्र वाहने";
	public static String factoryName = "जरंडेश्वर सुगर";
	public static String usRawanaReportHead = "दि. %1$s रोजची ऊस अवाक";
	public static String finalTotal = "एकंदर एकूण";
	// Report Field End
	public static String lblToDateInward="आज अखेर आवक क्विंटल";
	public static String lblToDateSales="आज अखेर विक्री क्विंटल";
	public static String lblToDateSalesBag="आज अखेर साखर पोती विक्री";
	public static String lblToDateRemaning="आज अखेर शिल्लक क्विंटल";
	public static String lblToDatefarmerCount="आज अखेर सभासद संख्या";
	public static String lblToDateSaleAmount="आज अखेर विक्री रक्कम";
	public static String lblToDaySugarSale="आजची विक्री क्विंटल";
	public static String lblToDaySalesBag="आजची साखर पोती विक्री";
	public static String lblToDayfarmerCount="आजचे सभासद";
	public static String lblToDaySaleAmount="आजची विक्री रक्कम रुपये ";
	public static String lblDailyInwardReportHeading="दैनिक जावक";
	
	public static String lblSectionName="गटाचे नाव";
	public static String lblToDaySugarSale1="आज";
	public static String lblToDayFarmerQty="आज सभासद  संख्या";
	public static String lblToDateSugarSale="आजअखेर";
	public static String lblToDateFarmerQty="आज अखेर सभासद  संख्या";
	public static String lblSrno="अ.नं.";
	public static String lblDailySugarSaleReportHeading="सभासद साखर वाटप रिपोर्ट दि .";
	public static String lblDaysCountHeading="वाटप दिवस";
	public static String lblInward="आवक";//(क्विं.)
	public static String lblSale="वाटप"; //(क्विं.)
	public static String lblCount="संख्या ";
	public static String month = "महिना";
	public static String n6 = "06";
	public static String n7 = "07";
	public static String n8 = "08";
	public static String n9 = "09";
	public static String n10 = "10";
	public static String n11 = "11";
	public static String n12 = "12";
	public static String n1 = "01";
	public static String n2 = "02";
	public static String n3 = "03";
	public static String n4 = "04";
	public static String n5 = "05";
	public static String wa = " वा";
	public static String la = " ला";
	public static String ra = " रा";
	public static String tha = " था";
	public static String totalharvesting ="एकूण तुटणारे";
	public static String totalCrushing = "एकूण गाळप";
	public static String otherCrushing = "इतरत्र गाळप";
	public static String charavbene = "चारा व बेणेस";
	public static String totalVilhewat = "एकूण विल्हेवाट";
	public static String lbllot="लॉट नं.";
	public static String lblMukadamcode="मुकादम कोड";
	public static String lblbtcode="गाडीवान कोड";
	public static String lblbtname="गाडीवान नाव";
	public static String lbltranspoterCode="वाहतूकदार कोड";
	public static String lblTokanNo="टोकन नं";
	public static String lblnumber="नंबर";
	public static String lblnumbertiming="नंबरची दिनांक व वेळ";
	public static String lblfirstlot="प्रथम  लॉट";
	public static String lblmobile="मोबाईल नं.";
	public static String lblStatus="स्थिती";
	public static String lblRemark="रिमार्क";
	public static String lblActionbyName="कारवाई करणाऱ्याचे नाव";
	public static String lblRequestbyName="विनंती करणाऱ्याचे नाव";
	public static String lblrequsted="विनंती केली";
	public static String lblaccepted="स्वीकारले";
	
	
	
	public static final String lblRemaning = "शिल्लक";
	public static final String rujuwatpending = "रुजुवात पेंडिंग ";
	public static final String modtodarea = "मोडलेले क्षेत्र";
	public static final String vadhalelarea = "वाढलेले क्षेत्र";
	public static final String cutarea = "तुटणारे क्षेत्र";
	public static final String cutareaShort = "तु क्षेत्र";
	public static final String aprotommage = "अंदाजे टनेज";
	public static final String lblfarmerCode = "शेतकरी कोड";
	public static final String lblVilleageSHivar = "शिवार  नाव";
	public static final String lblhangam = "हंगाम";
	public static final String lblvariety = "ऊस जात";
	public static final String lblgrosswt = "वजन";
	public static final String lblemptywt = "मोकळे वजन ";
	public static final String lblnetwt = "निव्वळ वजन ";
	public static final String lblrejected = "नाकारले";
	public static final String QRCode = "QR कोड";
	public static String errorNFCdataNotFound="या कार्ड ची NFC माहिती भरलेली नाही. कृपया NFC माहिती भरून पुन्हा KYC साठी यावे .";
	public static String yes="होय";
	public static String no="नाही";
	public static String lblCode="कोड";
	public static String lblName="नाव";
	public static String shares="शेअर्स";
	public static String lblkycdate = "के.वाय.सी. दिनांक";
	public static String kycFilePath=baseFilePath+"kycimage/";
}
