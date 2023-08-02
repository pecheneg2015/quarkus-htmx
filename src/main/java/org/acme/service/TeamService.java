package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Team;

import java.util.*;
import java.util.stream.IntStream;

@ApplicationScoped
public class TeamService {
    private static List<Team> data = new ArrayList<>(Arrays.asList(
            new Team("id1","Астрахань",1931,"ru"),
            new Team("id2","Кубань",1928, "ru"),
            new Team("id3","Тобол", 1960,"ru"),
            new Team("id4","Актобе", 1967,"kz"),
            new Team("id5","Женис",1964,"kz"),
            new Team("id6","Тораз", 1960,"kz"),
            new Team("id7","Лез Астр",2002,"cm"),
            new Team("id8","Орикс Дуала",1937,"cm"),
            new Team("id9","Стяуа",1947,"ro"),
            new Team("id10","Корвинул",1921,"ro")
    ));

    public List<Team> getTeamsByCode (String code){
        return  data.stream().filter(item -> Objects.equals(item.countryCode, code)).toList();
    }


    public  void removeTeamById (String id){
        data =  new ArrayList<>(data.stream().filter(item -> !Objects.equals(item.id, id)).toList());
    }

    public  void addTeam (Team team){
        data.add(team);
    }

    public  Team getTeamById (String id){
        return  data.stream().filter(item -> Objects.equals(item.id, id)).findFirst().orElse(null);
    }

    public  void updateTeam (Team team){
        int index = IntStream.range(0, data.size())
                .filter(i -> Objects.equals(data.get(i).id, team.id))
                .findFirst()
                .orElse(-1);
        if(index != -1)
            data.set(index,team);
    }
}
