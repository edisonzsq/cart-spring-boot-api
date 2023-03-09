package sg.edu.ntu.cart_api.controller;

import java.util.List;
import java.util.Optional;

import javax.crypto.NullCipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.cart_api.entity.Cart;
import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.repository.CartRepository;
import sg.edu.ntu.cart_api.repository.ProductRepository;
import sg.edu.ntu.cart_api.exception.NotFoundException;
import sg.edu.ntu.cart_api.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {
    
    @Autowired
    CartRepository repo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    CartService service;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Cart>> findAll(){
        List<Cart> cartItems = (List<Cart>)repo.findAll();
        if(cartItems.size() > 0) return ResponseEntity.ok().body(cartItems);
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value="/add/{productId}", method=RequestMethod.POST)
    public ResponseEntity add(@PathVariable int productId, @RequestParam Optional<Integer> quantity){
        try{
            service.add(productId, quantity);
            return ResponseEntity.ok().build(); // When no exception is thrown means operation is successful.
        }catch(NotFoundException nfe){
            nfe.printStackTrace();
            return ResponseEntity.notFound().build(); // Return 404 if NotFoundException is thrown.
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(); // Return 500 if any other unexpected exception is thrown.
        }
        
    }

    @RequestMapping(value="/decrement/{productId}", method=RequestMethod.POST)
    public ResponseEntity decrement(@PathVariable int productId){

        try{
            service.decrement(productId);
            return ResponseEntity.ok().build(); // When no exception is thrown means operation is successful.
        }catch(NotFoundException nfe){
            nfe.printStackTrace();
            return ResponseEntity.notFound().build(); // Return 404 if NotFoundException is thrown.
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(); // Return 500 if any other unexpected exception is thrown.
        }
    }
}
