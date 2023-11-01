package com.example.externalapidemo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Gender {
    String gender;
    String name;
    int count;
    double probability;

    public String toString(){
        return this.name + ", " + this.gender + ", " + this.count + ", " + this.probability;
    }
}

