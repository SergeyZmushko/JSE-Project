package by.epam.lab.handler;

import by.epam.lab.bean.Result;
import by.epam.lab.factory.ResultFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import static by.epam.lab.util.Constants.*;

import java.util.ArrayList;
import java.util.List;

public class XMLHandler extends DefaultHandler {
    private enum ResultEnum {
        TESTS, STUDENT, LOGIN, RESULTS, TEST
    }

    private final ResultFactory factory;
    private final List<Result> results = new ArrayList<>();
    private ResultEnum currentEnum;
    private String currentLogin;

    public XMLHandler(ResultFactory factory) {
        this.factory = factory;
    }

    public List<Result> getResults() {
        return results;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentEnum = ResultEnum.valueOf(qName.toUpperCase());
        if (currentEnum == ResultEnum.TEST) {
            results.add(factory.getResultFromFactory(currentLogin, attributes.getValue(TEST_IND_XML),
                    attributes.getValue(DATE_IND_XML), attributes.getValue(MARK_IND_XML)));
        }
    }

    public void characters(char[] ch, int start, int length) {
        if (currentEnum == ResultEnum.LOGIN) {
            String str = new String(ch, start, length).trim();
            if (!str.isEmpty()) {
                currentLogin = str;
            }
        }
    }
}

