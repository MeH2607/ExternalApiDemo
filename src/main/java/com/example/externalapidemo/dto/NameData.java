package com.example.externalapidemo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NameData {
    public String name;
    public String gender;
    public double genderProbability;
    public int age;
    public int ageCount;
    public String country;
    public double countryProbability;

    @Override
    public String toString() {
        return name + ", " + gender + ", " + genderProbability + ", " + age + ", " + ageCount + ", " + country + ", " + countryProbability;
    }

}
