package com.lw.dmappserver.monster;

import java.util.Arrays;

public class Abilities {
    public Short strength;
    public Short dexterity;
    public Short constitution;
    public Short intelligence;
    public Short wisdom;
    public Short charisma;
    public Boolean[] proficiencies;

    public Abilities() {
    }

    public Abilities(Short strength, Short dexterity, Short constitution, Short intelligence, Short wisdom, Short charisma, Boolean[] proficiencies) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.proficiencies = proficiencies;
    }

    @Override
    public String toString() {
        return "Abilities{" +
                "strength=" + strength +
                ", dexterity=" + dexterity +
                ", constitution=" + constitution +
                ", intelligence=" + intelligence +
                ", wisdom=" + wisdom +
                ", charisma=" + charisma +
                ", proficiencies=" + Arrays.toString(proficiencies) +
                '}';
    }

    public Short getStrength() {
        return strength;
    }

    public void setStrength(Short strength) {
        this.strength = strength;
    }

    public Short getDexterity() {
        return dexterity;
    }

    public void setDexterity(Short dexterity) {
        this.dexterity = dexterity;
    }

    public Short getConstitution() {
        return constitution;
    }

    public void setConstitution(Short constitution) {
        this.constitution = constitution;
    }

    public Short getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Short intelligence) {
        this.intelligence = intelligence;
    }

    public Short getWisdom() {
        return wisdom;
    }

    public void setWisdom(Short wisdom) {
        this.wisdom = wisdom;
    }

    public Short getCharisma() {
        return charisma;
    }

    public void setCharisma(Short charisma) {
        this.charisma = charisma;
    }

    public Boolean[] getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(Boolean[] proficiencies) {
        this.proficiencies = proficiencies;
    }
}
