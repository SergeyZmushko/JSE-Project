package by.epam.lab;

import by.epam.lab.factory.HalfResultFactory;
import by.epam.lab.service.RunnerLogic;
import by.epam.lab.util.Constants;

public class RunnerHalf {
    public static void main(String[] args) {
        RunnerLogic.execute(new HalfResultFactory(), Constants.FILE_NAME_CSV_2);
    }
}
