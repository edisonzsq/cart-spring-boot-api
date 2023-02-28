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

    @Autowired
    ProductRepository repo;    
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Product>> findAll(){
        List<Product> products = (List) repo.findAll();
        if(products.size() == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(products);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findById(@PathVariable int id){
        Optional<Product> product = repo.findById(id);
        if(product.isPresent()) return ResponseEntity.ok().body(product.get());
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody Product product){
        
        try{
            Product created = repo.save(product);
            return new ResponseEntity(repo.findById(created.getId()), HttpStatus.CREATED);
        }catch(IllegalArgumentException iae){
            iae.printStackTrace();
            return ResponseEntity.badRequest().build();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
            
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Integer id){
        Optional<Product> currentProduct = repo.findById(id);
        if(currentProduct.isPresent()){            
            try{
                Product p = currentProduct.get();
                p.setName(product.getName());
                p.setDescription(product.getDescription());
                p.setPrice(product.getPrice());
                p = repo.save(p);
                return ResponseEntity.ok().body(p);
            }catch(Exception e){
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }            
        }
        return ResponseEntity.notFound().build();
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int id){
         boolean exist = repo.existsById(id);
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