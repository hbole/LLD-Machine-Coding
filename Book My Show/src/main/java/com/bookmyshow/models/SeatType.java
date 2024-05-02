package com.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "seat_type")
public class SeatType extends BaseModel {
    private String name;
}
