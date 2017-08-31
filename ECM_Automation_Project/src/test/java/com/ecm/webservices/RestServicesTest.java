package com.ecm.webservices;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecm.util.BaseUtil;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.*;

public class RestServicesTest extends BaseUtil {


	@Test
	private void Test_01() {
		Response resp = when().
				get("http://be-tms-web.causeway.local/icons/api/v1/assetmarker/png?motif=bicycle&colour=%230000FF&size=64&heading=55&selected=true");
		System.out.println(resp.getStatusCode());
		Assert.assertEquals(resp.getStatusCode(), 200);
	}
	
	@Test
	public void Test_02(){

		Response resp= when().
						get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=786a126946906853fd4b9850156a6961");
		System.out.println(resp.getStatusCode());
		Assert.assertEquals(resp.getStatusCode(), 200);
		System.out.println(resp.asString());

	}

}
