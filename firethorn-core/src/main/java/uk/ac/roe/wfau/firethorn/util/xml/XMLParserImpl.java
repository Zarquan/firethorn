/*
 *  Copyright (C) 2013 Royal Observatory, University of Edinburgh, UK
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package uk.ac.roe.wfau.firethorn.util.xml;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 */
@Slf4j
public class XMLParserImpl
    implements XMLParser
    {

    /**
     * Public constructor.
     *
     */
    public XMLParserImpl()
        {
        this(null);
        }

    /**
     * Public constructor.
     *
     */
    public XMLParserImpl(final String namespace, final String name)
        {
        this(
            new QName(
                namespace,
                name
                )
            );
        }

    /**
     * Public constructor.
     *
     */
    public XMLParserImpl(final QName qname)
        {
        this(
            false,
            qname
            );
        }

    /**
     * Public constructor.
     *
     */
    public XMLParserImpl(boolean strict, final QName qname)
        {
        log.debug("XMLReaderImpl(QName)");
        log.debug("  QName [{}]", qname);
        this.qname  = qname;
        this.strict = strict;
        }

    /**
     * The XML {@link QName} this reader matches.
     *
     */
    private final QName qname;

    /**
     * A flag to control if we are strict on namespaces.
     * By default this is set to false.
     * If this <em>false</em>, then we will match elements with an {@link XMLConstants#NULL_NS_URI empty} namespace.
     * If this <em>true</em>, then we will not match an element with an {@link XMLConstants#NULL_NS_URI empty} namespace.
     * @see XMLConstants#NULL_NS_URI
     * 
     */
    private boolean strict ;

    /**
     * The XML {@link QName} this reader matches.
     *
     */
    @Override
    public QName qname()
        {
        return this.qname;
        }

    @Override
    public boolean match(final XMLEventReader reader)
    throws XMLParserException
        {
        log.debug("match(XMLEventReader)");
        log.debug("  QName [{}]", this.qname());
        return this.match(
            this.peek(
                reader
                )
            );
        }

    @Override
    public boolean match(final XMLEvent event)
    throws XMLParserException
        {
        log.debug("match(XMLEvent)");
        log.debug("  QName [{}]", this.qname());
        log.debug("  Event [{}]", event);
        if (null != event)
            {
            if (event.isStartElement())
                {
                return this.match(
                    event.asStartElement()
                    );
                }
            }
        return false;
        }

    @Override
    public boolean match(final StartElement event)
    throws XMLParserException
        {
        log.debug("match(StartElement)");
        log.debug("  QName [{}]", this.qname());
        log.debug("  Event [{}]", event);
        return this.match(
            event.getName()
            );
        }

    @Override
    public void done(final XMLEventReader reader)
    throws XMLParserException
        {
        log.debug("done(XMLEventReader)");
        log.debug("  QName [{}]", this.qname());
        //
        // Peek at the next element and check we are done.
        final XMLEvent event = this.peek(reader);
        if (this.done(event))
            {
            this.next(
                reader
                );
            }
        else
            {
            log.debug("End element expected [{}][{}]", this.qname(), event);
            throw new XMLParserException(
                "End element expected",
                this.qname(),
                event
                );
            }
        }

    @Override
    public boolean done(final XMLEvent event)
        {
        log.debug("done(XMLEvent)");
        log.debug("  QName [{}]", this.qname());
        log.debug("  Event [{}]", event);
        if (event.isEndElement())
            {
            return this.done(
                event.asEndElement()
                );
            }
        else
            {
            return false;
            }
        }

    /**
     * Check if an {@link EndElement} matches our {@link QName}.
     * @returns true if the {@link EndElement} matches.
     *
     */
    protected boolean done(final EndElement event)
        {
        log.debug("done(EndElement)");
        log.debug("  QName [{}]", this.qname());
        log.debug("  Event [{}]", event);
        return match(
            event.getName()
            );
        }

    @Override
    public void skip(final XMLEventReader source)
    throws XMLParserException
        {
        log.debug("skip(XMLEventReader)");
        log.debug("  QName [{}]", this.qname());
        while (done(peek(source)) == false)
            {
            final XMLEvent skip = next(
                source
                );
            log.debug("Skipping [{}]", StringUtils.abbreviate(skip.toString(), 10));
            }
        }

    /**
     * Check if a {@link QName} matches ours.
     * @param theirs The {@link QName} to check.
     * @return true if the {@link QName} matches.
     * 
     */
    protected boolean match(final QName theirs)
        {
        log.debug("match(QName)");
        log.debug("  Ours   [{}]", this.qname());
        log.debug("  Theirs [{}]", theirs);
        //
        // If we have a QName.
        if (null != this.qname())
            {
            //
            // Check if the QName matches ours.
            if (this.qname().equals(theirs))
                {
                log.debug("QName matches");
                return true;
                }
            //
            // If the element QName does not match ours.
            else
                {
                //
                // If we are not strict, and their qname doesn't have a namespace. 
                if ((strict == false) && (XMLConstants.NULL_NS_URI.equals(theirs.getNamespaceURI())))
                    {
                    log.debug("QName has a null namespace");
                    //
                    // Check if the local name matches.
                    if (this.qname().getLocalPart().equals(theirs.getLocalPart()))
                        {
                        log.debug("QName local name matches");
                        return true;
                        }
                    else {
                        log.debug("QName local name does not match");
                        return false;
                        }
                    }
                else {
                    log.debug("QName does not match");
                    return false;
                    }
                }
            }
        //
        // If we don't have a QName, assume we accept any.
        else
            {
            log.debug("QName is null");
            return true;
            }
        }
    
    @Override
    public XMLEvent next(final XMLEventReader reader)
    throws XMLParserException
        {
        log.debug("next(XMLEventReader)");
        log.debug("  QName [{}]", this.qname());
        try {
            return reader.nextEvent();
            }
        catch (final XMLStreamException ouch)
            {
            log.debug("XMLStreamException while reading XML reader [{}]", ouch.getMessage());
            throw new XMLParserException(
                "XMLStreamException while reading XML stream",
                ouch
                );
            }
        }

    @Override
    public XMLEvent peek(final XMLEventReader reader)
    throws XMLParserException
        {
        log.debug("peek(XMLEventReader)");
        log.debug("  QName [{}]", this.qname());
        try
            {
            //
            // Check if there is another event.
            if (reader.hasNext())
                {
                //
                // Peek at the next event.
                final XMLEvent event = reader.peek();
                log.debug("  Event [{}][{}]", typename(event), event);
                switch (event.getEventType())
                    {
                    //
                    // Start or end element are valid.
                    case XMLStreamConstants.START_ELEMENT:
                    case XMLStreamConstants.END_ELEMENT:
                        return event;
                        //
                        // End document is valid.
                    case XMLStreamConstants.END_DOCUMENT:
                        return event;
                        //
                        // Skip processing events.
                    case XMLStreamConstants.DTD:
                    case XMLStreamConstants.PROCESSING_INSTRUCTION:
                    case XMLStreamConstants.START_DOCUMENT:
                        //
                        // Move to the next event and process that.
                        reader.nextEvent();
                        return this.peek(reader);
                        //
                        // Skip comments.
                    case XMLStreamConstants.COMMENT:
                        //
                        // Move to the next event and process that.
                        reader.nextEvent();
                        return this.peek(reader);
                        //
                        // Skip whitespace.
                    case XMLStreamConstants.SPACE:
                        //
                        // Move to the next event and process that.
                        reader.nextEvent();
                        return this.peek(reader);
                        //
                        // Skip character events if they are whitespace.
                    case XMLStreamConstants.CHARACTERS:
                        if (event.asCharacters().isWhiteSpace())
                            {
                            //
                            // Move to the next event and process that.
                            reader.nextEvent();
                            return peek(reader);
                            }
                        else
                            {
                            return event;
                            }
                        //
                        // Return null for other event types.
                    case XMLStreamConstants.ATTRIBUTE:
                    case XMLStreamConstants.CDATA:
                    case XMLStreamConstants.ENTITY_DECLARATION:
                    case XMLStreamConstants.ENTITY_REFERENCE:
                    case XMLStreamConstants.NAMESPACE:
                    case XMLStreamConstants.NOTATION_DECLARATION:
                    default:
                        return null;
                    }
                }
            //
            // If the stream is empty.
            else
                {
                return null;
                }
            }
        catch (final XMLStreamException ouch)
            {
            log.debug("XMLStreamException while reading XML stream [{}]", ouch.getMessage());
            throw new XMLParserException(
                "XMLStreamException while reading XML stream",
                ouch
                );
            }
        }

    @Override
    public StartElement start(final XMLEventReader reader)
    throws XMLParserException
        {
        log.debug("start(XMLEventReader)");
        log.debug("  QName [{}]", this.qname());
        //
        // Find the next element event.
        XMLEvent event = this.peek(reader);
        //
        // If we found an element event.
        if (null != event)
            {
            //
            // Read the element from the reader.
            try {
                event = reader.nextEvent();
                }
            catch (final XMLStreamException ouch)
                {
                log.debug("XMLStreamException while reading XML stream [{}]", ouch.getMessage());
                throw new XMLParserException(
                    "XMLStreamException while reading XML stream",
                    ouch
                    );
                }
            log.debug("  Next [{}][{}]", typename(event), event);
            switch (event.getEventType())
                {
                //
                // Start element is what we are looking for.
                case XMLStreamConstants.START_ELEMENT:
                    return verify(
                        event.asStartElement()
                        );
                //
                // Throw exception for other event types.
                default:
                    log.debug("XML start element expected [{}][{}]", typename(event), event);
                    throw new XMLParserException(
                        "XML start element expected",
                        this.qname(),
                        event
                        );
                }
            }
        //
        // If we didn't find an element.
        else
            {
            log.debug("XML element expected [null]");
            throw new XMLParserException(
                "XML start element expected",
                this.qname(),
                event
                );
            }
        }

    /**
     * Verify a {@link StartElement} matches our {@link QName}.
     * @param element The {@link StartElement} to check.
     * @return The {@link StartElement}.
     * @throws XMLParserException If the {@link StartElement} is not what we expect.
     *
     */
    public StartElement verify(final StartElement  element)
    throws XMLParserException
        {
        log.debug("start(StartElement)");
        log.debug("  QName [{}]", this.qname());

        if (this.qname() == null)
            {
            return element ;
            }
        else {
            if (match(element))
                {
                return element ;
                }
            else {
                throw new UnexpectedXMLElementException(
                    this.qname(),
                    element
                    );
                }
            }
        }

    /**
     * Decode a {@link XMLEvent} type into a human readable name.
     *
     */
    public static String typename(final XMLEvent event)
        {
        return typename(event.getEventType());
        }

    /**
     * Decode a {@link XMLEvent} type into a human readable name.
     *
     */
    public static String typename(final int type)
        {
        switch (type)
            {
            case XMLStreamConstants.ATTRIBUTE:
                return "ATTRIBUTE";
            case XMLStreamConstants.CDATA:
                return "CDATA";
            case XMLStreamConstants.CHARACTERS:
                return "CHARACTERS";
            case XMLStreamConstants.COMMENT:
                return "COMMENT";
            case XMLStreamConstants.DTD:
                return "DTD";
            case XMLStreamConstants.END_DOCUMENT:
                return "END_DOCUMENT";
            case XMLStreamConstants.END_ELEMENT:
                return "END_ELEMENT";
            case XMLStreamConstants.ENTITY_DECLARATION:
                return "ENTITY_DECLARATION";
            case XMLStreamConstants.ENTITY_REFERENCE:
                return "ENTITY_REFERENCE";
            case XMLStreamConstants.NAMESPACE:
                return "NAMESPACE";
            case XMLStreamConstants.NOTATION_DECLARATION:
                return "NOTATION_DECLARATION";
            case XMLStreamConstants.PROCESSING_INSTRUCTION:
                return "PROCESSING_INSTRUCTION";
            case XMLStreamConstants.SPACE:
                return "SPACE";
            case XMLStreamConstants.START_DOCUMENT:
                return "START_DOCUMENT";
            case XMLStreamConstants.START_ELEMENT:
                return "START_ELEMENT";
            default:
                return "unknown";
            }
        }
    }
