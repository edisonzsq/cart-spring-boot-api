package sg.edu.ntu.cart_api.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

import sg.edu.ntu.cart_api.entity.Cart;
import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.repository.CartRepository;
import sg.edu.ntu.cart_api.repository.ProductRepository;
import sg.edu.ntu.cart_api.exception.NotFoundException;

@Service
public class CartService {

    @Autowired
    CartRepository repo;

    @Autowired
    ProductRepository productRepo;
    
    public void add(int productId, Optional<Integer> quantity, int userId) throws NotFoundException {
        Optional<Cart> optionalCartItem = repo.findByProductIdAndUserId(productId, userId);
        
        if(optionalCartItem.isPresent()){

            // Product found in cart
            Cart cartItem = optionalCartItem.get();
            int currentQuantity = cartItem.getQuantity();
            cartItem.setQuantity(quantity.orElseGet(() -> currentQuantity + 1)); // If quantity param not exist, just increment by 1
            cartItem = repo.save(cartItem);

        }else{

            // Product not found in cart
            Optional<Product> optionalProduct = productRepo.findById(productId);
            if(optionalProduct.isPresent()){
                Product product = optionalProduct.get();
                Cart cartItem = new Cart();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity.orElseGet(() -> 1));
                cartItem.setUserId(userId);
                repo.save(cartItem);
            }else{
                throw new NotFoundException("Product ID not found");
            }
        }
    }

    public void decrement(int productId, int userId) throws NotFoundException{
        Optional<Cart> optionalCartItem = repo.findByProductIdAndUserId(productId, userId);        
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
        }

        throw new NotFoundException("Product ID not found");
    }
}
