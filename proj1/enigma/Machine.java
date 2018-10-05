package enigma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;

import static enigma.EnigmaException.*;

/**
 * Class that represents a complete enigma machine.
 *
 * @author Zhibo Fan
 */
class Machine {

    /**
     * A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     * and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     * available rotors.
     */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        if (numRotors <= 1) {
            throw error("There has to be more than 1 rotors!");
        }
        if (pawls < 0 || pawls >= numRotors) {
            throw error("Wrong number of pawls!");
        }
        _alphabet = alpha;
        _plug = new Permutation("", new CharacterRange('A', 'Z'));
        _slots = new Rotor[numRotors];
        _archive = new HashMap<String, Rotor>();
        for (Rotor rotor : allRotors) {
            _archive.put(rotor.name(), rotor);
        }
        _numPawls = pawls;
    }

    /**
     * Return the number of rotor slots I have.
     */
    int numRotors() {
        return _slots.length;
    }

    /**
     * Return the number pawls (and thus rotating rotors) I have.
     */
    int numPawls() {
        return _numPawls;
    }

    /**
     * Set my rotor slots to the rotors named ROTORS from my set of
     * available rotors (ROTORS[0] names the reflector).
     * Initially, all rotors are set at their 0 setting.
     */
    void insertRotors(String[] rotors) {
        if (rotors.length > _slots.length) {
            System.arraycopy(rotors, 0,
                    rotors, 0, _slots.length);
        }
        for (int i = 0; i < rotors.length; i++) {
            String key = rotors[i];
            if (_archive.containsKey(key)) {
                if (i == 0 && !_archive.get(key).reflecting()) {
                    throw error(String.format(
                            "The first rotor is not a reflector!", key));
                }
                if (i < _slots.length - _numPawls
                        && _archive.get(key).rotates()) {
                    throw error(String.format(
                            "Some rotors are expected to be fixed!", key));
                }
                _slots[i] = _archive.get(key);
            }
        }
    }

    /**
     * Set my rotors according to SETTING, which must be a string of
     * numRotors()-1 upper-case letters. The first letter refers to the
     * leftmost rotor setting (not counting the reflector).
     */
    void setRotors(String setting) {
        if (setting.length() != _slots.length - 1) {
            throw error(String.format("Size of setting info is not correct!",
                    setting, setting.length()));
        }
        for (int i = 0; i < setting.length(); i += 1) {
            _slots[i + 1].set(setting.charAt(i));
        }
    }

    /**
     * Set the plugboard to PLUGBOARD.
     */
    void setPlugboard(Permutation plugboard) {
        _plug = plugboard;
    }

    /**
     * Returns the result of converting the input character C (as an
     * index in the range 0..alphabet size - 1), after first advancing
     * <p>
     * the machine.
     */
    int convert(int c) {
        int entry = _plug.permute(c);

        int numFixed = _slots.length - _numPawls;
        boolean[] isGoingToAdvance = new boolean[_numPawls];
        isGoingToAdvance[_numPawls - 1] = true;
        for (int i = numFixed; i < _slots.length; i += 1) {
            if (_slots[i].atNotch()) {
                if (i == numFixed) {
                    isGoingToAdvance[0] = true;
                } else {
                    isGoingToAdvance[i - numFixed] = true;
                    isGoingToAdvance[i - numFixed - 1] = true;
                }
            }
        }
        for (int i = 0; i < _numPawls; i++) {
            if (isGoingToAdvance[i]) {
                _slots[i + numFixed].advance();
            }
        }

        for (int i = _slots.length - 1; i >= 0; i -= 1) {
            entry = _slots[i].convertForward(entry);
        }
        for (int i = 1; i < _slots.length; i += 1) {
            entry = _slots[i].convertBackward(entry);
        }
        entry = _plug.permute(entry);

        return entry;
    }

    /**
     * Returns the encoding/decoding of MSG, updating the state of
     * the rotors accordingly.
     */
    String convert(String msg) {
        msg = msg.replaceAll(",", "");
        msg = msg.toUpperCase();
        char[] buffer = new char[msg.length()];
        for (int i = 0; i < msg.length(); i += 1) {
            buffer[i] = _alphabet.toChar(convert(
                    _alphabet.toInt(msg.charAt(i))));
        }
        String result = new String(buffer);
        return result;
    }

    /**
     * Returns true iff the archive contains the rotor
     */
    boolean contains(String name) {
        return _archive.containsKey(name);
    }

    /**
     * Common alphabet of my rotors.
     */
    private final Alphabet _alphabet;

    /**
     * PlugBoard.
     */
    private Permutation _plug;

    /**
     * Rotor slots.
     */
    private Rotor[] _slots;

    /**
     * An archive of available rotors.
     */
    private HashMap<String, Rotor> _archive;

    /**
     * Number of pawls.
     */
    private int _numPawls;
}
