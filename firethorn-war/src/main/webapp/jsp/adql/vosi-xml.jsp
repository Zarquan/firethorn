<%@ page
    import="uk.ac.roe.wfau.firethorn.meta.adql.AdqlResource"
    import="uk.ac.roe.wfau.firethorn.meta.adql.AdqlSchema"
    import="uk.ac.roe.wfau.firethorn.meta.adql.AdqlTable"
    import="uk.ac.roe.wfau.firethorn.meta.adql.AdqlColumn"
    import="uk.ac.roe.wfau.firethorn.widgeon.adql.AdqlResourceController"
    contentType="text/xml; charset=UTF-8" 
    session="false"
%><%
AdqlResource resource = (AdqlResource) request.getAttribute(
    AdqlResourceController.TARGET_ENTITY
    ) ;
%><?xml version='1.0' encoding='UTF-8'?>
<tableset
    xsi:type="vods:TableSet"
    xmlns="http://www.ivoa.net/xml/VODataService/v1.1"
    xmlns:vors="http://www.ivoa.net/xml/VOResource/v1.0"
    xmlns:vods="http://www.ivoa.net/xml/VODataService/v1.1"
    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'
    xsi:schemaLocation="
        http://www.ivoa.net/xml/VOResource/v1.0
        http://www.ivoa.net/xml/VOResource/v1.0
        http://www.ivoa.net/xml/VODataService/v1.1
        http://www.ivoa.net/xml/VODataService/v1.1
        "
    ><%
    for(AdqlSchema schema : resource.schemas().select())
        {
        %><schema>
        <name><%= schema.name() %></name>
        <title></title>
        <utype></utype>
        <% 
        if (schema.text() != null)
            {
            %><description><%= schema.text() %></description><%
            }
        else {
            %><description/><%
            }
        for (AdqlTable table : schema.tables().select())
            {
            %>
        <table type='table'>
            <name><%= table.name() %></name>
            <title></title>
            <utype></utype>
            <%
            if (table.text() != null)
                {
                %><description><%= table.text() %></description><%
                }
            else {
                %><description/><%
                }
            %>
            <%
            for (AdqlColumn column : table.columns().select())
                {
                %>
            <column>
                <name><%= column.name() %></name>
                <%
                if (column.text() != null)
                    {
                    %><description><%= column.text() %></description><%
                    }
                else {
                    %><description/><%
                    }

                AdqlColumn.Metadata meta = column.info();
                if ((meta != null) && (meta.adql() != null))
                    {
                    if (meta.adql().ucd() != null)
                        {
                        %>
                        <ucd><%= meta.adql().ucd() %></ucd>
                        <%
                        }
                    else {
                        %></ucd><%
                        }
                    if (meta.adql().unit() != null)
                        {
                        %>
                        <unit><%= meta.adql().unit() %></unit>
                        <%
                        }
                    else {
                        %>
                        </unit>
                        <%
                        }
                    if (meta.adql().utype() != null)
                        {
                        %>
                        <utype><%= meta.adql().utype() %></utype>
                        <%
                        }
                    else {
                        %>
                        </utype>
                        <%
                        }

                    if (meta.adql().type() != null)
                        {
                        %>
                        <type <%= (meta.adql().size() != null) ? "size='"  + meta.adql().size() + "'" : "" %>><%= meta.adql().type() %></type>
                        <%
                        }
                    else {
                        %>
                        <type/>
                        <%
                        }


                    }
                else {
                    %>
                    <!-- no metadata -->
                    <%
                    }
                %>
            </column>
            <% } %>
        </table>
        <% } %>
    </schema>
    <% } %>
</tableset>

