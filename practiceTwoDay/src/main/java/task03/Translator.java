package task03;

import java.util.*;

class Translator {
    private Map<String, List<String>> enToCn = new HashMap<>();
    private Map<String, List<String>> cnToEn = new HashMap<>();

    public Translator() {
        // 初始化翻译数据
        initData();
    }

    private void initData() {
        // 英文 - 中文翻译数据
        enToCn.put("bank", Arrays.asList("银行", "岸"));
        enToCn.put("watch", Arrays.asList("手表", "观看"));
        enToCn.put("trip", Arrays.asList("旅行", "摔倒", "过失"));
        enToCn.put("play", Arrays.asList("玩耍", "演奏", "游戏"));
        enToCn.put("park", Arrays.asList("公园", "停车"));
        enToCn.put("note", Arrays.asList("笔记", "注释"));
        enToCn.put("like", Arrays.asList("喜欢", "希望"));
        enToCn.put("enjoy", Arrays.asList("喜欢", "享受"));
        enToCn.put("travel", Arrays.asList("旅行"));
        enToCn.put("ball", Arrays.asList("球", "舞会"));

        // 中文 - 英文翻译数据
        for (Map.Entry<String, List<String>> entry : enToCn.entrySet()) {
            String enWord = entry.getKey();
            for (String cnWord : entry.getValue()) {
                cnToEn.putIfAbsent(cnWord, new ArrayList<>());
                cnToEn.get(cnWord).add(enWord);
            }
        }
    }

    public void translate() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入需要翻译的单词或词语: ");
            String input = scanner.nextLine();
            if (enToCn.containsKey(input)) {
                System.out.println("中文翻译: " + String.join("  ", enToCn.get(input)));
            } else if (cnToEn.containsKey(input)) {
                System.out.println("英文翻译: " + String.join("  ", cnToEn.get(input)));
            } else {
                System.out.println("未找到该单词或词语的翻译。");
            }
            System.out.print("是否继续翻译？(Y/N) ");
            String choice = scanner.nextLine();
            if (!"Y".equalsIgnoreCase(choice)) {
                break;
            }
        }
    }
}