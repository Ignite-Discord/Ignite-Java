package com.general_hello.commands.RPG;

import java.io.File;
import java.util.ArrayList;

public class TestMain {
    public static void main(String[] args) {
        String typeOfItem = "Common";
        File folder = new File("src/main/java/com/general_hello/commands/RPG/Items/" + typeOfItem);
        File[] listOfFiles = folder.listFiles();

        ArrayList<String> imports = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String name = listOfFiles[i].getName().replace(".java", "");
                System.out.println("private ArrayList<" + name + "> " + name + " = new ArrayList<>();");
                imports.add("import com.general_hello.commands.RPG.Items." + typeOfItem + "." + name + ";");
                //import com.general_hello.commands.RPG.Items.Common.AttainFish.FishCommon;
            } else if (listOfFiles[i].isDirectory()) {
                File folder1 = new File("src/main/java/com/general_hello/commands/RPG/Items/" + typeOfItem + "/" + listOfFiles[i].getName());
                System.out.println("//" + folder1.getName());
                File[] listOfFiles1 = folder1.listFiles();

                for (int x = 0; x < listOfFiles1.length; x++) {
                    if (listOfFiles1[x].isFile()) {
                        String name = listOfFiles1[x].getName().replace(".java", "");
                        System.out.println("private ArrayList<" + name + "> " + name + " = new ArrayList<>();");
                        imports.add("import com.general_hello.commands.RPG.Items." + typeOfItem + "." + folder1.getName() + "." + name + ";");
                    } else if (listOfFiles1[x].isDirectory()) {
                        System.out.println("Directory " + listOfFiles1[x].getName());
                    }

                }
            }
        }

        String finalImports = imports.toString().replace("[", "").replace("]", "").replace(", ", "\n");
        System.out.println("\n\nImports here:\n");
        System.out.println(finalImports);
    }
}
