package com.ifi.trainer_api.bo;

import javax.persistence.Embeddable;

@Embeddable
public class Pokemon {

    private int id;
    private int pokemonType;

    private int level;

    public Pokemon() {
    }

    public Pokemon(int pokemonType, int level) {
        this.pokemonType = pokemonType;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(int pokemonType) {
        this.pokemonType = pokemonType;
    }


}