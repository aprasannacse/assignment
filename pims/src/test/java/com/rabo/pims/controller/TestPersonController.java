package com.rabo.pims.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rabo.pims.TestBaseAbstract;
import com.rabo.pims.dto.PersonReqDTO;
import com.rabo.pims.dto.PersonRespDTO;
import com.rabo.pims.entity.PersonEntity;
@SpringBootTest

class TestPersonController extends TestBaseAbstract{

	@Autowired
	private PersonController objPersonCtrlr;
	
	MvcResult mvcResult;
	int status;
	String content;
	String uri;
    
    
    @Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		
		mvcResult=null;	
		status=0;
		content=null;
		uri=null;
	}
    
   
	
	@Test
	void contextLoads() {
		assertThat(objPersonCtrlr).isNotNull();
	}
	
  	
	@DisplayName("PersonController-testAddPerson()")
    @Test
		public void testAddPerson() throws Exception {
			uri = "/person";
			
			PersonReqDTO objPersonEntity=new PersonReqDTO();
			objPersonEntity.setFirstName("Ajith");
			objPersonEntity.setLastName("AK");
			objPersonEntity.setDob("11-Jan-1950");
			objPersonEntity.setCurrentAddress("Delhi");
			String requestJSON=super.mapToJson(objPersonEntity);
			
			mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJSON)).andReturn();
			status = mvcResult.getResponse().getStatus();
			assertEquals(201, status);			
		}
	@DisplayName("PersonController-testAddPersonAlreadyExists()")
    @Test
		public void testAddPersonAlreadyExists() throws Exception {
			uri = "/person";
			
			PersonReqDTO objPersonEntity=new PersonReqDTO();
			objPersonEntity.setFirstName("Prasanna");
			objPersonEntity.setLastName("A");
			objPersonEntity.setDob("20-Nov-2010");
			objPersonEntity.setCurrentAddress("Tirchy");
			String requestJSON=super.mapToJson(objPersonEntity);
			
			mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJSON)).andReturn();
			status = mvcResult.getResponse().getStatus();
			assertEquals(406, status);			
		}
		
	
	
	@DisplayName("PersonController-testGetAllPersons()")
    @Test
	public void testGetAllPersons() throws Exception {
		uri = "/persons";
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		content = mvcResult.getResponse().getContentAsString();
		PersonRespDTO[] objRespList = super.mapFromJson(content, PersonRespDTO[].class);
		assertTrue(objRespList.length>0);
	}
	
	
	
	@DisplayName("PersonController-testGetPersonById()")
    @Test
	public void testGetPersonById() throws Exception {

		Integer id=1; //Input Data Here
		uri = "/person/"+id;
		
		PersonRespDTO[] objRespList =null;
		
		
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();			

		status = mvcResult.getResponse().getStatus();						
		content = mvcResult.getResponse().getContentAsString();
		objRespList = super.mapFromJson(content, PersonRespDTO[].class);
		
		assertEquals(200, status);
		assertTrue(objRespList.length>0);		

	}
	
	@DisplayName("PersonController-testGetPersonByIdNotExist()")
    @Test
	public void testGetPersonByIdNotExist() throws Exception {

		Integer id=1000; //Input Data Here
		uri = "/person/"+id;
		
	
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		status = mvcResult.getResponse().getStatus();		
		assertEquals(404, status);
	}
	
	@DisplayName("PersonController-testGetPersonByName()")
    @Test
	public void testGetPersonByName() throws Exception {

		String firstName="Siva"; //Input Data Here
		String lastName="A"; //Input Data Here

		uri = "/person?firstName="+firstName+"&lastName="+lastName;
		
		PersonRespDTO[] objRespList =null;
			
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();			
		
		status = mvcResult.getResponse().getStatus();						
		content = mvcResult.getResponse().getContentAsString();
		objRespList = super.mapFromJson(content, PersonRespDTO[].class);		
		assertEquals(200, status);
		assertTrue(objRespList.length>0); 		

	}
	
	//Test case to test get Person details by FirstName or LastName
	@DisplayName("PersonController-testGetPersonBySingleName()")
    @Test
	public void testGetPersonBySingleName() throws Exception {

		String firstName="Siva"; //Input Data Here
		String lastName=""; 

		uri = "/person?firstName="+firstName+"&lastName="+lastName;
		
		PersonRespDTO[] objRespList =null;
			
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();			
		
		status = mvcResult.getResponse().getStatus();						
		content = mvcResult.getResponse().getContentAsString();
		objRespList = super.mapFromJson(content, PersonRespDTO[].class);		
		assertEquals(200, status);
		assertTrue(objRespList.length>0); 		

	}
	
	@DisplayName("PersonController-testGetPersonByNameNotExists()")
    @Test
	public void testGetPersonByNameNotExists() throws Exception {

		String firstName="XXX"; //Input Data Here
		String lastName="XXX"; //Input Data Here

		uri = "/person?firstName="+firstName+"&lastName="+lastName;
			
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();			
		
		status = mvcResult.getResponse().getStatus();						
		content = mvcResult.getResponse().getContentAsString();
		System.out.println("Inside TEST CASE"+content); 
		assertEquals(204, status);

	}
	
	
	@DisplayName("PersonController-testUpdatePersonAddress()")
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN") // Providing ADMIN credentials to test spring security enabled Endpoint
		public void testUpdatePersonAddress() throws Exception {
			
			int id=3;// Input Mock Data here
			
			uri = "/person/"+id;
			PersonEntity objPersonEntity=new PersonEntity();
			
			objPersonEntity.setCurrentAddress("Chennai 600046");
			
			String requestJSON=super.mapToJson(objPersonEntity);
			mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJSON)).andReturn();
			status = mvcResult.getResponse().getStatus();
			assertEquals(200, status);			
		}
	
	
	

}
