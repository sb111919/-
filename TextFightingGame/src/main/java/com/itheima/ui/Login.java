package com.itheima.ui;

import com.itheima.domain.User;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {

    public void start() {
        System.out.println("遊戲的登錄註冊頁面打開了~");
        ArrayList<User> list = new ArrayList<>();

        while (true) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("         🎮  歡迎來到文字格鬥遊戲  🎮       ");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("請選擇操作：1登錄 2注冊 3退出");

            Scanner sc = new Scanner(System.in);
            String choose = sc.next();

            switch (choose) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> {
                    System.out.println("用戶選擇了退出");
                    System.exit(0);
                }
                default -> System.out.println("輸入有誤，請重新輸入");
            }
        }
    }

    public void login(ArrayList<User> list) {
        System.out.println("==== 用戶選擇了登入 ====");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("請輸入用戶名：");
            String username = sc.next();

            // 1. 檢查用戶是否存在
            if (!contains(list, username)) {
                System.out.println("該用戶名稱不存在，請先註冊！");
                return;
            }

            // 2. 檢查用戶是否被禁用
            int index = findIndex(list, username);
            User u = list.get(index);
            if (u.isStatus()) {
                System.out.println("用戶" + username + "已被禁用，請聯繫客服123-456789");
                return;
            }

            String rightpassword = u.getPassword();

            // 3. 密碼驗證迴圈（給予 3 次機會）
            for (int i = 0; i < 3; i++) {
                System.out.println("請輸入密碼：");
                String password = sc.next();

                // 4. 驗證碼死迴圈（不耗費密碼次數，直到輸入正確才前進）
                while (true) {
                    String rightcode = getCode();
                    System.out.println("【系統提示】當前驗證碼為: " + rightcode); // 修正：一定要印出來！

                    System.out.println("請輸入驗證碼:");
                    String code = sc.next();

                    if (code.equalsIgnoreCase(rightcode)) {
                        System.out.println("驗證碼輸入正確！");
                        break; // 跳出驗證碼死迴圈，準備比對密碼
                    } else {
                        System.out.println("驗證碼輸入錯誤，請重新輸入。");
                        // 這裡會 continue 回到驗證碼迴圈開頭，重新生成並輸入驗證碼
                    }
                }

                // 5. 比對剛才輸入的密碼 (修正：用使用者輸入的 password 相比)
                if (rightpassword.equals(password)) {
                    System.out.println("登入成功，遊戲啟動！");

                    FightingGame fg = new FightingGame();
                    fg.gamestart(username);
                    return; // 登入成功，徹底結束 login 方法
                } else {
                    System.out.println("登入失敗，密碼輸入錯誤");
                    if (i == 2) {
                        u.setStatus(true); // 修正：鎖定帳號要把狀態改成 true (代表禁用)
                        System.out.println("當前帳號 " + username + " 已被鎖定，請聯繫客服");
                        return;
                    } else {
                        System.out.println("密碼錯誤，你還剩下 " + (2 - i) + " 次機會\n");
                    }
                }
            }
        }
    }


    public int findIndex(ArrayList<User> list, String user) {
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            if (u.getUsername().equals(user)) {
                return i;
            }
        }
        return -1;
    }

    public void register(ArrayList<User> list) {
        System.out.println("==== 用戶選擇了註冊 ====");
        User u = new User();
        Scanner sc = new Scanner(System.in);

        String username = "";
        while (true) {
            System.out.println("請輸入註冊用戶名 (3-16字，只能由字母、數字組成)：");
            username = sc.next();

            if (!checkLength(3, 16, username)) {
                System.out.println("用戶名不符合要求，必須是3-16字");
                continue;
            }
            if (!checkUsername(username)) {
                System.out.println("用戶名只能由字母、數字組成");
                continue;
            }
            if (contains(list, username)) {
                System.out.println("該用戶名稱已存在，請換一個！");
                continue;
            }
            break;
        }

        while (true) {
            System.out.println("請輸入密碼 (3-8字，必須包含字母與數字)：");
            String password1 = sc.next();
            System.out.println("請再次輸入密碼：");
            String password2 = sc.next();

            if (!checkLength(3, 8, password1)) {
                System.out.println("密碼長度不符合要求，必須是3-8位之間");
                continue;
            }
            if (!checkPassword(password1)) {
                System.out.println("密碼只能是字母加數字的組合，不能有其他符號");
                continue;
            }
            if (!password1.equals(password2)) {
                System.out.println("兩次密碼輸入不一致，請重新輸入");
                continue;
            }

            u.setUsername(username);
            u.setPassword(password1);
            u.setStatus(false); // 預設註冊成功為未禁用狀態
            list.add(u);
            System.out.println("恭喜！用戶 [ " + u.getUsername() + " ] 註冊成功！");
            break;
        }
    }

    public boolean checkUsername(String username) {
        int[] arr = getCount(username);
        // 原本邏輯：必須包含字母(arr[0]>0)，數字可有可無(arr[1]>=0)，不能有其他字元(arr[2]==0)
        return arr[0] > 0 && arr[1] >= 0 && arr[2] == 0;
    }

    public boolean checkLength(int minLen, int maxLen, String str) {
        return str.length() >= minLen && str.length() <= maxLen;
    }

    public boolean checkPassword(String password) {
        int[] arr = getCount(password);
        // 密碼必須同時包含字母與數字，且無其他字元
        return arr[0] > 0 && arr[1] > 0 && arr[2] == 0;
    }

    boolean contains(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public int[] getCount(String userinfo) {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;

        for (int i = 0; i < userinfo.length(); i++) {
            char c = userinfo.charAt(i);
            if (Character.isLetter(c)) {
                charCount++;
            } else if (Character.isDigit(c)) {
                numCount++;
            } else {
                otherCount++;
            }
        }
        return new int[]{charCount, numCount, otherCount};
    }

    public static String getCode() {
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }

        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }

        sb.append(r.nextInt(10)); // 0-9 的隨機數
        char[] arr = sb.toString().toCharArray();
        int i = r.nextInt(arr.length);

        // 打亂最後一個數字的位置
        char temp = arr[i];
        arr[i] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;

        return new String(arr);
    }
}