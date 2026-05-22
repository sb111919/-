# Text Fighting Game (文字格鬥遊戲)

一個基於 Java 基礎語法與物件導向程式設計（OOP）開發的命令列（CLI）回合制格鬥遊戲。本專案是我在學習 Java 實作時的練習作品，全面採用分層架構（Package）設計，並整合了使用者登入與完整的戰鬥核心邏輯。

---

## 🎮 遊戲核心特色

* **使用者登入系統**：內建 `Login` 與 `User` 模組，模擬基礎的使用者驗證流程。
* **分層架構設計**：嚴格劃分 `domain`（業務實體）與 `ui`（介面顯示），程式碼結構清晰、易於維護。
* **物件導向繼承機制**：核心角色採用繼承架構（`EnemyCharacter` 與 `HeroCharacter` 均繼承自 `Character`），並透過 `@Override` 實作自訂的傷害計算與防禦減傷邏輯。
* **豐富的戰鬥機制**：
    * **血條動態渲染**：終端機會根據當前 HP 比例動態輸出圖形化血條（如 `[████░░░░]`）。
    * **技能消耗系統**：部分強力技能需消耗自身生命值（HP）才能發動，提升戰術策略性。
    * **動態防禦機制**：敵人或玩家可進入防禦狀態，觸發減傷公式計算。

---

## 🛠️ 運用之 Java 核心技術

* **封裝 (Encapsulation)**：角色屬性（姓名、血量、攻防值）皆以 `private` 保護，透過建構子與 Getter/Setter 進行存取。
* **繼承 (Inheritance)**：抽取共通屬性至父類別 `Character`，子類別再行擴充。
* **多型與複寫 (Polymorphism & Overriding)**：複寫 `takeDamage()` 方法，依據角色的防禦狀態（`defing`）動態計算最終傷害。
* **集合與流程控制**：利用迴圈控管回合制邏輯，並透過條件判斷處理格鬥事件。

---

## 📂 專案目錄結構

專案嚴格遵循 Java 套件（Package）命名規範，結構如下：

```text
src/main/java/com/itheima/
├── domain/             # 業務邏輯與實體模型 (Domain Model)
│   ├── Character.java       # 角色基準父類別（定義基礎屬性與受傷邏輯）
│   ├── HeroCharacter.java   # 玩家英雄類別（擴充英雄專屬技能）
│   ├── EnemyCharacter.java  # 敵方怪物類別（實作特殊減傷機制）
│   ├── User.java            # 使用者帳號資料模型
│   └── Test.java            # 獨立功能測試類別
└── ui/                 # 介面與顯示邏輯 (User Interface)
    ├── App.java             # 程式啟動總入口
    ├── FightingGame.java    # 戰鬥核心畫面與回合制調度
    └── Login.java           # 登入與註冊介面流程

執行步驟
複製本專案至本地端：

Bash
git clone [https://github.com/sb111919/TextFightingGame.git](https://github.com/sb111919/TextFightingGame.git)

使用 IntelliJ IDEA 開啟專案。

導航至 src/main/java/com/itheima/ui/App.java。

📋 實際戰鬥畫面範例
Plaintext
第 6 回合開始
aaa[██████████          ] 106/170hp
重裝坦克[█              ] 15/130hp
==========你的回合==========
1. 普通攻擊
2. 強力一擊
> 2

🩸 消耗 10 點生命，你對 重裝坦克 造成了 21 點傷害！
重裝坦克 受到了 21 點傷害！剩餘血量：15

==========重裝坦克的回合==========
👾 敵人採取了普通攻擊
💥 aaa 受到了 7 點傷害！剩餘血量：106
重裝坦克 對你造成了 7 點傷害！


✍️ 學習心得與未來優化
透過這個專案的開發，我成功將書本上的物件導向觀念（OOP）轉化為實際的程式邏輯。尤其在處理 Character 繼承鏈以及在 FightingGame 中控管多個物件的回合互動時，讓我對 Java 的記憶體操作與物件引用有了更深層的體會。

未來優化方向：

[ ] 實作資料庫或檔案讀寫，讓使用者註冊的帳號可以永久保存。

[ ] 增加更多職業（如刺客、法師）與隨機裝備掉落系統。

[ ] 優化 AI 出招邏輯，使其會根據自身血量判斷是否施放防禦或大招。
