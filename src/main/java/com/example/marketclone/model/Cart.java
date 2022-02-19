package com.example.marketclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cart {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @OneToMany(mappedBy = "cart")
    private List<ProductInCart> productInCartList = new ArrayList<>();

}
