package ru.xodkewic.mergesort;

public class CommandLineArguments {
    public enum SortMode {
        ASCENDING,
        DESCENDING
    };
    public enum DataType {
        STRINGS,
        INTEGERS,
    };
    private SortMode sortMode;
    private DataType dataType;
    private String outFile;
    private String[] inFiles;

    public CommandLineArguments(String[] args) throws Exception {
        int i, n_input_files;

        sortMode = SortMode.ASCENDING;
        for (i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                switch (args[i]) {
                    case "-a":
                        sortMode = SortMode.ASCENDING;
                        break;
                    case "-d":
                        sortMode = SortMode.DESCENDING;
                        break;
                    case "-s":
                        dataType = DataType.STRINGS;
                        break;
                    case "-i":
                        dataType = DataType.INTEGERS;
                        break;
                }
            } else break;
        }

        if (dataType == null) {
            throw new Exception("Data type has not been specified");
        }

        outFile = args[i++];
        n_input_files = args.length - i;
        if (n_input_files <= 0) {
            throw new Exception("Input files not specified");
        }

        inFiles = new String[n_input_files];
        for (int x = 0; i < args.length; i++, x++) {
            inFiles[x] = args[i];
        }
    }

    public DataType getDataType() {
        return dataType;
    }

    public SortMode getSortMode() {
        return sortMode;
    }

    public String getOutFile() {
        return outFile;
    }

    public String[] getInFiles() {
        return inFiles;
    }
}
