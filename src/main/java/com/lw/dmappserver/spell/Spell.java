package com.lw.dmappserver.spell;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "spells")
public class Spell {
    @Id
    private String id;
    private String name;
    private int level;
    private List<String> classes;
    private String school;
    private String castingTime;
    private String range;
    private String components;
    private String duration;
    private boolean isConcentration;
    private boolean isRitual;

    public Spell() {
    }


    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isConcentration() {
        return isConcentration;
    }

    public void setConcentration(boolean concentration) {
        isConcentration = concentration;
    }

    public boolean isRitual() {
        return isRitual;
    }

    public void setRitual(boolean ritual) {
        isRitual = ritual;
    }
}
