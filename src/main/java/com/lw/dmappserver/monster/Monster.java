package com.lw.dmappserver.monster;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Field;
import java.util.*;


@Document(collection = "monsters")
public class Monster {
    @Id
    private String id;
    private String userId;
    private String type;
    private int hp;
    private int ac;
    private String size;
    private String alignment;
    private String[] languages;
    private int challenge;
    private int proficiency;
    private String name;
    private Map<String, Short> speed;
    private Abilities abilities;
    private Abilities saves;
    private Skill[] skills;
    private String[] resistances;
    private String[] vulnerabilities;
    private String[] damageImmunities;
    private String[] conditionImmunities;
    private Map<String, Short> senses;
    private Feature[] features;
    private Feature[] actions;
    private Feature[] legendaryActions;
    private Date dateCreated;

    public Monster() {}

    public Monster(String id, String userId, String type, int hp, int ac, String size, String alignment, String[] languages, int challenge, int proficiency, String name, Map<String, Short> speed, Abilities abilities, Abilities saves, Skill[] skills, String[] resistances, String[] vulnerabilities, String[] damageImmunities, String[] conditionImmunities, Map<String, Short> senses, Feature[] features, Feature[] actions, Feature[] legendaryActions, Date dateCreated) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.hp = hp;
        this.ac = ac;
        this.size = size;
        this.alignment = alignment;
        this.languages = languages;
        this.challenge = challenge;
        this.proficiency = proficiency;
        this.name = name;
        this.speed = speed;
        this.abilities = abilities;
        this.saves = saves;
        this.skills = skills;
        this.resistances = resistances;
        this.vulnerabilities = vulnerabilities;
        this.damageImmunities = damageImmunities;
        this.conditionImmunities = conditionImmunities;
        this.senses = senses;
        this.features = features;
        this.actions = actions;
        this.legendaryActions = legendaryActions;
        this.dateCreated = dateCreated;
    }


    @Override
    public String toString() {
        return "Monster{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", hp=" + hp +
                ", ac=" + ac +
                ", size='" + size + '\'' +
                ", alignment='" + alignment + '\'' +
                ", languages=" + Arrays.toString(languages) +
                ", challenge=" + challenge +
                ", proficiency=" + proficiency +
                ", name='" + name + '\'' +
                ", speed=" + speed +
                ", resistances=" + Arrays.toString(resistances) +
                ", vulnerabilities=" + Arrays.toString(vulnerabilities) +
                ", damageImmunities=" + Arrays.toString(damageImmunities) +
                ", conditionImmunities=" + Arrays.toString(conditionImmunities) +
                ", senses=" + senses +
                ", features=" + Arrays.toString(features) +
                ", actions=" + Arrays.toString(actions) +
                ", legendaryActions=" + Arrays.toString(legendaryActions) +
                ", dateCreated=" + dateCreated +
                '}';
    }

    public Map<String, String> getBasicStats() {
        Map<String, String> tmp = new LinkedHashMap<>();
        tmp.put("Name", getName());
        tmp.put("Alignment", getAlignment());
        tmp.put("Challenge", "" + getChallenge());
        return tmp;
    }


    public Map<String, String> getRawFieldMap() throws IllegalAccessException {
        Map<String, String> tmp = new LinkedHashMap<>();


        for (Field field : Monster.class.getDeclaredFields()) {
            if (field.get(this) != null) {
                if (field.get(this).getClass().isArray()) {
                    StringBuilder arrayString = new StringBuilder();
                    for (Object o : (Object[])field.get(this)) {
                        arrayString.append(o.toString() + ", ");
                    }
                    tmp.put(field.getName(), arrayString.toString());
                } else {
                    tmp.put(field.getName(), field.get(this).toString());
                }
            }
        }

        return tmp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public int getChallenge() {
        return challenge;
    }

    public void setChallenge(int challenge) {
        this.challenge = challenge;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Short> getSpeed() {
        return speed;
    }

    public void setSpeed(Map<String, Short> speed) {
        this.speed = speed;
    }

    public Abilities getAbilities() {
        return abilities;
    }

    public void setAbilities(Abilities abilities) {
        this.abilities = abilities;
    }

    public Abilities getSaves() {
        return saves;
    }

    public void setSaves(Abilities saves) {
        this.saves = saves;
    }

    public Skill[] getSkills() {
        return skills;
    }

    public void setSkills(Skill[] skills) {
        this.skills = skills;
    }

    public String[] getResistances() {
        return resistances;
    }

    public void setResistances(String[] resistances) {
        this.resistances = resistances;
    }

    public String[] getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(String[] vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public String[] getDamageImmunities() {
        return damageImmunities;
    }

    public void setDamageImmunities(String[] damageImmunities) {
        this.damageImmunities = damageImmunities;
    }

    public String[] getConditionImmunities() {
        return conditionImmunities;
    }

    public void setConditionImmunities(String[] conditionImmunities) {
        this.conditionImmunities = conditionImmunities;
    }

    public Map<String, Short> getSenses() {
        return senses;
    }

    public void setSenses(Map<String, Short> senses) {
        this.senses = senses;
    }

    public Feature[] getFeatures() {
        return features;
    }

    public void setFeatures(Feature[] features) {
        this.features = features;
    }

    public Feature[] getActions() {
        return actions;
    }

    public void setActions(Feature[] actions) {
        this.actions = actions;
    }

    public Feature[] getLegendaryActions() {
        return legendaryActions;
    }

    public void setLegendaryActions(Feature[] legendaryActions) {
        this.legendaryActions = legendaryActions;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}