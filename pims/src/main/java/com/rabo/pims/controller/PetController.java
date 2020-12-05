package com.rabo.pims.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.pims.dto.PetReqDTO;
import com.rabo.pims.dto.PetRespDTO;
import com.rabo.pims.exception.ErrorResponse;
import com.rabo.pims.exception.PetLinkedAlreadyException;
import com.rabo.pims.exception.PetNotAvailableException;
import com.rabo.pims.exception.PetNotFoundException;
import com.rabo.pims.service.PetServiceImpl;
import com.rabo.pims.utils.DataUtils;
import com.rabo.pims.utils.Log4jManager;

@RestController	

public class PetController {
	@Autowired
	private PetServiceImpl petService;

	HttpStatus httpStatusCode=null;
	ErrorResponse error = null;
	List<String> details = new ArrayList<>();

	@PostMapping(value = "/pet")
	public ResponseEntity<PetRespDTO> addPet(@RequestBody PetReqDTO petReqEntity){
		PetRespDTO respEnity = null;		
		try {
			Log4jManager.writeDebugLog("Invoked PetController:addPet()");
			respEnity = petService.addPet(petReqEntity);
			httpStatusCode=HttpStatus.CREATED;
		}catch(Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.INTERNAL_SERVER_ERROR;
		}
		Log4jManager.writeDebugLog("PetController:addPet()|Response Status Code"+httpStatusCode); 
		return new ResponseEntity<>(respEnity, httpStatusCode);
	}

	@GetMapping(value = "/pets",produces = {"application/json"} )
	
	public ResponseEntity<List<PetRespDTO>> getAllPets()
	{

		List<PetRespDTO> objPetsList= null;
		try {
			Log4jManager.writeDebugLog("Invoked PetController:getAllPets()");
			objPetsList= petService.getAllPets();

			if(!(objPetsList == null || objPetsList.isEmpty())) {
				httpStatusCode=HttpStatus.OK;
			}else{
				httpStatusCode= HttpStatus.NO_CONTENT;
			}
		}catch(PetNotFoundException p) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(p)); //
			httpStatusCode=HttpStatus.NOT_FOUND; 			
			details.add(p.getLocalizedMessage());
			error = new ErrorResponse(p.getMessage(), details);
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.NO_CONTENT;
		}
		Log4jManager.writeDebugLog("PetController:getAllPets()|Response Status Code"+httpStatusCode); 
		return new ResponseEntity<>(objPetsList, httpStatusCode);

	}
	@PutMapping("/pet/{petId}")
	public ResponseEntity<PetRespDTO> updatePetDetails(@PathVariable int petId,@RequestBody PetReqDTO petReqEntity) 
	{
		PetRespDTO respEnity = null;
		try {
			Log4jManager.writeDebugLog("Invoked PetController:updatePetDetails()");

			respEnity = petService.updatePetDetails(petReqEntity,petId);

			if(!(null == respEnity)) {
				httpStatusCode= HttpStatus.OK;
			}
		}catch(PetNotFoundException e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.NOT_FOUND; 			
			details.add(e.getLocalizedMessage());
			error = new ErrorResponse(e.getMessage(), details);
			return new ResponseEntity(error,httpStatusCode);
		}catch(Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.INTERNAL_SERVER_ERROR;
		}
		Log4jManager.writeDebugLog("PetController:updatePetDetails()|Response Status Code"+httpStatusCode);
		return new ResponseEntity<PetRespDTO>(respEnity, httpStatusCode);		

	}
	
	@DeleteMapping("/pet/{petId}")
	//@PreAuthorize("denyAll") // To disable this end point access, uncomment this annotation 
	public ResponseEntity<HttpStatus> deletePet(@PathVariable("petId") int petId) {
		try {
			Log4jManager.writeDebugLog("Invoked PetController:deletePet()");

			if(petService.deleteById(petId)) {
				httpStatusCode= HttpStatus.OK;
			}else {
				httpStatusCode= HttpStatus.NO_CONTENT; 
			}

		} catch (Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.INTERNAL_SERVER_ERROR;
		}
		Log4jManager.writeDebugLog("PetController:deletePet()|Response Status Code"+httpStatusCode);
		return new ResponseEntity<>(httpStatusCode);

	}

	@PostMapping(value = "/pet/{personId}")
	public ResponseEntity<PetRespDTO> linkPetToPerson(@PathVariable int personId,@RequestBody PetReqDTO petReqEntity ) 
	{
		PetRespDTO respEnity = null;
		try {
			Log4jManager.writeDebugLog("Invoked PetController:linkPetToPerson()");

			respEnity = petService.linkPetToPerson(personId,petReqEntity);
			if(!(null == respEnity)) {
			httpStatusCode= HttpStatus.CREATED;
			}

		}catch(PetLinkedAlreadyException | PetNotAvailableException e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.NOT_FOUND; 			
			details.add(e.getLocalizedMessage());
			error = new ErrorResponse(e.getMessage(), details);
			return new ResponseEntity(error,httpStatusCode);
		}catch (Exception e){
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode= HttpStatus.NO_CONTENT;
		}
		Log4jManager.writeDebugLog("PetController:linkPetToPerson()|Response Status Code"+httpStatusCode);
		return new ResponseEntity<>(respEnity,httpStatusCode);

	}

}
