package sg.edu.ntu.cart_api.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.repository.ProductRepository;
// import sg.edu.ntu.cart_api.repository.ProductRepository;
import sg.edu.ntu.cart_api.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService service;

    /*
     * For a simple RESTful Endpoints, we do not need to use @service layer to encapsulate complex business logics.
     * Hence, we shall @Autowired the repository directly into the controller class. As for the CartController, we will
     * definitely use @Service class to encapsulate the logics.
     */
    @Autowired
    ProductRepository repo;
    
    // GET /products
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Product>> findAll(){
        List<Product> products = (List) repo.findAll();
        if(products.size() == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(products);
    }

    // GET /products/{id}
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findById(@PathVariable int id){
        Optional<Product> product = repo.findById(id);
        if(product.isPresent()) return ResponseEntity.ok().body(product.get());
        return ResponseEntity.notFound().build();
    }

    /*
     * POST /products
     * 
     * Request body is expected to carry the information to create 
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody Product product){
        
        try{
            Product created = repo.save(product); // when "id" is not present, .save() will perform create operation.
            return new ResponseEntity(repo.findById(created.getId()), HttpStatus.CREATED);
        }catch(IllegalArgumentException iae){
            iae.printStackTrace();
            return ResponseEntity.badRequest().build();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
            
    }

    /*
     * PUT /products/{id}
     * 
     * Request body is expected to carry the information to update
     */
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Integer id){
        Optional<Product> currentProduct = repo.findById(id); // Optional is a utility class to handle null
        if(currentProduct.isPresent()){ // Check if the expected object is present
            try{
                Product p = currentProduct.get(); // Get the object - Product

                // Update the fetched product with name, description, price sent via Request Body
                p.setName(product.getName());
                p.setDescription(product.getDescription());
                p.setPrice(product.getPrice());

                p = repo.save(p); // When "id" is present, .save() will perform update operation.
                return ResponseEntity.ok().body(p);
            }catch(Exception e){
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }            
        }
        return ResponseEntity.notFound().build();
    }
    
    /*
     * DELETE /products/{id}
     */
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int id){
         boolean exist = repo.existsById(id); // This is another way to check if an object exist without holding the object's data in memory
         if(exist){
            try{
                repo.deleteById(id); 
                return ResponseEntity.ok().build();
            }catch(Exception e){
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
         }
         return ResponseEntity.notFound().build();
    }

}