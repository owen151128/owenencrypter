package kr.owens.parser;

import kr.owens.Constants;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Providing features related to Input Arguments Parser class
 */
public class InputArgumentsParser {
    private static final Logger logger = Logger.getLogger(InputArgumentsParser.class.getName());

    public static final String ENCRYPT_MODE = "-e";
    public static final String DECRYPT_MODE = "-d";
    public static final String OUTPUT_MODE = "-o";
    public static final String HELP_MODE = "--h";
    public static final String VERSION_MODE = "--v";

    private HashSet<String> optionSet = null;
    private HashSet<String> printOptionSet = null;
    private HashMap<String, String> valueMap = null;

    public String[] arguments;

    /**
     * Default Costructor
     *
     * @param args encrypter's arguments array
     */
    public InputArgumentsParser(String[] args) {
        arguments = args;
        initialization();
    }

    /**
     * Input Arguments Parser initialization
     */
    public void initialization() {
        if (optionSet != null) {
            optionSet.clear();
        }

        if (valueMap != null) {
            valueMap.clear();
        }

        optionSet = new HashSet<>();
        printOptionSet = new HashSet<>();
        valueMap = new HashMap<>();

        addOption();
    }

    /**
     * Parse Input Arguments
     *
     * @return if success, return true
     */
    public boolean parser() {
        boolean isValid = argumentsCheck(arguments);

        if (!isValid) {
            return false;
        }

        String opt = null;
        String value;

        boolean isPrintOption = false;
        boolean isOptionEntered = false;

        for (String argument : arguments) {

            for (String printOption : printOptionSet) {
                if (argument.equals(printOption)) {
                    isPrintOption = true;
                    opt = argument;
                    break;
                }
            }

            if (isPrintOption) {
                if (opt.equals(HELP_MODE)) {
                    printUsage();
                } else {
                    printVersion();
                }
                isPrintOption = false;

            } else {
                if (!isOptionEntered) {
                    opt = argument;

                    if (!isExistOption(opt)) {
                        logger.log(Level.SEVERE, "This option does not exist : " + opt);

                        return false;
                    }

                    isOptionEntered = true;

                } else {
                    value = argument;
                    addValueMap(opt, value);
                    isOptionEntered = false;
                }
            }
        }

        return true;
    }

    /**
     * Add option into OptionSet
     */
    public void addOption() {
        optionSet.add(ENCRYPT_MODE);
        optionSet.add(DECRYPT_MODE);
        optionSet.add(OUTPUT_MODE);
        printOptionSet.add(HELP_MODE);
        printOptionSet.add(VERSION_MODE);
    }

    /**
     * check input option exist optionSet
     *
     * @param option input Option
     * @return if exist, return true
     */
    public boolean isExistOption(String option) {
        return optionSet.contains(option);
    }

    /**
     * check input option exist valueMap
     *
     * @param option input Option
     * @return if exist, return true
     */
    public boolean isExistValue(String option) {
        return valueMap.containsKey(option);
    }

    /**
     * add into valueMap
     *
     * @param option input Option
     * @param value  input Value
     */
    public void addValueMap(String option, String value) {
        valueMap.put(option, value);
    }

    /**
     * verify the Arguments are correct
     *
     * @param args encrypter's arguments
     * @return if correct, return true
     */
    public boolean argumentsCheck(String[] args) {
        int length = args.length;

        if (length < 1) {
            printUsage();

            return false;
        }

        return true;
    }

    /**
     * check value exist in valueMap
     *
     * @return if valueMap isn't empty, return true
     */
    public boolean isValueExistInValueMap() {
        if (valueMap == null) {
            return false;
        } else {
            return !valueMap.isEmpty();
        }
    }

    public Path getTargetPath() {
        if (!isValueExistInValueMap()) {
            return null;
        }

        if (valueMap.containsKey(ENCRYPT_MODE)) {
            return new File(new File(valueMap.get(ENCRYPT_MODE)).getAbsolutePath()).toPath();
        } else if (valueMap.containsKey(DECRYPT_MODE)) {
            return new File(new File(valueMap.get(DECRYPT_MODE)).getAbsolutePath()).toPath();
        }

        return null;
    }

    public Path getOutputPath() {
        if (!isValueExistInValueMap()) {
            return null;
        }

        if (valueMap.containsKey(OUTPUT_MODE)) {
            return new File(new File(valueMap.get(OUTPUT_MODE)).getAbsolutePath()).toPath();
        }

        return null;
    }

    public Constants.MODE getMode() {
        if (!isValueExistInValueMap()) {
            return null;
        }

        if (valueMap.containsKey(ENCRYPT_MODE)) {
            return Constants.MODE.ENCRYPT;
        } else if (valueMap.containsKey(DECRYPT_MODE)) {
            return Constants.MODE.DECRYPT;
        }

        return null;
    }

    /**
     * print usage Method
     */
    private void printUsage() {
        System.out.println("Usage : owenEncrypter [options] <input-file>" +
                "\n" +
                "where <input-file> are any file possible!" +
                "\n" +
                "and options are :" +
                "\n" +
                "-e \t\t encrypt the file <input-file>" +
                "\n" +
                "-d \t\t decrypt the file <input-file>" +
                "\n" +
                "-o \t\t set output result in <output-file>" +
                "\n" +
                "-h \t\t print this message." +
                "\n" +
                "-v \t\t print the version of owenEncrypter");
    }

    /**
     * print version Method
     */
    private void printVersion() {
        System.out.println("owenEncrypter version : 0.1");
    }
}
