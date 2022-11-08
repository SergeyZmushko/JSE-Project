package by.epam.lab.dao.impl;

import by.epam.lab.bean.Result;
import by.epam.lab.dao.ResultDao;
import by.epam.lab.exceptions.ParseRuntimeException;
import by.epam.lab.exceptions.SourceException;
import by.epam.lab.factory.ResultFactory;
import by.epam.lab.handler.XMLHandler;
import by.epam.lab.util.Constants;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Iterator;

public class ResultImplXml implements ResultDao {
    private Iterator<Result> iterator;
    private XMLHandler handler;

    public ResultImplXml(String fileName, ResultFactory factory) throws SourceException {
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            handler = new XMLHandler(factory);
            parser.parse(fileName, handler);
            iterator = handler.getResults().iterator();
        } catch (IOException e) {
            throw new SourceException(e.getMessage());
        } catch (SAXException | ParserConfigurationException e) {
            throw new ParseRuntimeException(Constants.WRONG_DATA_XML);
        }
    }

    @Override
    public Result nextResult() {
        return iterator.next();
    }

    @Override
    public boolean hasResult() {
        return iterator.hasNext();
    }

    @Override
    public void close() {
        iterator = null;
    }
}
