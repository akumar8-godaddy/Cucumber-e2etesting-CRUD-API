package search;

import Order.OrderEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import okhttp3.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.io.IOException;
import java.util.List;
import java.util.Map;



public class MyStepdefs {


    OrderEntity newOrder = new OrderEntity();
    OkHttpClient client = new OkHttpClient();
    String apiUrl ;
    Response response;
    String responseBody;
    ObjectMapper objectMapper = new ObjectMapper();

    @Given("I have a valid API endpoint for create")
    public void iHaveAValidAPIEndpointForCreate() {
        apiUrl = "http://localhost:8080/addOrder";
    }
    @When("I send a POST request to create a resource with resource details as below")
    public void iSendAPOSTRequestToCreateAResourceWithResourceDetailsAsBelow(Map<String, List<String>> data) throws IOException {

        List<String> orderID = data.get("orderID");
        List<String> productID = data.get("productID");
        List<String> customerID = data.get("customerID");
        List<String> customerName = data.get("customerName");
        List<String> customerAddress = data.get("customerAddress");
        List<String> subtotal = data.get("subTotal");
        newOrder.setOrderID(orderID.get(0));
        newOrder.setProductID(productID.get(0));
        newOrder.setCustomerID(customerID.get(0));
        newOrder.setCustomerName(customerName.get(0));
        newOrder.setCustomerAddress(customerAddress.get(0));
        newOrder.setSubtotal(Integer.parseInt(subtotal.get(0)));
        String requestBody = objectMapper.writeValueAsString(newOrder);
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .build();
        response = client.newCall(request).execute();
         responseBody = response.body().string();
    }

    @Then("the response body should contain the string {string}")
    public void theResponseBodyShouldContainTheCreatedResourceDetails(String response) {
        assertEquals(response, responseBody);
    }


    @Given("I have a valid API endpoint for get")
    public void iHaveAValidAPIEndpointForGet() {
        apiUrl = "http://localhost:8080";
    }

    @When("I send a GET request to read a order with orderID as {string} and customerID as {string}")
    public void iSendAGETRequestToReadAOrderWithOrderIDAsAndCustomerIDAs(String orderID, String customerID) {
    apiUrl += "/orderID/"+orderID+"/customerID/"+customerID;
        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .build();
        try {
            response = client.newCall(request).execute();
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Then("the response body should contain the order with orderID as {string} and customerID as {string}")
    public void theResponseBodyShouldContainTheOrderWithOrderIDAsAndCustomerIDAs(String orderID, String customerID) throws JsonProcessingException {

        List<Map<String, Object>> maps = objectMapper.readValue(responseBody, new TypeReference<List<Map<String, Object>>>(){});
            assertEquals(orderID, maps.get(0).get("orderID"));
            assertEquals(customerID, maps.get(0).get("customerID"));
    }


    @Given("I have a valid API endpoint for delete")
    public void iHaveAValidAPIEndpointForDelete() {
        apiUrl = "http://localhost:8080/delete";
    }

    @When("I send a DELETE request to delete a order with orderID as {string} and customerID as {string}")
    public void iSendADELETERequestToDeleteAOrderWithOrderIDAsAndCustomerIDAs(String orderID, String customerID) {
        apiUrl += "/orderID/"+orderID+"/customerID/"+customerID;
        Request request = new Request.Builder()
                .url(apiUrl)
                .delete()
                .build();
        try {
            response = client.newCall(request).execute();
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("the response body of delete request should contain the string {string}")
    public void theResponseBodyOfDeleteRequestShouldContainTheString(String response) {
        assertEquals(responseBody, response);
    }

    @And("the response body should not contain the order with orderID as {string} and customerID as {string} when I send a GET request should get empty list\"")
    public void theResponseBodyShouldNotContainTheOrderWithOrderIDAsAndCustomerIDAsWhenISendAGETRequestShouldGetEmptyList(String orderID, String customerID) throws Throwable {
        apiUrl = "http://localhost:8080/orderID/"+orderID+"/customerID/"+customerID;
        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .build();
        try {
            response = client.newCall(request).execute();
            responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("[]", responseBody);
}

    @Given("I have a valid API endpoint for update")
    public void iHaveAValidAPIEndpointForUpdate() {
        apiUrl = "http://localhost:8080/update";
    }

    @When("I send a PUT request to update a order with orderID as {string} and customerID as {string} with new details as below")
    public void iSendAPUTRequestToUpdateAOrderWithOrderIDAsAndCustomerIDAsWithNewDetailsAsBelow(String orderID, String customerID, Map<String, List<String>> data) throws IOException {
        List<String> productID = data.get("productID");
        List<String> customerName = data.get("customerName");
        List<String> customerAddress = data.get("customerAddress");
        List<String> subtotal = data.get("subTotal");
        newOrder.setOrderID(orderID);
        newOrder.setCustomerID(customerID);
        newOrder.setProductID(productID.get(0));
        newOrder.setCustomerName(customerName.get(0));
        newOrder.setCustomerAddress(customerAddress.get(0));
        newOrder.setSubtotal(Integer.parseInt(subtotal.get(0)));
        String requestBody = objectMapper.writeValueAsString(newOrder);
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(apiUrl)
                .put(body)
                .build();
        response = client.newCall(request).execute();
        responseBody = response.body().string();

    }

    @Then("the response body should contain the order with orderID as {string} and customerID as {string} with updated details i.e name should be {string} and subTotal should be {string}")
    public void theResponseBodyShouldContainTheOrderWithOrderIDAsAndCustomerIDAsWithUpdatedDetailsIENameShouldBeAndSubTotalShouldBe(String orderID, String customerID, String customerName, String subtotal) throws JsonProcessingException {
        Map<String, Object> map = objectMapper.readValue(responseBody, Map.class);
        assertEquals(customerName, map.get("customerName"));
        assertEquals(subtotal, map.get("subtotal").toString());

    }
}
