package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@ApplicationScoped
public class CountryService {
    private static List<Country>  data = new ArrayList<>(Arrays.asList(
            new Country("ru","Российская федерация"),
            new Country("kz","Казахстан"),
            new Country("cm","Камерун"),
            new Country("ro","Румыния")
    ));


    public  List<Country> getALlCountries (){
        return data;
    }

    public  void removeCountry (String code){
        data =  new ArrayList<>(data.stream().filter(item -> !Objects.equals(item.code, code)).toList());
    }

    public  void addCountry (Country country){
        data.add(country);
    }

    public  Country getCountryByCode (String code){
        return  data.stream().filter(item -> Objects.equals(item.code, code)).findFirst().orElse(null);
    }

    public  void updateCountry (Country country){
        int index = IntStream.range(0, data.size())
                .filter(i -> Objects.equals(data.get(i).code, country.code))
                .findFirst()
                .orElse(-1);
        if(index != -1)
            data.set(index,country);
    }
}
