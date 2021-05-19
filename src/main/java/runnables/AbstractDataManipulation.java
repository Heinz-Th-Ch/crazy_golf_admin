package runnables;

import enumerations.DataManipulationFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract definitions of all needed data manipulation runners of crazy golf application.
 */
public abstract class AbstractDataManipulation<T> implements Runnable {

    private final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

    private final List<T> characteristics;

    private final List<T> resultCharacteristics = new ArrayList<>();
    private T resultCharacteristic;

    private DataManipulationFunction function;

    public AbstractDataManipulation(List<T> characteristics) {
        this.characteristics = characteristics;
    }

    public void run() {

        while (function != DataManipulationFunction.BACK) {
            try {
                String command = readConsoleCommand();
                function = DataManipulationFunction.valueOf(command);
                if (function == DataManipulationFunction.BACK) {
                    continue;
                }
                switch (function) {
                    case DELETE:
                        deleteCharacteristic();
                        break;
                    case INSERT:
                        insertCharacteristic();
                        break;
                    case LIST:
                        listCharacteristics();
                        break;
                    case SELECT:
                        selectCharacteristic();
                        break;
                    case UPDATE:
                        updateCharacteristic();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                function = DataManipulationFunction.BACK;
            }

        }

    }

    public void clearResultCharacteristics() {
        resultCharacteristics.clear();
    }

    public List<T> getCharacteristics() {
        return characteristics;
    }

    public List<T> getResultCharacteristics() {
        return resultCharacteristics;
    }

    public T getResultCharacteristic() {
        return resultCharacteristic;
    }

    public void setResultCharacteristic(T resultCharacteristic) {
        this.resultCharacteristic = resultCharacteristic;
    }

    /**
     *
     */
    protected abstract void deleteCharacteristic();

    protected abstract void insertCharacteristic();

    protected abstract void listCharacteristics();

    protected abstract void selectCharacteristic();

    protected abstract void updateCharacteristic();

    private String readConsoleCommand() throws IOException {
        System.out.println("Please enter one of the following commands: "
                + DataManipulationFunction.mainCommands());
        return console.readLine().toUpperCase();
    }

}
