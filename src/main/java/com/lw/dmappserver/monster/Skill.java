package com.lw.dmappserver.monster;

public class Skill {
    public String name;
    public Short score;
    public String ability;
    public boolean proficiency;

    public Skill() {
    }

    public Skill(String name, Short score, String ability, boolean proficiency) {
        this.name = name;
        this.score = score;
        this.ability = ability;
        this.proficiency = proficiency;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", ability='" + ability + '\'' +
                ", proficiency=" + proficiency +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getScore() {
        return score;
    }

    public void setScore(Short score) {
        this.score = score;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public boolean isProficiency() {
        return proficiency;
    }

    public void setProficiency(boolean proficiency) {
        this.proficiency = proficiency;
    }
}
