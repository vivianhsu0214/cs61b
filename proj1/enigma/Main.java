package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/**
 * Enigma simulator.
 *
 * @author Zhibo Fan
 */
public final class Main {

    /**
     * Process a sequence of encryptions and decryptions, as
     * specified by ARGS, where 1 <= ARGS.length <= 3.
     * ARGS[0] is the name of a configuration file.
     * ARGS[1] is optional; when present, it names an input file
     * containing messages.  Otherwise, input comes from the standard
     * input.  ARGS[2] is optional; when present, it names an output
     * file for processed messages.  Otherwise, output goes to the
     * standard output. Exits normally if there are no errors in the input;
     * otherwise with code 1.
     */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /**
     * Check ARGS and open the necessary files (see comment on main).
     */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);
        _copyConfig = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /**
     * Return a Scanner reading from the file named NAME.
     */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Return a PrintStream writing to the file named NAME.
     */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Configure an Enigma machine from the contents of configuration
     * file _config and apply it to the messages in _input, sending the
     * results to _output.
     */
    private void process() {
        Machine enigma = readConfig();
        boolean noSetting = true;
        _config.close();
        while (_input.hasNextLine()) {
            String line = _input.nextLine().trim();
            if (line.replaceAll("(\\s)+", "").equals("")) {
                continue;
            }
            if (line.charAt(0) == '*') {
                if (!noSetting) {
                    printMessageLine("\r");
                }
                setUp(enigma, line.replaceAll("\\*", "").trim());
                noSetting = false;
            } else {
                if (noSetting) {
                    throw error("Missing setting!");
                }
                String print = enigma.convert(line.replaceAll("\\s", ""));
                printMessageLine(print);
            }
        }
        _input.close();
        _output.close();
    }

    /**
     * Return an Enigma machine configured from the contents of configuration
     * file _config.
     *
     * @return Machine with initialized
     */
    private Machine readConfig() {
        try {
            String currentLine = null;
            currentLine = nextValidLine(0);
            _alphabet = readAlphabet(currentLine);

            int numRotors = _config.nextInt();
            int numPawls = _config.nextInt();
            _copyConfig.nextInt();
            _copyConfig.nextInt();

            LinkedList<Rotor> archive = new LinkedList<Rotor>();
            archive = fillArchive(archive);
            return new Machine(_alphabet, numRotors, numPawls, archive);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /**
     * Skip to the next valid line.
     * Return null if nothing left.
     *
     * @param option 0: Result without whitespace. 1: Original result.
     */
    private String nextValidLine(int option) {
        String src = "";
        String temp = "";
        boolean valid = false;
        while (_config.hasNextLine()) {
            src = _config.nextLine();
            _copyConfig.nextLine();
            temp = src.replaceAll(" ", "");
            if (temp.length() != 0) {
                valid = true;
                break;
            }
        }
        if (valid) {
            if (option == 0) {
                return temp;
            } else {
                return src;
            }
        }
        return null;
    }

    /**
     * Generate an alphabet with the config string.
     *
     * @param alpha alphabet config
     * @return alphabet object
     */
    private Alphabet readAlphabet(String alpha) {
        alpha = alpha.replaceAll(" ", "");
        alpha = alpha.toUpperCase();
        if (alpha.contains(String.valueOf('('))
                || alpha.contains(String.valueOf(')'))
                || alpha.contains(String.valueOf('+'))
                || alpha.contains(String.valueOf('*'))) {
            throw error(String.format("Including illegal symbol", alpha));
        }
        if (alpha.contains(String.valueOf('-'))) {
            if (alpha.length() != 3 || alpha.indexOf(String.valueOf('-')) != 1
                    || alpha.charAt(0) >= alpha.charAt(2)) {
                throw error(String.format("Unrecognized alphabet", alpha));
            }
            return new CharacterRange(alpha.charAt(0), alpha.charAt(2));
        } else {
            return new SymbolAlphabet(alpha);
        }
    }

    /**
     * Return a list of rotors.
     *
     * @param archive empty list
     * @return archive filed
     */
    private LinkedList<Rotor> fillArchive(LinkedList<Rotor> archive) {
        String rotorInfo = "";
        boolean newInfoFlag;
        boolean firstInfoFlag = true;
        while (_config.hasNext()) {
            String thisLine = nextValidLine(1);
            newInfoFlag = thisLine.trim().charAt(0) != '(';

            if (firstInfoFlag) {
                rotorInfo = thisLine;
                firstInfoFlag = false;
                continue;
            }
            if (newInfoFlag) {
                archive.add(readRotor(rotorInfo.trim()));
                rotorInfo = thisLine;
            } else {
                rotorInfo += thisLine;
            }
        }
        archive.add(readRotor(rotorInfo.trim()));
        return archive;
    }


    /**
     * Return a rotor, reading its description from _config.
     *
     * @param rotorInfo concatenated line of rotor config
     * @return rotor initialized
     */
    private Rotor readRotor(String rotorInfo) {
        try {
            String[] temp = rotorInfo.split("(\\s)+");
            String name = temp[0].toUpperCase();
            String feature = temp[1].replaceAll("(\\s)+", "");
            String[] permuteTemp = new String[temp.length - 2];
            System.arraycopy(temp, 2, permuteTemp, 0, permuteTemp.length);
            String perm = "";
            for (String s : permuteTemp) {
                perm += s;
            }
            Permutation objPerm = new Permutation(perm.trim(), _alphabet);
            char type = feature.charAt(0);
            switch (type) {
                case 'M':
                    return new MovingRotor(name, objPerm,
                            feature.substring(1));
                case 'N':
                    return new FixedRotor(name, objPerm);
                case 'R':
                    return new Reflector(name, objPerm);
                default:
                    throw error("Wrong rotor type");
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /**
     * Set M according to the specification given on SETTINGS,
     * which must have the format specified in the assignment.
     */
    private void setUp(Machine M, String settings) {
        String[] temp = settings.trim().split("(\\s)+");
        int num = 0;
        for (String s : temp) {
            if (M.contains(s)) {
                num += 1;
            }
        }
        String[] rotors = new String[num];
        System.arraycopy(temp, 0, rotors, 0, num);
        M.insertRotors(rotors);
        M.setRotors(temp[num]);
        String permute = "";
        for (int i = num + 1; i < temp.length; i++) {
            permute += temp[i].trim();
        }
        Permutation plug = new Permutation(permute, _alphabet);
        M.setPlugboard(plug);
    }

    /**
     * Print MSG in groups of five (except that the last group may
     * have fewer letters).
     */
    private void printMessageLine(String msg) {
        String section = "";
        for (int i = 0; i < msg.length(); i += 1) {
            section += msg.charAt(i);
            if (i % 5 == 4) {
                if (i != msg.length() - 1) {
                    section += " ";
                }
                _output.print(section);
                section = "";
            }
        }
        _output.println(section);
    }

    /**
     * Alphabet used in this machine.
     */
    private Alphabet _alphabet;

    /**
     * Source of input messages.
     */
    private Scanner _input;

    /**
     * Source of machine configuration.
     */
    private Scanner _config;

    /**
     * File for encoded/decoded messages.
     */
    private PrintStream _output;

    /**
     * A backup of config.
     */
    private Scanner _copyConfig;
}
