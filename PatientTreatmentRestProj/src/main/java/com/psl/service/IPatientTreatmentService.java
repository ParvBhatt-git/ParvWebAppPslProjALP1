package com.psl.service;

import com.psl.entity.PatientTreatment;

public interface IPatientTreatmentService {
	//read
	public Iterable<PatientTreatment> getAllPatientTreatmentInfo();
	
	//readById
	public PatientTreatment getPatientTreatmentInfoById(Integer id) throws Exception;

	//create
	public PatientTreatment registerPatientTreatmentInfo(PatientTreatment pt) throws Exception;
	
	//delete
	public void deletePatientTreatmentInfoById(Integer id); 
	
	//deleteAll
	public void deleteAllPatientTreatmentInfo();
}//interface
