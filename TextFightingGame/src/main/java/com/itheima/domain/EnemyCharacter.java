package com.itheima.domain;

public class EnemyCharacter extends Character {
    public String skill;
    public boolean defing;

    public EnemyCharacter() {
        super();
    }

    public EnemyCharacter(String name, int hp, int maxHp, int attack, int def, boolean defing, String skill) {
        super(name, hp, maxHp, attack, def);
        this.defing = defing;
        this.skill = skill;
    }


    @Override
    public void takeDamage(int damage) {

        if (defing) {

            damage = damage / 2 > 1 ? damage / 2 : 1;
            defing = false;
        }
        super.takeDamage(damage);
    }
}