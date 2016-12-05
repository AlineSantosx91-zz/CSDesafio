package br.com.cs.desafio.teste;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.model.Phone;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
public class CustomerTeste {
	
	 public static final String REST_SERVICE_URI = "http://localhost:8080/api";

  
  
  public void getHello2(){
      System.out.println("Testing getUser API----------");
      RestTemplate restTemplate = new RestTemplate();
      String message = restTemplate.getForObject(REST_SERVICE_URI+"/hello/", String.class);
      System.out.println(message);
  }
  
  @Test
/* POST */
@SuppressWarnings("unused")
public  void createUser() {
    System.out.println("Testing create User API----------");
    RestTemplate restTemplate = new RestTemplate();
    Customer customer = new Customer();
    customer.setName("João da Silva");
    customer.setEmail("joao@silva.org");
    customer.setPassword("hunter2");
    
    Phone phone1 = new Phone();
    phone1.setDdd("11");
    phone1.setNumber("123456987");
    
    Phone phone2 = new Phone();
    phone2.setDdd("21");
    phone2.setNumber("123654789");
    
    
    List<Phone> phones = new ArrayList<>();
    phones.add(phone1);
    phones.add(phone2);
    
    customer.setPhones(phones);
    
    
    URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/customers/", customer, Customer.class);
    System.out.println("Location : "+uri.toASCIIString());
}
  
  
  /* GET */
  @SuppressWarnings("unchecked")
  private static void listAllUsers(){
      System.out.println("Testing listAllUsers API-----------");
       
      RestTemplate restTemplate = new RestTemplate();
      List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/customers/", List.class);
       
      if(usersMap!=null){
          for(LinkedHashMap<String, Object> map : usersMap){
              System.out.println("User : id="+map.get("id")+", Email="+map.get("email"));
          }
      }else{
          System.out.println("No user exist----------");
      }
  }
   
  /* GET */
  private static void getUser(){
      System.out.println("Testing getUser API----------");
      RestTemplate restTemplate = new RestTemplate();
      Customer user = restTemplate.getForObject(REST_SERVICE_URI+"/customers/1", Customer.class);
      System.out.println(user);
  }
   

//
//  /* PUT */
//  private static void updateUser() {
//      System.out.println("Testing update User API----------");
//      RestTemplate restTemplate = new RestTemplate();
//      User user  = new User(1,"Tomy",33, 70000);
//      restTemplate.put(REST_SERVICE_URI+"/user/1", user);
//      System.out.println(user);
//  }
//
//  /* DELETE */
//  private static void deleteUser() {
//      System.out.println("Testing delete User API----------");
//      RestTemplate restTemplate = new RestTemplate();
//      restTemplate.delete(REST_SERVICE_URI+"/user/3");
//  }
//
//
//  /* DELETE */
//  private static void deleteAllUsers() {
//      System.out.println("Testing all delete Users API----------");
//      RestTemplate restTemplate = new RestTemplate();
//      restTemplate.delete(REST_SERVICE_URI+"/user/");
//  }
//
//  public static void main(String args[]){
//      listAllUsers();
//      getUser();
//      createUser();
//      listAllUsers();
//      updateUser();
//      listAllUsers();
//      deleteUser();
//      listAllUsers();
//      deleteAllUsers();
//      listAllUsers();
//  }


}
