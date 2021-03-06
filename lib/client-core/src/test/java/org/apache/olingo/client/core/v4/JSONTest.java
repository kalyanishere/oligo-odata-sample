/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.core.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.olingo.client.api.v4.ODataClient;
import org.apache.olingo.client.core.AbstractTest;
import org.apache.olingo.commons.api.Constants;
import org.apache.olingo.commons.api.data.Delta;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.domain.ODataCollectionValue;
import org.apache.olingo.commons.api.domain.ODataComplexValue;
import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.apache.olingo.commons.api.domain.v4.ODataProperty;
import org.apache.olingo.commons.api.domain.v4.ODataValue;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.constants.ODataServiceVersion;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.junit.Test;

public class JSONTest extends AbstractTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  protected ODataClient getClient() {
    return v4Client;
  }

  protected ODataFormat getODataPubFormat() {
    return ODataFormat.JSON;
  }

  protected ODataFormat getODataFormat() {
    return ODataFormat.JSON;
  }

  private void cleanup(final ObjectNode node) {
    final ODataServiceVersion version = getClient().getServiceVersion();
    if (node.has(Constants.JSON_CONTEXT)) {
      node.remove(Constants.JSON_CONTEXT);
    }
    if (node.has(version.getJsonName(ODataServiceVersion.JsonKey.ETAG))) {
      node.remove(version.getJsonName(ODataServiceVersion.JsonKey.ETAG));
    }
    if (node.has(version.getJsonName(ODataServiceVersion.JsonKey.TYPE))) {
      node.remove(version.getJsonName(ODataServiceVersion.JsonKey.TYPE));
    }
    if (node.has(version.getJsonName(ODataServiceVersion.JsonKey.EDIT_LINK))) {
      node.remove(version.getJsonName(ODataServiceVersion.JsonKey.EDIT_LINK));
    }
    if (node.has(version.getJsonName(ODataServiceVersion.JsonKey.READ_LINK))) {
      node.remove(version.getJsonName(ODataServiceVersion.JsonKey.READ_LINK));
    }
    if (node.has(version.getJsonName(ODataServiceVersion.JsonKey.MEDIA_EDIT_LINK))) {
      node.remove(version.getJsonName(ODataServiceVersion.JsonKey.MEDIA_EDIT_LINK));
    }
    if (node.has(version.getJsonName(ODataServiceVersion.JsonKey.MEDIA_READ_LINK))) {
      node.remove(version.getJsonName(ODataServiceVersion.JsonKey.MEDIA_READ_LINK));
    }
    if (node.has(version.getJsonName(ODataServiceVersion.JsonKey.MEDIA_CONTENT_TYPE))) {
      node.remove(version.getJsonName(ODataServiceVersion.JsonKey.MEDIA_CONTENT_TYPE));
    }
    if (node.has(version.getJsonName(ODataServiceVersion.JsonKey.COUNT))) {
      node.remove(version.getJsonName(ODataServiceVersion.JsonKey.COUNT));
    }
    final List<String> toRemove = new ArrayList<String>();
    for (final Iterator<Map.Entry<String, JsonNode>> itor = node.fields(); itor.hasNext();) {
      final Map.Entry<String, JsonNode> field = itor.next();

      final String key = field.getKey();
      if (key.charAt(0) == '#'
              || key.endsWith(version.getJsonName(ODataServiceVersion.JsonKey.TYPE))
              || key.endsWith(version.getJsonName(ODataServiceVersion.JsonKey.MEDIA_EDIT_LINK))
              || key.endsWith(version.getJsonName(ODataServiceVersion.JsonKey.MEDIA_CONTENT_TYPE))
              || key.endsWith(version.getJsonName(ODataServiceVersion.JsonKey.ASSOCIATION_LINK))
              || key.endsWith(version.getJsonName(ODataServiceVersion.JsonKey.MEDIA_ETAG))) {

        toRemove.add(key);
      } else if (field.getValue().isObject()) {
        cleanup((ObjectNode) field.getValue());
      } else if (field.getValue().isArray()) {
        for (final Iterator<JsonNode> arrayItems = field.getValue().elements(); arrayItems.hasNext();) {
          final JsonNode arrayItem = arrayItems.next();
          if (arrayItem.isObject()) {
            cleanup((ObjectNode) arrayItem);
          }
        }
      }
    }
    node.remove(toRemove);
  }

  protected void assertSimilar(final String filename, final String actual) throws Exception {
    final JsonNode expected = OBJECT_MAPPER.readTree(IOUtils.toString(getClass().getResourceAsStream(filename)).
            replace(getClient().getServiceVersion().getJsonName(ODataServiceVersion.JsonKey.NAVIGATION_LINK),
                    Constants.JSON_BIND_LINK_SUFFIX));
    cleanup((ObjectNode) expected);
    final ObjectNode actualNode = (ObjectNode) OBJECT_MAPPER.readTree(new ByteArrayInputStream(actual.getBytes()));
    cleanup(actualNode);
    assertEquals(expected, actualNode);
  }

  protected void entitySet(final String filename, final ODataFormat format) throws Exception {
    final StringWriter writer = new StringWriter();
    getClient().getSerializer(format).write(writer, getClient().getDeserializer(format).toEntitySet(
            getClass().getResourceAsStream(filename + "." + getSuffix(format))).getPayload());

    assertSimilar(filename + "." + getSuffix(format), writer.toString());
  }

  @Test
  public void entitySets() throws Exception {
    entitySet("Customers", getODataPubFormat());
    entitySet("collectionOfEntityReferences", getODataPubFormat());
  }

  protected void entity(final String filename, final ODataFormat format) throws Exception {
    final StringWriter writer = new StringWriter();
    getClient().getSerializer(format).write(writer, getClient().getDeserializer(format).toEntity(
            getClass().getResourceAsStream(filename + "." + getSuffix(format))).getPayload());

    assertSimilar(filename + "." + getSuffix(format), writer.toString());
  }

  @Test
  public void additionalEntities() throws Exception {
    entity("entity.minimal", getODataPubFormat());
    entity("entity.primitive", getODataPubFormat());
    entity("entity.complex", getODataPubFormat());
    entity("entity.collection.primitive", getODataPubFormat());
    entity("entity.collection.complex", getODataPubFormat());
  }

  @Test
  public void entities() throws Exception {
    entity("Products_5", getODataPubFormat());
    entity("VipCustomer", getODataPubFormat());
    entity("Advertisements_f89dee73-af9f-4cd4-b330-db93c25ff3c7", getODataPubFormat());
    entity("entityReference", getODataPubFormat());
    entity("entity.withcomplexnavigation", getODataPubFormat());
    entity("annotated", getODataPubFormat());
  }

  protected void property(final String filename, final ODataFormat format) throws Exception {
    final StringWriter writer = new StringWriter();
    getClient().getSerializer(format).write(writer, getClient().getDeserializer(format).
            toProperty(getClass().getResourceAsStream(filename + "." + getSuffix(format))).getPayload());

    assertSimilar(filename + "." + getSuffix(format), writer.toString());
  }

  @Test
  public void properties() throws Exception {
    property("Products_5_SkinColor", getODataFormat());
    property("Products_5_CoverColors", getODataFormat());
    property("Employees_3_HomeAddress", getODataFormat());
    property("Employees_3_HomeAddress", getODataFormat());
  }

  @Test
  public void crossjoin() throws Exception {
    assertNotNull(getClient().getDeserializer(ODataFormat.JSON_FULL_METADATA).toEntitySet(
            getClass().getResourceAsStream("crossjoin.json")));
  }

  protected void delta(final String filename, final ODataFormat format) throws Exception {
    final Delta delta = getClient().getDeserializer(format).toDelta(
            getClass().getResourceAsStream(filename + "." + getSuffix(format))).getPayload();
    assertNotNull(delta);
    assertNotNull(delta.getDeltaLink());
    assertEquals(5, delta.getCount(), 0);

    assertEquals(1, delta.getDeletedEntities().size());
    assertTrue(delta.getDeletedEntities().get(0).getId().toASCIIString().endsWith("Customers('ANTON')"));

    assertEquals(1, delta.getAddedLinks().size());
    assertTrue(delta.getAddedLinks().get(0).getSource().toASCIIString().endsWith("Customers('BOTTM')"));
    assertEquals("Orders", delta.getAddedLinks().get(0).getRelationship());

    assertEquals(1, delta.getDeletedLinks().size());
    assertTrue(delta.getDeletedLinks().get(0).getSource().toASCIIString().endsWith("Customers('ALFKI')"));
    assertEquals("Orders", delta.getDeletedLinks().get(0).getRelationship());

    assertEquals(2, delta.getEntities().size());
    Property property = delta.getEntities().get(0).getProperty("ContactName");
    assertNotNull(property);
    assertTrue(property.isPrimitive());
    property = delta.getEntities().get(1).getProperty("ShippingAddress");
    assertNotNull(property);
    assertTrue(property.isLinkedComplex());
  }

  @Test
  public void deltas() throws Exception {
    delta("delta", getODataPubFormat());
  }

  @Test
  public void issueOLINGO390() throws Exception {
    final ODataEntity message = getClient().getObjectFactory().
            newEntity(new FullQualifiedName("Microsoft.Exchange.Services.OData.Model.Message"));

    final ODataComplexValue<ODataProperty> toRecipient = getClient().getObjectFactory().
            newComplexValue("Microsoft.Exchange.Services.OData.Model.Recipient");
    toRecipient.add(getClient().getObjectFactory().newPrimitiveProperty("Name",
            getClient().getObjectFactory().newPrimitiveValueBuilder().buildString("challen_olingo_client")));
    toRecipient.add(getClient().getObjectFactory().newPrimitiveProperty("Address",
            getClient().getObjectFactory().newPrimitiveValueBuilder().buildString("challenh@microsoft.com")));
    final ODataCollectionValue<ODataValue> toRecipients = getClient().getObjectFactory().
            newCollectionValue("Microsoft.Exchange.Services.OData.Model.Recipient");
    toRecipients.add(toRecipient);
    message.getProperties().add(getClient().getObjectFactory().newCollectionProperty("ToRecipients", toRecipients));

    final ODataComplexValue<ODataProperty> body =
            getClient().getObjectFactory().newComplexValue("Microsoft.Exchange.Services.OData.Model.ItemBody");
    body.add(getClient().getObjectFactory().newPrimitiveProperty("Content",
            getClient().getObjectFactory().newPrimitiveValueBuilder().
            buildString("this is a simple email body content")));
    body.add(getClient().getObjectFactory().newEnumProperty("ContentType",
            getClient().getObjectFactory().newEnumValue("Microsoft.Exchange.Services.OData.Model.BodyType", "text")));
    message.getProperties().add(getClient().getObjectFactory().newComplexProperty("Body", body));

    final String actual = IOUtils.toString(getClient().getWriter().writeEntity(message, ODataFormat.JSON));
    final JsonNode expected = OBJECT_MAPPER.readTree(IOUtils.toString(getClass().getResourceAsStream("olingo390.json")).
            replace(getClient().getServiceVersion().getJsonName(ODataServiceVersion.JsonKey.NAVIGATION_LINK),
                    Constants.JSON_BIND_LINK_SUFFIX));
    final ObjectNode actualNode = (ObjectNode) OBJECT_MAPPER.readTree(new ByteArrayInputStream(actual.getBytes()));
    assertEquals(expected, actualNode);
  }
}
