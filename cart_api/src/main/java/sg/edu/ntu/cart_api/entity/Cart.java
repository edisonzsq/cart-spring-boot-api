package sg.edu.ntu.cart_api.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer id; // auto generated PK in db
    
    int quantity = 1;

    @OneToOne
    @JoinColumn(name="product_id")
    Product product;

    @Column(name="created_at", updatable= false)
    Timestamp createdAt = new Timestamp(new Date().getTime());

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
