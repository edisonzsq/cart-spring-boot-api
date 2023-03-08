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

@RestController
@RequestMapping("/carts")
public class CartController {
    
    @Autowired
    CartRepository repo;

    @Autowired
    ProductRepository productRepo;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Cart>> findAll(){
        List<Cart> cartItems = (List<Cart>)repo.findAll();
        if(cartItems.size() > 0) return ResponseEntity.ok().body(cartItems);
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value="/add/{productId}", method=RequestMethod.POST)
    public ResponseEntity add(@PathVariable int productId, @RequestParam Optional<Integer> quantity){
        Optional<Cart> optionalCartItem = repo.findByProductId(productId);
        
        if(optionalCartItem.isPresent()){

            // Product found in cart
            Cart cartItem = optionalCartItem.get();
            int currentQuantity = cartItem.getQuantity();
            cartItem.setQuantity(quantity.orElseGet(() -> currentQuantity + 1)); // If quantity param not exist, just increment by 1
            cartItem = repo.save(cartItem);
            return ResponseEntity.ok().build();
        }else{

            // Product not found in cart
            Optional<Product> optionalProduct = productRepo.findById(productId);
            if(optionalProduct.isPresent()){
                Product product = optionalProduct.get();
                Cart cartItem = new Cart();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity.orElseGet(() -> 1));
                repo.save(cartItem);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.badRequest().build();
            }
        }
        
    }

    @RequestMapping(value="/decrement/{productId}", method=RequestMethod.POST)
    public ResponseEntity decrement(@PathVariable int productId){

        Optional<Cart> optionalCartItem = repo.findByProductId(productId);        
        if(optionalCartItem.isPresent()){

            int currentQuantity = 0;
            Cart cart = optionalCartItem.get();
            if(cart.getQuantity() == 1) 
                repo.deleteById(cart.getId());
            else{
                currentQuantity = cart.getQuantity();
                cart.setQuantity(currentQuantity - 1);
                repo.save(cart);
            }                
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
