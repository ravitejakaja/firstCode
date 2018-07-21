package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.howtodoinjava.schemas.school.StudentDetailsRequest;
import com.example.howtodoinjava.schemas.school.StudentDetailsResponse;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
public class OTPcontroller {
	
  @Autowired
  SOAPConnector soap;
	
 private Map<String,OTPsystem> otp_data= new HashMap();
 private final static String ACCOUNT_ID="ACf963c0c4d2b3a868ec5bf1513feababc";
 private final static String AUTH_ID="bfac391bcdb5c75fbffe9e180b67af9b";		 
 
 static {
	 Twilio.init(ACCOUNT_ID,AUTH_ID);
 }
 @RequestMapping(value="/mobilenumber/{mobilenumber}/otp", method=RequestMethod.POST)
 public ResponseEntity<Object> sendOTP(@PathVariable("mobilenumber") String mobilenumber){
	 String name = "Sajal";//Default Name

	 StudentDetailsRequest request = new StudentDetailsRequest();
     request.setName(name);
     StudentDetailsResponse response =(StudentDetailsResponse) soap.callWebService("http://localhost:8080/service/student-details", request);
     System.out.println("Got Response As below ========= : ");
     System.out.println("Name : "+response.getStudent().getName());
     System.out.println("Standard : "+response.getStudent().getStandard());
     System.out.println("Address : "+response.getStudent().getAddress());

	 
	 OTPsystem otpsystem= new OTPsystem();
	 otpsystem.setMobileNumber("9704086509");
	 otpsystem.setOtp(String.valueOf(((int)(Math.random()*(10000-1000)))+1000));
	 otpsystem.setExpTime(System.currentTimeMillis()+2000);
	 otp_data.put(mobilenumber, otpsystem);
	 Message.creator(new PhoneNumber("+919704086509"), new PhoneNumber("+12569065509"),"your OTP is"+otpsystem.getOtp());
	 System.out.println(otpsystem.getOtp());
	 return new ResponseEntity<>("OTP send Sucesses",HttpStatus.OK);
 }
 
 
}
