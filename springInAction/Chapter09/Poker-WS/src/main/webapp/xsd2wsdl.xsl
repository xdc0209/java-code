<!--
  ~ Copyright 2006 Arjen Poutsma.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:wsdlsoap="http://schemas.xmlsoap.org/wsdl/soap/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema">
    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    <!-- Parameters -->
    <!-- Name for the WSDL definition, which is also uses as a prefix for portType, binding, and service -->
    <xsl:param name="name"/>
    <!-- Location where the service can be found -->
    <xsl:param name="location"/>
    <!-- Namespace of the schema -->
    <xsl:param name="schemaNamespace" select="/xsd:schema/@targetNamespace"/>
    <!-- Prefix for namespace of the schema -->
    <xsl:param name="schemaPrefix" select="'schema'"/>
    <!-- WSDL Namespace, by default the same as the schema namespace -->
    <xsl:param name="targetNamespace" select="$schemaNamespace"/>
    <!-- Prefix for WSDL -->
    <xsl:param name="prefix" select="'tns'"/>
    <xsl:param name="requestSuffix" select="'Request'"/>
    <xsl:param name="responseSuffix" select="'Response'"/>
    <xsl:param name="messageSuffix" select="'Message'"/>
    <xsl:param name="inputSuffix" select="'Input'"/>
    <xsl:param name="outputSuffix" select="'Output'"/>
    <xsl:param name="portTypeSuffix" select="'PortType'"/>
    <xsl:param name="bindingSuffix" select="'Binding'"/>
    <xsl:param name="serviceSuffix" select="'Service'"/>
    <xsl:param name="portSuffix" select="'Port'"/>
    <xsl:param name="soapActionPrefix" select="$targetNamespace"/>

    <xsl:variable name="elements" select="/xsd:schema/xsd:element"/>

    <!-- Elements that end with the request suffix, keyed under operation name -->
    <xsl:key name="requestElements" match="/xsd:schema/xsd:element[ends-with(@name, $requestSuffix)]"
             use="substring-before(@name,$requestSuffix)"/>
    <!-- Elements that end with the response suffix, keyed under operation name -->
    <xsl:key name="responseElements" match="/xsd:schema/xsd:element[ends-with(@name, $responseSuffix)]"
             use="substring-before(@name,$responseSuffix)"/>

    <!-- Global template -->
    <xsl:template match="/">
        <xsl:call-template name="definitions"/>
    </xsl:template>

    <!-- WSDL definitions -->
    <xsl:template name="definitions">
        <wsdl:definitions>
            <xsl:attribute name="name">
                <xsl:value-of select="$name"/>
            </xsl:attribute>
            <xsl:namespace name="{$schemaPrefix}" select="$schemaNamespace"/>
            <xsl:namespace name="{$prefix}" select="$targetNamespace"/>
            <xsl:attribute name="targetNamespace">
                <xsl:value-of select="$targetNamespace"/>
            </xsl:attribute>
            <xsl:call-template name="types"/>
            <xsl:for-each select="$elements">
                <xsl:call-template name="message">
                    <xsl:with-param name="elementName">
                        <xsl:value-of select="@name"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
            <xsl:call-template name="portType"/>
        </wsdl:definitions>
    </xsl:template>

    <!-- WSDL types  -->
    <xsl:template name="types">
        <wsdl:types>
            <xsl:copy-of select="/xsd:schema"/>
        </wsdl:types>
    </xsl:template>

    <!-- WSDL Message -->
    <xsl:template name="message">
        <xsl:param name="elementName"/>
        <wsdl:message>
            <xsl:attribute name="name">
                <xsl:value-of select="concat($elementName,$messageSuffix)"/>
            </xsl:attribute>
            <wsdl:part name="body">
                <xsl:attribute name="element">
                    <xsl:value-of select="concat($schemaPrefix,':',$elementName)"/>
                </xsl:attribute>
            </wsdl:part>
        </wsdl:message>
    </xsl:template>

    <!-- WSDL Port type -->
    <xsl:template name="portType">
        <xsl:variable name="portTypeName" select="concat($name, $portTypeSuffix)"/>
        <wsdl:portType>
            <xsl:attribute name="name">
                <xsl:value-of select="$portTypeName"/>
            </xsl:attribute>
            <!-- Find all elements that end with $requestSuffix, and start an operation for that  -->
            <xsl:for-each select="/xsd:schema/xsd:element[ends-with(@name, $requestSuffix)]">
                <xsl:call-template name="portTypeOperation">
                    <xsl:with-param name="operationName">
                        <xsl:value-of select="substring-before(@name,$requestSuffix)"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </wsdl:portType>
        <xsl:call-template name="binding">
            <xsl:with-param name="portTypeName" select="$portTypeName"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="portTypeOperation">
        <xsl:param name="operationName"/>
        <wsdl:operation>
            <xsl:attribute name="name">
                <xsl:value-of select="$operationName"/>
            </xsl:attribute>
            <xsl:for-each select="key('requestElements', $operationName)">
                <wsdl:input>
                    <xsl:attribute name="name">
                        <xsl:value-of select="concat($operationName, $inputSuffix)"/>
                    </xsl:attribute>
                    <xsl:attribute name="message">
                        <xsl:value-of select="concat($prefix,':',@name,$messageSuffix)"/>
                    </xsl:attribute>
                </wsdl:input>
            </xsl:for-each>
            <xsl:for-each select="key('responseElements', $operationName)">
                <wsdl:output>
                    <xsl:attribute name="name">
                        <xsl:value-of select="concat($operationName, $outputSuffix)"/>
                    </xsl:attribute>
                    <xsl:attribute name="message">
                        <xsl:value-of select="concat($prefix,':',@name,$messageSuffix)"/>
                    </xsl:attribute>
                </wsdl:output>
            </xsl:for-each>
        </wsdl:operation>
    </xsl:template>

    <xsl:template name="binding">
        <xsl:param name="portTypeName"/>
        <xsl:variable name="bindingName" select="concat($name, $bindingSuffix)"/>
        <wsdl:binding>
            <xsl:attribute name="name">
                <xsl:value-of select="$bindingName"/>
            </xsl:attribute>
            <xsl:attribute name="type">
                <xsl:value-of select="concat($prefix,':',$portTypeName)"/>
            </xsl:attribute>
            <wsdlsoap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
            <!-- Find all elements that end with $requestSuffix, and start an operation for that  -->
            <xsl:for-each select="/xsd:schema/xsd:element[ends-with(@name, $requestSuffix)]">
                <xsl:call-template name="bindingOperation">
                    <xsl:with-param name="operationName">
                        <xsl:value-of select="substring-before(@name,$requestSuffix)"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </wsdl:binding>
        <xsl:call-template name="service">
            <xsl:with-param name="bindingName" select="$bindingName"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="bindingOperation">
        <xsl:param name="operationName"/>
        <wsdl:operation>
            <xsl:attribute name="name">
                <xsl:value-of select="$operationName"/>
            </xsl:attribute>
            <wsdlsoap:operation>
                <xsl:attribute name="soapAction">
                    <xsl:value-of select="concat($soapActionPrefix,'/',$operationName)"/>
                </xsl:attribute>
            </wsdlsoap:operation>
            <xsl:for-each select="key('requestElements', $operationName)">
                <wsdl:input>
                    <xsl:attribute name="name">
                        <xsl:value-of select="concat($operationName, $inputSuffix)"/>
                    </xsl:attribute>
                    <wsdlsoap:body use="literal"/>
                </wsdl:input>
            </xsl:for-each>
            <xsl:for-each select="key('responseElements', $operationName)">
                <wsdl:output>
                    <xsl:attribute name="name">
                        <xsl:value-of select="concat($operationName, $outputSuffix)"/>
                    </xsl:attribute>
                    <wsdlsoap:body use="literal"/>
                </wsdl:output>
            </xsl:for-each>
        </wsdl:operation>
    </xsl:template>

    <xsl:template name="service">
        <xsl:param name="bindingName"/>
        <wsdl:service>
            <xsl:attribute name="name">
                <xsl:value-of select="concat($name, $serviceSuffix)"/>
            </xsl:attribute>
            <wsdl:port>
                <xsl:attribute name="name">
                    <xsl:value-of select="concat($name, $portSuffix)"/>
                </xsl:attribute>
                <xsl:attribute name="binding">
                    <xsl:value-of select="concat($prefix,':', $bindingName)"/>
                </xsl:attribute>
                <wsdlsoap:address>
                    <xsl:attribute name="location">
                        <xsl:value-of select="$location"/>
                    </xsl:attribute>
                </wsdlsoap:address>
            </wsdl:port>
        </wsdl:service>
    </xsl:template>

</xsl:stylesheet>