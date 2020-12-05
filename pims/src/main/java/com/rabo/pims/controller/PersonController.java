
package com.rabo.pims.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.pims.dto.PersonReqDTO;
import com.rabo.pims.dto.PersonRespDTO;
import com.rabo.pims.exception.ErrorResponse;
import com.rabo.pims.exception.PersonAlreadyExistException;
import com.rabo.pims.exception.PersonNotFoundException;
import com.rabo.pims.service.PersonServiceImpl;
import com.rabo.pims.utils.DataUtils;
import com.rabo.pims.utils.Log4jManager;


@RestController
public class PersonController 
{

	@Autowired
	private PersonServiceImpl pmSerObj;

	HttpStatus httpStatusCode = null;
	ErrorResponse error = null;
	List<String> details = new ArrayList<>();

	@PostMapping(value = "/person")
	public ResponseEntity<PersonRespDTO> addPerson(@RequestBody PersonReqDTO personReqEntity )
	{
		PersonRespDTO respEnity =null;
		try {
			Log4jManager.writeDebugLog("Invoked PersonController:addPerson()");
			respEnity = pmSerObj.addPerson(personReqEntity);

			if(!(respEnity == null)) {
				httpStatusCode= HttpStatus.CREATED;
			}else {
				httpStatusCode= HttpStatus.NO_CONTENT;
			}
		}
		catch (PersonAlreadyExistException p) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(p));
			httpStatusCode= HttpStatus.NOT_ACCEPTABLE;			
			details.add(p.getLocalizedMessage());
			error = new ErrorResponse(p.getMessage(), details);
			return new ResponseEntity(error, httpStatusCode);
		}
		catch (Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.INTERNAL_SERVER_ERROR;
		}

		Log4jManager.writeDebugLog("PersonController:addPerson()|Response Status Code"+httpStatusCode); 
		return new ResponseEntity<PersonRespDTO>(respEnity, httpStatusCode);
	}

	@GetMapping(value = "/persons",produces = {"application/json"} )	
	public ResponseEntity<List<PersonRespDTO>> getAllPersons(){
		List<PersonRespDTO> objPersonList=null;
		Log4jManager.writeDebugLog("Invoked PersonController:getAllPersons()");

		try {
			objPersonList= pmSerObj.getAllPersons();

			if(!(objPersonList == null || objPersonList.isEmpty())) {
				httpStatusCode= HttpStatus.OK;
			}else{
				httpStatusCode= HttpStatus.NO_CONTENT;
			}

		}catch(PersonNotFoundException p) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(p)); //
			httpStatusCode=HttpStatus.NOT_FOUND; 			
			details.add(p.getLocalizedMessage());
			error = new ErrorResponse(p.getMessage(), details);
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			e.printStackTrace();
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.INTERNAL_SERVER_ERROR;
		}
		Log4jManager.writeDebugLog("PersonController:getAllPersons()|Response Status Code"+httpStatusCode); 

		return new ResponseEntity<>(objPersonList, HttpStatus.OK);

	}

	@GetMapping(value = "/person/{personId}",produces = {"application/json"})	
	public ResponseEntity<PersonRespDTO> getPersonById(@PathVariable Integer personId) 
	{
		PersonRespDTO objRespEntity = null;
		HttpStatus httpStatusCode=null;
		try {
			Log4jManager.writeDebugLog("Invoked PersonController:getPersonById()");
			Log4jManager.writeDebugLog("PersonController:getPersonById()|Request Id"+personId); 

			objRespEntity = pmSerObj.getPersonById(personId);

			if(!(objRespEntity == null)) {
				httpStatusCode= HttpStatus.OK;
			}else {
				httpStatusCode= HttpStatus.NO_CONTENT;
			}
		} 
		catch(PersonNotFoundException p) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(p)); //
			httpStatusCode=HttpStatus.NOT_FOUND; 			
			details.add(p.getLocalizedMessage());
			error = new ErrorResponse(p.getMessage(), details);
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.INTERNAL_SERVER_ERROR;			
		}
		Log4jManager.writeDebugLog("PersonController:getPersonById()|Response Status Code"+httpStatusCode); 

		return new ResponseEntity<PersonRespDTO>(objRespEntity, httpStatusCode);

	}


	@PutMapping(value="/person/{personId}",produces = {"application/json"})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PersonRespDTO> updatePersonAddress(@PathVariable int personId,@RequestBody PersonReqDTO personReqEntity) 
	{
		PersonRespDTO objRespEntity =null;
		HttpStatus httpStatusCode=null;

		try {

			Log4jManager.writeDebugLog("Invoked PersonController:updatePersonAddress()");
			Log4jManager.writeDebugLog("PersonController:updatePersonAddress()|Request Id"+personId);

			objRespEntity = pmSerObj.updateAddress(personReqEntity,personId);

			if(!(objRespEntity == null)) {
				httpStatusCode= HttpStatus.OK;
			}

		}catch(PersonNotFoundException p) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(p)); //
			httpStatusCode=HttpStatus.NOT_FOUND; 			
			details.add(p.getLocalizedMessage());
			error = new ErrorResponse(p.getMessage(), details);
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.INTERNAL_SERVER_ERROR;
		}

		Log4jManager.writeDebugLog("PersonController:updatePersonAddress()|Response Status Code"+httpStatusCode);
		return new ResponseEntity<PersonRespDTO>(objRespEntity,httpStatusCode);

	}

	@GetMapping(value = "/person",produces = {"application/json"})
	public ResponseEntity<List<PersonRespDTO>> getPersonByName(
			@RequestParam(value="firstName",required=false, defaultValue="") String firstName,
			@RequestParam(value="lastName",required=false, defaultValue="")  String lastName) 
	{

		List<PersonRespDTO> objPersonList=null;
		HttpStatus httpStatusCode=null;

		Log4jManager.writeDebugLog("Invoked PersonController:getPersonByName()");
		Log4jManager.writeDebugLog("PersonController:getPersonByName()|Request firstName  "+firstName+" lastName  "+lastName);

		try {
			objPersonList= pmSerObj.getPersonByName(firstName,lastName);

			if(!(objPersonList == null || objPersonList.isEmpty())) {
				httpStatusCode= HttpStatus.OK;
			}else {
				httpStatusCode= HttpStatus.NO_CONTENT;
			}
		}catch(PersonNotFoundException p) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(p)); //
			httpStatusCode=HttpStatus.NOT_FOUND; 			
			details.add(p.getLocalizedMessage());
			error = new ErrorResponse(p.getMessage(), details);
			return new ResponseEntity(error, httpStatusCode);
		}catch(Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			httpStatusCode=HttpStatus.INTERNAL_SERVER_ERROR;
		}

		Log4jManager.writeDebugLog("PersonController:getPersonByName()|Response Status Code"+httpStatusCode);
		return new ResponseEntity<>(objPersonList, httpStatusCode);

	}

}

