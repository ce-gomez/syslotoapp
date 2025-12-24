package com.sysloto.app.domain.seller;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Seller {
    @Id
    public Long sellerId;
    public String name;
    public String lastname;
    public double factor;

    public static Seller create(String name, String lastname, double factor) {
        return new Seller(null, name, lastname, factor);
    }
}
