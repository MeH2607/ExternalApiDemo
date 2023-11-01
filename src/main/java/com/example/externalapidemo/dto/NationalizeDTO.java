package com.example.externalapidemo.dto;

import com.example.externalapidemo.entity.CountryInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NationalizeDTO {
    private int count;
    private String name;
    private List<CountryInfo> country;


}






