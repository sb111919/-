package com.itheima.domain;

import java.util.ArrayList;

public class HeroCharacter extends Character{
   public ArrayList<String> skillList ;

    public HeroCharacter(String username, int hp, int attack, int def) {
        super(username, hp, hp, attack, def);
        this.skillList=new ArrayList<>();
    }

    public HeroCharacter(String name, int hp, int maxHp, int attack, int def, ArrayList<String>skillList) {
        super(name, hp, maxHp, attack, def);
        skillList = new ArrayList<>();
    }
    public String showSkill(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < skillList.size(); i++) {
            sb.append(skillList.get(i));
            if(i != skillList.size()-1){
                sb.append(",");
            }


        }

        return sb.toString();
    }

}
