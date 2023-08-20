package ru.xodkewic.mergesort;

import java.io.FileWriter;
import java.math.BigInteger;

/**
 * <h1>
 * Класс Main
 * <p>
 * Главный класс программы
 * <p>
 * По всей видимости, здесь необходимо разработать алгоритм слияния нескольких
 * отсортированных по возрастанию файлов в один.
 * <p>
 * В общих чертах он такой:
 * <ol>
 * <li>открываем одновременно несколько файлов;</li>
 * <li>заводим массив, где будем хранить считанную строку;</li>
 * <li>сравниваем строки между собой и ищем минимальную;</li>
 * <li>там, где найдена минимальная, считывается следующая строка.</li>
 * </ol>
 * <p>
 * Поскольку необходимо записать файлы не только в порядке возрастания,
 * но и в порядке убывания, итог весь будет сохраняться в памяти.
 * <p>
 * Затраты памяти: O(m*n).
 */
public class Main {
    public static void main(String[] args) {
        CommandLineArguments clArgs;
        String[] inFiles;
        FileLineIterator[] it;



        try {
            clArgs = new CommandLineArguments(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        inFiles = clArgs.getInFiles();
        it = new FileLineIterator[inFiles.length];

        for (int i = 0; i < inFiles.length; i++) {
            try {
                it[i] = new FileLineIterator(inFiles[i]);
                it[i].next();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        boolean hasData;

        StringBuilder outBuf = new StringBuilder();
        while (true) {
            String curString, minString = null;
            BigInteger curInt, minInt = null;
            int min_index = 0;

            hasData = false;
            for (int i = 0; i < it.length; i++) {
                curString = it[i].getCurrentLine();
                hasData = hasData || (curString != null);

                if (curString == null) {
                    continue;
                }

                switch (clArgs.getDataType()) {
                    case STRINGS:
                        if (minString == null) {
                            minString = curString;
                            min_index = i;
                        } else {
                            if (minString.compareTo(curString) > 0) {
                                minString = curString;
                                min_index = i;
                            }
                        }
                        break;
                    case INTEGERS:
                        try {
                            curInt = new BigInteger(curString);
                        }
                        catch (NumberFormatException e) {
                            continue;
                        }
                        if (minInt == null) {
                            minInt = curInt;
                            minString = minInt.toString();
                            min_index = i;
                        } else {
                            if (minInt.compareTo(curInt) > 0) {
                                minInt = curInt;
                                minString = minInt.toString();
                                min_index = i;
                            }
                        }
                        break;
                }
            }
            if (!hasData)
                break;

            switch (clArgs.getSortMode()) {
                case ASCENDING:
                    outBuf.append(minString);
                    outBuf.append("\n");
                    break;
                case DESCENDING:
                    outBuf.insert(0, minString);
                    outBuf.insert(minString.length(), "\n");
                    break;
            }

            it[min_index].next();
        }

        try {
            FileWriter writer = new FileWriter(clArgs.getOutFile());
            writer.append(outBuf);
            writer.flush();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}