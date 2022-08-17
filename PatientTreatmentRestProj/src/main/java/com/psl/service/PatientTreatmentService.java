package com.psl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.psl.entity.PatientTreatment;
import com.psl.repository.IPatientTreatmetRepo;

@Service
public class PatientTreatmentService implements IPatientTreatmentService {
	@Autowired
	private IPatientTreatmetRepo repo;
	
	public Iterable<PatientTreatment> getAllPatientTreatmentInfo(){
		return repo.findAll();
	}//getAllPatientTreatmentInfo
	
	public PatientTreatment getPatientTreatmentInfoById(Integer id) throws Exception {	
		return repo.findById(id).orElseThrow(()->  new Exception("PatientTreatment Not found by id : "+id));
	}//getPatientTreatmentById	

	public PatientTreatment registerPatientTreatmentInfo(PatientTreatment pt) throws Exception {	
		if(pt == null)
			throw new Exception("PatientTreatment Info is NULL");
		return repo.save(pt);
	}//getPatientTreatmentById	
	
	public void deletePatientTreatmentInfoById(Integer id) {	
		repo.deleteById(id);
	}//deletePatientTreatmentInfoById
	
	public void deleteAllPatientTreatmentInfo() {	
		repo.deleteAll();
	}//deletePatientTreatmentInfo
}//class
