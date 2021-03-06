package com.lw.dmappserver.spell;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "spells")
public class Spell {
    @Id
    private String id;


    private String userId;
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
    private String description;
    private String sub_school;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSub_school() {
        return sub_school;
    }

    public void setSub_school(String sub_school) {
        this.sub_school = sub_school;
    }

    @Override
    public String toString() {
        return "Spell{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", classes=" + classes +
                ", school='" + school + '\'' +
                ", castingTime='" + castingTime + '\'' +
                ", range='" + range + '\'' +
                ", components='" + components + '\'' +
                ", duration='" + duration + '\'' +
                ", isConcentration=" + isConcentration +
                ", isRitual=" + isRitual +
                ", description=" + description +
                '}';
    }

    public Map<String, String> getPropertyMap() {
        Map<String, String> tmp = new LinkedHashMap<>();
        tmp.put("Name", getName());
        tmp.put("Level", "" + getLevel());
        if (getClasses() != null) {
            tmp.put("Classes", String.join(" ", getClasses()));
        }
        else {
                tmp.put("Classes","-");
        }
        tmp.put("School", getSchool());
        if (getSub_school() != null && getSub_school().matches("[rR]itual")) {
            tmp.put("Casting Time", getCastingTime() + " (Ritual)");
        } else {
            tmp.put("Casting Time", getCastingTime());
        }
        tmp.put("Range", getRange());
        tmp.put("Components", getComponents());
        tmp.put("Duration", getDuration());
        tmp.put("Description", getDescription());
        return tmp;
    }
}
