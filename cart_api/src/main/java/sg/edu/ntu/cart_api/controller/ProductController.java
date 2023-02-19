package sg.edu.ntu.cart_api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.cart_api.entity.Product;

/*
 * @RestController annotation converts this class into a REST API.
 * 
 * @RequestMapping("/products") set this class on the /products path
 */
@RestController
@RequestMapping("/products")
public class ProductController {


    /*
     * The value is being picked up and injected from the resources/application.properties file
     */
    @Value("${PROJECT_NAME}")
    String projectName;


    /*
     * @RequestMapping set HTTP Verb to be GET. Since not further path is appended, this method shall follow the path set at the class level
     * 
     * Actual path to call: GET /products
     * 
     * Cannot use YARC extension to call because it is not a json/application content type
     */
    @RequestMapping(method = RequestMethod.GET)
    public String findAll(){
        return projectName;
    }

    /* 
     * @RequestMapping configured this method to append /{id} path, which makes the actual path /products/{id}. The {id} is received with @PathVariable annotation as int type.
     * @RequestMapping also set the HTTP Verb of this method to be a GET
     * 
     * Actual path to call: GET /products/{id}
     * 
     * Note: Cannot use YARC extension to call because it is not a json/application content type
     */
    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    public String findById(@PathVariable int id){
        return "GET /products/"+id;
    }

    /*
     * @RequestMapping has set the HTTP Verb to be POST. Since no further path is appended, this method follows the path set at class level.
     * @RequestBody enable this method to inject request.body content to the argument annotated with @RequestBody, which is a POJO (plain old java object) called Product.
     * @ResponseBody enable this method to return an object as the response.body
     * 
     * Note: When @RequestBody and @ResponseBody is used, this method automatically configured to Content-type: application/json, which can be called using YARC extension. 
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Product create(@RequestBody Product product){
        return product;
    }
}
