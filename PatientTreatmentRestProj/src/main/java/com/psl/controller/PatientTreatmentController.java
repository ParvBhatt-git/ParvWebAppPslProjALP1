package com.psl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.psl.entity.PatientTreatment;
import com.psl.entity.User;
import com.psl.model.JwtRequest;
import com.psl.model.JwtResponse;
import com.psl.service.IPatientTreatmentService;
import com.psl.service.*;
import com.psl.utility.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//http://localhost:5555/swagger-ui.html#/							:SWAGGER
//http://localhost:5555/patientTretmentInfo/find/all		:FIND ALL RECORD

@RestController
@RequestMapping("/patientTretmentInfo")
@Api("All about PatientTreatment")
public class PatientTreatmentController {
	private static Logger LOGGER = LoggerFactory.getLogger(PatientTreatmentController.class);

	@Autowired
	private IPatientTreatmentService service;
	
	public static final String DESTINATION_NAME = "read";

	@Autowired
	private JWTUtil jwtUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@GetMapping("/")
	public String home() {
		return "In HOME";
	}


	@ApiOperation("To retrive all patient treatment information.")
	@GetMapping("find/all")
	public ResponseEntity<?> displayAllPatientTretmentInfo(){
		try {
			LOGGER.info("Method displayAllPatientTretmentInfo Called.");
			return new ResponseEntity<Iterable<PatientTreatment>>(service.getAllPatientTreatmentInfo(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//displayAllPatientTretmentInfo


	@ApiOperation("To retrive patient treatment information by given Id.")
	@GetMapping("find/{id}")
	public ResponseEntity<?> displayPatientTreatmentById(@PathVariable Integer id){
		System.out.println("In the find by id method");
		try {
			LOGGER.info("Method displayPatientTreatmentById Called with ID :  "+id+".");
			return new ResponseEntity<PatientTreatment>(service.getPatientTreatmentInfoById(id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//displayPatientTreatmentById


	@ApiOperation("To register patient treatment information.")
	@PostMapping("/save")
	public ResponseEntity<?> registerPatientTreatmentInfo(@RequestBody PatientTreatment pt){
		System.out.println("in the save method");
		try {
			LOGGER.info("Method registerPatientTreatmentInfo Called.");
			return new ResponseEntity<PatientTreatment>(service.registerPatientTreatmentInfo(pt),HttpStatus.CREATED);			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//registerPatientTreatmentInfo

	@ApiOperation("To update patient treatment information.")
	@PostMapping("/update/{id}")
	public ResponseEntity<?> updatePatientTreatmentInfo(@PathVariable Integer id, @RequestBody PatientTreatment pt){
		try {
			LOGGER.info("Method updatePatientTreatmentInfo Called.");
			if(service.getPatientTreatmentInfoById(id)!=null) {
				pt.setId(id);
				return new ResponseEntity<PatientTreatment>(service.registerPatientTreatmentInfo(pt),HttpStatus.CREATED);
			}else {
				throw new Exception("Id Not Found! :: "+id);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//UpdatePatientTreatmentInfo


	@ApiOperation("To hard delete patient treatment information by given Id.")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePatientTreatmentInfoById(@PathVariable Integer id){
		try {
			LOGGER.info("Method deletePatientTreatmentInfoById Called.");
			service.deletePatientTreatmentInfoById(id);
			return new ResponseEntity<String>("PatientTratmentInfo deleted by id: "+id,HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//deletePatientTreatmentInfoById
	
	@ApiOperation("To hard delete All patient treatment information.")
	@DeleteMapping("/delete/all")
	public ResponseEntity<String> deleteAllPatientTreatmentInfoById(){
		try {
			LOGGER.info("Method deleteAllPatientTreatmentInfo Called.");
			service.deleteAllPatientTreatmentInfo();
			return new ResponseEntity<String>("All PatientTratmentInfo deleted.",HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//deletePatientTreatmentInfoById

	@PostMapping("/authenticate")
	public  JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							jwtRequest.getUsername(), 
							jwtRequest.getPassword())
					);				
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Credentials",e);
		}
			
		final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
		final String token = JWTUtil.generateToken("07032001", userDetails.getUsername());
		
		return new JwtResponse(token);
	}
	
	@PostMapping("/messages")
	public String postMessage(@RequestParam String message) {
		 LOGGER.info("Sending Message");
	     jmsTemplate.convertAndSend(DESTINATION_NAME, new User(message));
	     return message;
	}	
}//class
