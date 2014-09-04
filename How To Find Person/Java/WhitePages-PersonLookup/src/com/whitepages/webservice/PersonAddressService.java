package com.whitepages.webservice;

import com.whitepages.data.PersonAddressLookupData;
import com.whitepages.utilities.PersonAddressLookupParser;
import com.whitepages.utilities.ServerApis;
import com.whitepages.utilities.ServerUrls;
import com.whitepages.utilities.Constants;

public class PersonAddressService {

	public PersonAddressLookupData getPersonAddressLookUpData(String firstName, String lastName, String address)
	{
		PersonAddressLookupData personAddressLookupData = null;
		
		if(firstName != null && firstName != "")
		{
			firstName = firstName.trim();
			lastName = (lastName != null && !lastName.equals("")) ? lastName.trim() : "";
			address = (address != null && !address.equals("")) ? address.trim() : "";
			
			String request = getPersonAdressLookUpRequest(firstName, lastName, address);
			HttpRestClient httpRestClient = new HttpRestClient();
			String response = httpRestClient.doGetPersonAddresslookupData(request);
			
			// Parse phone lookup data json response
			personAddressLookupData = parseDataResponse(response);
		}
		
		return personAddressLookupData;
	}
	
	/**
	 * This method create a request to get person address lookup data.
	 * @param firstName: firstName to get data.
	 * @param lastName: lastName to get data.
	 * @param address: address to get data.
	 * @return: request to get person address lookup data.
	 */
	private String getPersonAdressLookUpRequest(String firstName, String lastName, String address)
	{
		String request = null;
		String serverUrl = ServerUrls.SERVER_URL;
		String apiVersion = ServerApis.API_VERSION;
		String personAddressApi = ServerApis.PERSON_ADDRESS_API;
		String apiKey = Constants.API_KEY;
		String apiRequestParam = ServerApis.PERSON_ADDRESS_API_REQUEST_PARAM;
		apiRequestParam = apiRequestParam.replace(":first_name", firstName);
		apiRequestParam = apiRequestParam.replace(":last_name", lastName);
		apiRequestParam = apiRequestParam.replace(":address", address);
		apiRequestParam = apiRequestParam.replace(":api_key", apiKey);
		
		request = serverUrl + apiVersion + personAddressApi + "?" + apiRequestParam;
		return request;
	}
	
	private PersonAddressLookupData parseDataResponse(String jsonResponse)
	{
		PersonAddressLookupData personAddressLookupData = null;
		if(jsonResponse != null && !jsonResponse.equals(""))
		{
			PersonAddressLookupParser personAddressLookupParser = new PersonAddressLookupParser();
			personAddressLookupData = personAddressLookupParser.parsePersonAdressLookupData(jsonResponse);
		}
		
		return personAddressLookupData;
	}
}