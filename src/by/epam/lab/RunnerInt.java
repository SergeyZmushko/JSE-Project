package by.epam.lab;

import by.epam.lab.factory.ResultFactory;
import by.epam.lab.service.RunnerLogic;
import by.epam.lab.util.Constants;

public class RunnerInt {
    public static void main(String[] args) {
        RunnerLogic.execute(new ResultFactory(), Constants.FILE_NAME_CSV_1);
    }
}
