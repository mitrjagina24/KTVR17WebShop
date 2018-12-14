/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author pupil
 */
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long Id;
    @Column(unique = true)
    private String name;
    private Integer price;
    private Integer count;

    public Product() {
    }

    public Product(String name, Integer price, Integer count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getCount() {
        return count;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.Id);
        hash = 61 * hash + Objects.hashCode(this.name);
        hash = 61 * hash + Objects.hashCode(this.price);
        hash = 61 * hash + Objects.hashCode(this.count);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.Id, other.Id)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.count, other.count)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "Id=" + Id + ", name=" + name + ", price=" + price + ", count=" + count + '}';
    }

    

    

  
}
