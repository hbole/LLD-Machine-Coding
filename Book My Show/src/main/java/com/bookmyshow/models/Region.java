package com.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "regions")
public class Region extends BaseModel {
    private String name;
//    private List<Theatre> theatres;
}
