package com.itheima.domain;

public class Character {
    private String name;
    private int hp;
    private int maxHp;
    private int attack;
    private int def;

    public Character() {}

    public Character(String username, int hp, int maxHp, int attack, int def) {
        this.name = username;
        this.hp = hp;
        this.maxHp = maxHp;
        this.attack = attack;
        this.def = def;
    }

    public boolean isAlive() {
        return hp > 0;
    }


    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(this.name + " 受到了 " + damage + " 點傷害！剩餘血量: " + this.hp);
    }

    public String show(){
        return name +"[當前生命:"+hp +"當前攻擊:"+attack+"當前防禦:"+def+"]";
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }


}