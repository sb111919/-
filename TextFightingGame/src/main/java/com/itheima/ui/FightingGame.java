package com.itheima.ui;

import com.itheima.domain.EnemyCharacter;
import com.itheima.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {

    public void gamestart(String username) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("     🎮 " + username + " 歡迎來到文字格鬥遊戲 🎮 ");
        System.out.println("╚════════════════════════════════════════╝");

        HeroCharacter player = createPlayerCharacter(username);
        System.out.println("\n✅ 角色創建成功！");
        System.out.println("📊 初始屬性為: " + player.show());
        System.out.println("⚔️ 擁有的技能: " + player.showSkill());

        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初級戰士", 80, 80, 15, 10, false, "猛擊"));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 60, 20, 5, false, "快速攻擊"));
        enemyList.add(new EnemyCharacter("重裝坦克", 120, 120, 10, 20, false, "防禦姿勢"));
        enemyList.add(new EnemyCharacter("神秘法師", 70, 70, 25, 8, false, "火球術"));

        int count = 1;
        int wins = 0;

        // ✨ 修正：確保玩家活著，且清單內還有怪物可以打
        while (player.isAlive() && enemyList.size() > 0) {
            if (wins != 0) {
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    c.setMaxHp(c.getMaxHp() + 10);
                    c.setAttack(c.getAttack() + 3);
                    c.setDef(c.getDef() + 2);
                    c.defing = false;
                }
            }

            Random r = new Random();
            int index = r.nextInt(enemyList.size());
            EnemyCharacter enemyCharacter = enemyList.get(index);

            System.out.println("\n=====================");
            System.out.println("第 " + count + " 場戰鬥開始! 對手: " + enemyCharacter.getName());

            int round = 1;
            while (player.isAlive() && enemyCharacter.isAlive()) {
                System.out.println("--------------------");
                System.out.println("第 " + round + " 回合開始");

                System.out.println(getHealthBar(player.getName(), player.getHp(), player.getMaxHp()));
                System.out.println(getHealthBar(enemyCharacter.getName(), enemyCharacter.getHp(), enemyCharacter.getMaxHp()));

                // 1. 玩家回合
                playerTurn(player, enemyCharacter);

                // 2. 檢查敵人死了沒
                if (!enemyCharacter.isAlive()) {
                    System.out.println("🎉 你擊敗了 " + enemyCharacter.getName() + "!");
                    wins++;
                    enemyList.remove(index); // ✨ 關鍵：把死掉的怪物移出清單！
                    break;
                }

                // 3. 敵人回合
                enemyTurn(enemyCharacter, player);

                // 4. 檢查玩家死了沒
                if (!player.isAlive()) {
                    System.out.println("💀 你被 " + enemyCharacter.getName() + " 擊敗了...");
                    break;
                }

                round++;
            }

            // 5. 戰鬥結束後的結算 (如果玩家還活著，代表這場打贏了)
            if (player.isAlive()) {
                int heal = r.nextInt(20, 41);

                // ✨ 修正 73 行語法錯誤：計算新血量並存回 player 物件中
                int newHp = player.getHp() + heal;
                if (newHp > player.getMaxHp()) {
                    newHp = player.getMaxHp(); // 防呆：血量不能超過上限
                }
                player.setHp(newHp);

                System.out.println("❤️ 戰鬥勝利結算：你在戰後休息中恢復了 " + heal + " 點生命值！");
                wins++; // ✨ 修正：場數推進
                System.out.println("===================");
            }
            if(player.isAlive() && wins >0 && wins%3==0){
                System.out.println("恭喜，你獲得了屬性提升");
                player.setMaxHp(player.getMaxHp() + 30);
                player.setAttack(player.getAttack() + 5);
                player.setDef(player.getDef() + 3);
                player.setHp(player.getHp() + 30);

                System.out.println("當前屬性:"+player.show());
            }
            if(player.isAlive()){
                System.out.println("繼續下一場戰鬥? y/n");
                Scanner sc = new Scanner(System.in);
                String choose = sc.next();
                if("y".equalsIgnoreCase(choose)){
                count++;
                continue;
                }else if("n".equalsIgnoreCase(choose)){
                    break;
                }else {
                    System.out.println("沒有這個選項，遊戲繼續");
                    continue;
                }
            }
        }

        // 終局宣告
        System.out.println("\n=====================");
        System.out.println("遊戲結束");
        System.out.println("總勝場"+wins);
        System.out.println("感謝遊玩文字版格購遊戲");
        System.exit(0);
    }

    private void enemyTurn(EnemyCharacter enemyCharacter, HeroCharacter player) {
        System.out.println("==========" + enemyCharacter.getName() + "的回合=========");
        String action = "普通攻擊";
        Random r = new Random();
        int num = r.nextInt(10);

        // 假設技能屬性有開放直接存取，或是你有寫 getSkill()
        if (num >= 5) {
            action = enemyCharacter.skill;
        }

        switch (action) {
            case "普通攻擊":
                System.out.println("⚔️ 敵人採取了普通攻擊");
                // ✅ 修正：用變數接住傷害，並讓玩家實質扣血
                int dmg1 = calculateDamage(enemyCharacter.getAttack(), player.getDef());
                player.takeDamage(dmg1);
                System.out.println(enemyCharacter.getName() + " 對你造成了 " + dmg1 + " 點傷害！");
                break;
            case "猛擊":
                System.out.println("💥 敵人使用了 [猛擊]！");
                int dmg2 = calculateDamage((int)(enemyCharacter.getAttack() * 1.5), player.getDef());
                player.takeDamage(dmg2);
                System.out.println(enemyCharacter.getName() + " 對你造成了 " + dmg2 + " 點巨額傷害！");
                break;
            case "快速攻擊":
                System.out.println("⚡ 敵人使用了 [快速攻擊]！");
                int dmg3 =0;
                for (int i = 0; i < 2; i++) {
                    int temp= calculateDamage(enemyCharacter.getAttack()/2, player.getDef()) ;
                    dmg3 = dmg3 +temp;
                }
                player.takeDamage(dmg3);
                System.out.println(enemyCharacter.getName() + " 迅速對你造成了 " + dmg3 + " 點傷害！");
                break;
            case "防禦姿態":
                System.out.println("🛡️ 敵人擺出了 [防禦姿態]！");
                enemyCharacter.defing = true; // 觸發防禦狀態
                break;
            case "火球術":
                System.out.println("🔥 敵人唸動咒語使用了 [火球術]！");
                int dmg4 = calculateDamage((int)(enemyCharacter.getAttack() *1.8), player.getDef());
                player.takeDamage(dmg4);
                System.out.println(enemyCharacter.getName() + " 用烈火對你造成了 " + dmg4 + " 點傷害！");
                break;
        }
    }

    public String getHealthBar(String name , int hp,int maxHp){
            int barLength =20;
            int filled = (int)( (hp * 1.0/maxHp)*barLength);
            StringBuilder sb  = new StringBuilder();
            sb.append(name).append("[");
        for (int i = 0; i < barLength; i++) {
            if(i<filled){
                sb.append("▉");
            }else {
                sb.append(" ");
            }
        }
        sb.append("]").append(hp).append("/").append(maxHp).append("hp");
        return  sb.toString();
    }

    public void playerTurn(HeroCharacter player , EnemyCharacter enemy){
        System.out.println("==========你的回合==========");
        System.out.println("1. 普通攻擊");
        System.out.println("2. 強力一擊");
        System.out.println("3. 生命汲取");
        System.out.println("選擇行動1-3");

        Scanner sc = new Scanner(System.in);
        String choose = sc.next();
        switch (choose) {
            default:
                System.out.println("沒有這個操作，默認使用普通攻擊");
            case "1":
                System.out.println("1. 普通攻擊");
               int damage1 = calculateDamage(player.getAttack(), enemy.getDef());
                System.out.println("你對"+enemy.getName()+"造成了"+damage1+"點傷害");
                enemy.takeDamage(damage1);
                break;
            case "2":
                System.out.println("2. 強力一擊");
                if(player.getHp()>10){
                    player.takeDamage(10);
                  int damage2 =  calculateDamage((int)(player.getAttack()*1.8), enemy.getDef());
                    System.out.println("消耗10點生命，你對"+enemy+"造成了"+damage2+"點傷害");
                    enemy.takeDamage(damage2);
                }else {
                    System.out.println("血量不足，攻擊失敗");
                }
                break;
            case "3":
                System.out.println("3. 生命汲取");
                if(player.getHp()>10){
                    player.takeDamage(10);
                    Random r = new Random();
                    int heal = r.nextInt(21);
                    System.out.println("消耗10點生命，你使用了生命汲取，恢復了"+heal+"點血量");


                }else {
                    System.out.println("血量不足，恢復生命失敗");
                }
                break;

        }
    }



    public int calculateDamage(int attack,int def){
        int damage = attack - def   ;
        if(damage<1){
            damage = 1;
        }
        return  damage;
    }
    public HeroCharacter createPlayerCharacter(String username) {
        System.out.println("\n開始創建你的角色:");
        System.out.println("👤 角色名為: " + username);
        int points = 20;

        System.out.println("\n請分配屬性點，共 20 點");
        System.out.println("❤️ 生命值: (每點 +10)");
        System.out.println("🗡️ 攻擊力: (每點 +2)");
        System.out.println("🛡️ 防禦力: (每點 +1)");

        Scanner sc = new Scanner(System.in);

        String[] attributes = {"生命值", "攻擊力", "防禦力"};
        int[] values = new int[3];

        for (int i = 0; i < attributes.length; i++) {
            // 如果點數已經用完，後面的屬性直接補 0，不需要再讓玩家輸入
            if (points <= 0) {
                System.out.println(attributes[i] + " 自動分配為 0 點 (已無剩餘屬性點)");
                values[i] = 0;
                continue;
            }

            System.out.print("分配屬性點至 [" + attributes[i] + "] (剩餘點數:" + points + "): ");
            int input = sc.nextInt();

            // 防呆1: 防止輸入負數
            if (input < 0) {
                System.out.println("⚠️ 無效輸入，不能為負數，默認分配 0 點！");
                input = 0;
            }

            // 防呆2: 防止輸入超過剩餘點數
            if (input > points) {
                System.out.println("⚠️ 屬性點不足！剩餘的 " + points + " 點將全數分配到 [" + attributes[i] + "]");
                input = points; // 強制將輸入值設為剩餘的最大點數
            }

            points = points - input;
            values[i] = input;
        }

        // 假設 HeroCharacter 的建構子順序為 (名字, HP, 攻擊, 防禦)
        HeroCharacter player = new HeroCharacter(username, 100 + values[0] * 10, 10 + values[1] * 2, 0+values[2]);

        player.skillList.add("普通攻擊");
        player.skillList.add("強力一擊");
        player.skillList.add("生命汲取");

        return player;
    }
}