package XML;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

class StaXParser
{
    static final String NAME = "name";
    static final String NODE = "node";
    static final String CHUNK = "chunk";
    static final String INPUT = "input";
    @SuppressWarnings({ "unchecked", "null" })
    public List<Item> readConfig(String configFile)
    {
        boolean flag = false;
        List<Item> items = new ArrayList<Item>();
        try
        {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(configFile); //change to BufferedReader
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Item item = null;
            while (eventReader.hasNext())
            {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement())
                {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    String elementName = startElement.getName().getLocalPart();
                    switch (elementName)
                    {
                        case CHUNK:
                            item = new Item();
                            // We read the attributes from this tag and add the date
                            // attribute to our object
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals(NAME)) {
                                    item.setName(attribute.getValue());
                                }
                            }
                            break;
                        case NAME:
                            event = eventReader.nextEvent();
                            item.setName(event.asCharacters().getData());
                            break;
                        case NODE:
                            event = eventReader.nextEvent();
                            item.setNode(event.asCharacters().getData());
                            break;
                        case INPUT:
                            flag = true;
                            break;
                    }
                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(CHUNK)) {
                        items.add(item);
                    }
                }
                if (flag == true)
                    break;
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return items;
    }
}
