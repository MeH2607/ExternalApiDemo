package com.example.externalapidemo.service;

import com.example.externalapidemo.dto.AgifyDTO;
import com.example.externalapidemo.dto.GenderizeDTO;
import com.example.externalapidemo.dto.NameData;
import com.example.externalapidemo.dto.NationalizeDTO;
import com.example.externalapidemo.entity.CountryInfo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Comparator;

@NoArgsConstructor
@Service
public class NameDataService {

    private Mono<AgifyDTO> getAgifyData(String name){
        WebClient client = WebClient.create();
        Mono<AgifyDTO> agifyDTOMono = client.get()
                .uri("https://api.agify.io/?name=" + name)
                .retrieve()
                .bodyToMono(AgifyDTO.class);
        return agifyDTOMono;
    }

    private Mono<GenderizeDTO> getGenderizeData(String name){
        WebClient client = WebClient.create();
        Mono<GenderizeDTO> genderizeDTOMono = client.get()
                .uri("https://api.genderize.io/?name=" + name)
                .retrieve()
                .bodyToMono(GenderizeDTO.class);
        return genderizeDTOMono;
    }

    private Mono<NationalizeDTO> getNationalizeData(String name){
        WebClient client = WebClient.create();
        Mono<NationalizeDTO> nationalizeDTOMono = client.get()
                .uri("https://api.nationalize.io/?name=" + name)
                .retrieve()
                .bodyToMono(NationalizeDTO.class);
        return nationalizeDTOMono;
    }

    public NameData findNameData(String name) {
        //Henter de 3 monos asynkront
    Mono<AgifyDTO> agifyDTOMono = getAgifyData(name);
    Mono<GenderizeDTO> genderizeDTOMono = getGenderizeData(name);
    Mono<NationalizeDTO> nationalizeDTOMono = getNationalizeData(name);
        NameData nameData = new NameData();
        //mono.zip kombinerer de 3 monoer til en mono og mapper det til en tuple
    var rs = Mono.zip(agifyDTOMono, genderizeDTOMono, nationalizeDTOMono).map(tuple -> {

        //setter dataene fra de 3 monoene ind i nameData
        nameData.setName(name);
        nameData.setGender(tuple.getT2().getGender());
        nameData.setGenderProbability(tuple.getT2().getProbability() * 100);
        nameData.setAge(tuple.getT1().getAge());

        nameData.setAgeCount(tuple.getT1().getCount());
        CountryInfo countryHighestProbability = tuple.getT3().getCountry().stream()
                .max(Comparator.comparing(CountryInfo::getProbability))
                .orElse(null);
        nameData.setCountry(countryHighestProbability.getCountry_id());
        nameData.setCountryProbability(countryHighestProbability.getProbability() * 100);
        System.out.println(nameData.toString());
        return nameData;
    });

        return nameData;
    }

    public Mono<NameData> findNameDataMono(String name) {
        Mono<AgifyDTO> agifyDTOMono = getAgifyData(name);
        Mono<GenderizeDTO> genderizeDTOMono = getGenderizeData(name);
        Mono<NationalizeDTO> nationalizeDTOMono = getNationalizeData(name);

        return Mono.zip(agifyDTOMono, genderizeDTOMono, nationalizeDTOMono)
                .map(tuple -> {
                    NameData nameData = new NameData();

                    nameData.setName(name);
                    nameData.setGender(tuple.getT2().getGender());
                    nameData.setGenderProbability(tuple.getT2().getProbability() * 100);
                    nameData.setAge(tuple.getT1().getAge());
                    nameData.setAgeCount(tuple.getT1().getCount());

                    CountryInfo countryHighestProbability = tuple.getT3().getCountry().stream()
                            .max(Comparator.comparing(CountryInfo::getProbability))
                            .orElse(null);

                    if (countryHighestProbability != null) {
                        nameData.setCountry(countryHighestProbability.getCountry_id());
                        nameData.setCountryProbability(countryHighestProbability.getProbability() * 100);
                    } else {
                        // Handle the case where there is no country data
                    }

                    System.out.println(nameData.toString());

                    return nameData;
                });
    }

}
