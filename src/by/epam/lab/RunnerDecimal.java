package by.epam.lab;

import by.epam.lab.factory.DecimalResultFactory;
import by.epam.lab.service.RunnerLogic;
import by.epam.lab.util.Constants;

public class RunnerDecimal {
    public static void main(String[] args) {
        RunnerLogic.execute(new DecimalResultFactory(), Constants.FILE_NAME_XML);
    }
}
