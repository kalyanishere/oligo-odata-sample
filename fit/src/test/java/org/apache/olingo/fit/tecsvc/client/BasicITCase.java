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
package org.apache.olingo.fit.tecsvc.client;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.olingo.client.api.communication.ODataClientErrorException;
import org.apache.olingo.client.api.communication.ODataServerErrorException;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityCreateRequest;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityUpdateRequest;
import org.apache.olingo.client.api.communication.request.cud.v4.UpdateType;
import org.apache.olingo.client.api.communication.request.retrieve.EdmMetadataRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntityRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataServiceDocumentRequest;
import org.apache.olingo.client.api.communication.request.retrieve.XMLMetadataRequest;
import org.apache.olingo.client.api.communication.response.ODataEntityCreateResponse;
import org.apache.olingo.client.api.communication.response.ODataEntityUpdateResponse;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.edm.xml.XMLMetadata;
import org.apache.olingo.client.api.edm.xml.v4.Reference;
import org.apache.olingo.client.api.v4.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.domain.ODataError;
import org.apache.olingo.commons.api.domain.ODataServiceDocument;
import org.apache.olingo.commons.api.domain.v4.ODataAnnotation;
import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.apache.olingo.commons.api.domain.v4.ODataEntitySet;
import org.apache.olingo.commons.api.domain.v4.ODataLinkedComplexValue;
import org.apache.olingo.commons.api.domain.v4.ODataObjectFactory;
import org.apache.olingo.commons.api.domain.v4.ODataProperty;
import org.apache.olingo.commons.api.domain.v4.ODataValue;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.fit.AbstractBaseTestITCase;
import org.apache.olingo.fit.tecsvc.TecSvcConst;
import org.junit.Ignore;
import org.junit.Test;

public class BasicITCase extends AbstractBaseTestITCase {

  private static final String SERVICE_URI = TecSvcConst.BASE_URI;

  @Test
  public void readServiceDocument() {
    ODataServiceDocumentRequest request = getClient().getRetrieveRequestFactory()
        .getServiceDocumentRequest(SERVICE_URI);
    assertNotNull(request);

    ODataRetrieveResponse<ODataServiceDocument> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());

    ODataServiceDocument serviceDocument = response.getBody();
    assertNotNull(serviceDocument);

    assertThat(serviceDocument.getEntitySetNames(), hasItem("ESAllPrim"));
    assertThat(serviceDocument.getFunctionImportNames(), hasItem("FICRTCollCTTwoPrim"));
    assertThat(serviceDocument.getSingletonNames(), hasItem("SIMedia"));
  }

  @Test
  public void readMetadata() {
    EdmMetadataRequest request = getClient().getRetrieveRequestFactory().getMetadataRequest(SERVICE_URI);
    assertNotNull(request);

    ODataRetrieveResponse<Edm> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());

    Edm edm = response.getBody();

    assertNotNull(edm);
    assertEquals(2, edm.getSchemas().size());
    assertEquals("olingo.odata.test1", edm.getSchema("olingo.odata.test1").getNamespace());
    assertEquals("Namespace1_Alias", edm.getSchema("olingo.odata.test1").getAlias());
    assertEquals("Org.OData.Core.V1", edm.getSchema("Org.OData.Core.V1").getNamespace());
    assertEquals("Core", edm.getSchema("Org.OData.Core.V1").getAlias());
  }

  @Test
  public void readViaXmlMetadata() {
    XMLMetadataRequest request = getClient().getRetrieveRequestFactory().getXMLMetadataRequest(SERVICE_URI);
    assertNotNull(request);

    ODataRetrieveResponse<XMLMetadata> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());

    XMLMetadata xmlMetadata = response.getBody();

    assertNotNull(xmlMetadata);
    assertTrue(xmlMetadata instanceof org.apache.olingo.client.api.edm.xml.v4.XMLMetadata);
    assertEquals(2, xmlMetadata.getSchemas().size());
    assertEquals("olingo.odata.test1", xmlMetadata.getSchema("olingo.odata.test1").getNamespace());
    final List<Reference> references =
        ((org.apache.olingo.client.api.edm.xml.v4.XMLMetadata) xmlMetadata).getReferences();
    assertEquals(1, references.size());
    assertThat(references.get(0).getUri().toASCIIString(), containsString("vocabularies/Org.OData.Core.V1"));
  }

  @Test
  public void readEntitySet() {
    final ODataEntitySetRequest<ODataEntitySet> request = getClient().getRetrieveRequestFactory()
        .getEntitySetRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendEntitySetSegment("ESMixPrimCollComp").build());
    assertNotNull(request);

    final ODataRetrieveResponse<ODataEntitySet> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    assertThat(response.getContentType(), containsString(ContentType.APPLICATION_JSON.toContentTypeString()));

    final ODataEntitySet entitySet = response.getBody();
    assertNotNull(entitySet);

    assertNull(entitySet.getCount());
    assertNull(entitySet.getNext());
    assertEquals(Collections.<ODataAnnotation> emptyList(), entitySet.getAnnotations());
    assertNull(entitySet.getDeltaLink());

    final List<ODataEntity> entities = entitySet.getEntities();
    assertNotNull(entities);
    assertEquals(3, entities.size());
    final ODataEntity entity = entities.get(2);
    assertNotNull(entity);
    final ODataProperty property = entity.getProperty("PropertyInt16");
    assertNotNull(property);
    assertNotNull(property.getPrimitiveValue());
    assertEquals(0, property.getPrimitiveValue().toValue());
  }

  @Test
  public void readException() throws Exception {
    final ODataEntityRequest<ODataEntity> request = getClient().getRetrieveRequestFactory()
        .getEntityRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendEntitySetSegment("ESMixPrimCollComp").appendKeySegment("42").build());
    assertNotNull(request);

    try {
      request.execute();
      fail("Expected Exception not thrown!");
    } catch (final ODataClientErrorException e) {
      assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), e.getStatusLine().getStatusCode());
      final ODataError error = e.getODataError();
      assertThat(error.getMessage(), containsString("key property"));
    }
  }

  @Test
  public void readEntity() throws Exception {
    final ODataEntityRequest<ODataEntity> request = getClient().getRetrieveRequestFactory()
        .getEntityRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendEntitySetSegment("ESCollAllPrim").appendKeySegment(1).build());
    assertNotNull(request);

    final ODataRetrieveResponse<ODataEntity> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    assertThat(response.getContentType(), containsString(ContentType.APPLICATION_JSON.toContentTypeString()));

    final ODataEntity entity = response.getBody();
    assertNotNull(entity);
    final ODataProperty property = entity.getProperty("CollPropertyInt16");
    assertNotNull(property);
    assertNotNull(property.getCollectionValue());
    assertEquals(3, property.getCollectionValue().size());
    Iterator<ODataValue> iterator = property.getCollectionValue().iterator();
    assertEquals(1000, iterator.next().asPrimitive().toValue());
    assertEquals(2000, iterator.next().asPrimitive().toValue());
    assertEquals(30112, iterator.next().asPrimitive().toValue());
  }

  @Test
  @Ignore
  public void patchEntity() throws Exception {
    final ODataClient client = getClient();
    final ODataObjectFactory factory = client.getObjectFactory();
    ODataEntity patchEntity = factory.newEntity(new FullQualifiedName("olingo.odata.test1", "ETAllPrim"));
    patchEntity.getProperties().add(factory.newPrimitiveProperty("PropertyString",
        factory.newPrimitiveValueBuilder().buildString("new")));
    patchEntity.getProperties().add(factory.newPrimitiveProperty("PropertyDecimal",
        factory.newPrimitiveValueBuilder().buildDouble(42.875)));
    patchEntity.getProperties().add(factory.newPrimitiveProperty("PropertyInt64",
        factory.newPrimitiveValueBuilder().buildInt64(null)));
    final URI uri = client.newURIBuilder(SERVICE_URI).appendEntitySetSegment("ESAllPrim").appendKeySegment(32767)
        .build();
    final ODataEntityUpdateRequest<ODataEntity> request = client.getCUDRequestFactory().getEntityUpdateRequest(
        uri, UpdateType.PATCH, patchEntity);
    final ODataEntityUpdateResponse<ODataEntity> response = request.execute();
    assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), response.getStatusCode());

    // Check that the patched properties have changed and the other properties not.
    // This check has to be in the same session in order to access the same data provider.
    ODataEntityRequest<ODataEntity> entityRequest = client.getRetrieveRequestFactory().getEntityRequest(uri);
    entityRequest.addCustomHeader(HttpHeader.COOKIE, response.getHeader(HttpHeader.SET_COOKIE).iterator().next());
    final ODataRetrieveResponse<ODataEntity> entityResponse = entityRequest.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), entityResponse.getStatusCode());
    final ODataEntity entity = entityResponse.getBody();
    assertNotNull(entity);
    final ODataProperty property1 = entity.getProperty("PropertyString");
    assertNotNull(property1);
    assertEquals("new", property1.getPrimitiveValue().toValue());
    final ODataProperty property2 = entity.getProperty("PropertyDecimal");
    assertNotNull(property2);
    assertEquals(42.875, property2.getPrimitiveValue().toValue());
    final ODataProperty property3 = entity.getProperty("PropertyInt64");
    assertNotNull(property3);
    assertNull(property3.getPrimitiveValue());
    final ODataProperty property4 = entity.getProperty("PropertyDuration");
    assertNotNull(property4);
    assertEquals("PT6S", property4.getPrimitiveValue().toValue());
  }

  @Test
  @Ignore
  public void updateEntity() throws Exception {
    final ODataClient client = getClient();
    final ODataObjectFactory factory = client.getObjectFactory();
    ODataEntity newEntity = factory.newEntity(new FullQualifiedName("olingo.odata.test1", "ETAllPrim"));
    newEntity.getProperties().add(factory.newPrimitiveProperty("PropertyInt64",
        factory.newPrimitiveValueBuilder().buildInt32(42)));
    final URI uri = client.newURIBuilder(SERVICE_URI).appendEntitySetSegment("ESAllPrim").appendKeySegment(32767)
        .build();
    final ODataEntityUpdateRequest<ODataEntity> request = client.getCUDRequestFactory().getEntityUpdateRequest(
        uri, UpdateType.REPLACE, newEntity);
    final ODataEntityUpdateResponse<ODataEntity> response = request.execute();
    assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), response.getStatusCode());

    // Check that the updated properties have changed and that other properties have their default values.
    // This check has to be in the same session in order to access the same data provider.
    ODataEntityRequest<ODataEntity> entityRequest = client.getRetrieveRequestFactory().getEntityRequest(uri);
    entityRequest.addCustomHeader(HttpHeader.COOKIE, response.getHeader(HttpHeader.SET_COOKIE).iterator().next());
    final ODataRetrieveResponse<ODataEntity> entityResponse = entityRequest.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), entityResponse.getStatusCode());
    final ODataEntity entity = entityResponse.getBody();
    assertNotNull(entity);
    final ODataProperty property1 = entity.getProperty("PropertyInt64");
    assertNotNull(property1);
    assertEquals(42, property1.getPrimitiveValue().toValue());
    final ODataProperty property2 = entity.getProperty("PropertyDecimal");
    assertNotNull(property2);
    assertNull(property2.getPrimitiveValue());
  }

  @Test
  @Ignore
  public void patchEntityWithComplex() throws Exception {
    final ODataClient client = getClient();
    final ODataObjectFactory factory = client.getObjectFactory();
    ODataEntity patchEntity = factory.newEntity(new FullQualifiedName("olingo.odata.test1", "ETCompComp"));
    patchEntity.getProperties().add(factory.newComplexProperty("PropertyComp",
        factory.newLinkedComplexValue("olingo.odata.test1.CTCompComp").add(
            factory.newComplexProperty("PropertyComp",
                factory.newLinkedComplexValue("olingo.odata.test1.CTTwoPrim").add(
                    factory.newPrimitiveProperty("PropertyInt16",
                        factory.newPrimitiveValueBuilder().buildInt32(42)))))));
    final URI uri = client.newURIBuilder(SERVICE_URI).appendEntitySetSegment("ESCompComp").appendKeySegment(1).build();
    final ODataEntityUpdateRequest<ODataEntity> request = client.getCUDRequestFactory().getEntityUpdateRequest(
        uri, UpdateType.PATCH, patchEntity);
    final ODataEntityUpdateResponse<ODataEntity> response = request.execute();
    assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), response.getStatusCode());

    // Check that the patched properties have changed and the other properties not.
    // This check has to be in the same session in order to access the same data provider.
    ODataEntityRequest<ODataEntity> entityRequest = client.getRetrieveRequestFactory().getEntityRequest(uri);
    entityRequest.addCustomHeader(HttpHeader.COOKIE, response.getHeader(HttpHeader.SET_COOKIE).iterator().next());
    final ODataRetrieveResponse<ODataEntity> entityResponse = entityRequest.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), entityResponse.getStatusCode());
    final ODataEntity entity = entityResponse.getBody();
    assertNotNull(entity);
    final ODataLinkedComplexValue complex = entity.getProperty("PropertyComp").getLinkedComplexValue()
        .get("PropertyComp").getLinkedComplexValue();
    assertNotNull(complex);
    final ODataProperty property1 = complex.get("PropertyInt16");
    assertNotNull(property1);
    assertEquals(42, property1.getPrimitiveValue().toValue());
    final ODataProperty property2 = complex.get("PropertyString");
    assertNotNull(property2);
    assertEquals("String 1", property2.getPrimitiveValue().toValue());
  }

  @Test
  @Ignore("Actual leads to an unexpected exception")
  public void updateEntityWithComplex() throws Exception {
    final ODataClient client = getClient();
    final ODataObjectFactory factory = client.getObjectFactory();
    ODataEntity newEntity = factory.newEntity(new FullQualifiedName("olingo.odata.test1", "ETCompComp"));
    newEntity.getProperties().add(factory.newComplexProperty("PropertyComp", null));
    final URI uri = client.newURIBuilder(SERVICE_URI).appendEntitySetSegment("ESCompComp").appendKeySegment(1).build();
    final ODataEntityUpdateRequest<ODataEntity> request = client.getCUDRequestFactory().getEntityUpdateRequest(
        uri, UpdateType.REPLACE, newEntity);
    final ODataEntityUpdateResponse<ODataEntity> response = request.execute();
    assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), response.getStatusCode());

    // Check that the complex-property hierarchy is still there and that all primitive values are now null.
    // This check has to be in the same session in order to access the same data provider.
    ODataEntityRequest<ODataEntity> entityRequest = client.getRetrieveRequestFactory().getEntityRequest(uri);
    entityRequest.addCustomHeader(HttpHeader.COOKIE, response.getHeader(HttpHeader.SET_COOKIE).iterator().next());
    final ODataRetrieveResponse<ODataEntity> entityResponse = entityRequest.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), entityResponse.getStatusCode());
    final ODataEntity entity = entityResponse.getBody();
    assertNotNull(entity);
    final ODataLinkedComplexValue complex = entity.getProperty("PropertyComp").getLinkedComplexValue()
        .get("PropertyComp").getLinkedComplexValue();
    assertNotNull(complex);
    final ODataProperty property = complex.get("PropertyInt16");
    assertNotNull(property);
    assertNull(property.getPrimitiveValue());
  }

  /**
   * Currently a create request for an entity will lead to a "501 - Not Implemented" response
   * and hence to an ODataServerErrorException.
   */
  @Test(expected = ODataServerErrorException.class)
  public void createEntity() throws IOException {
    final ODataEntityRequest<ODataEntity> request = getClient().getRetrieveRequestFactory()
        .getEntityRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendEntitySetSegment("ESCollAllPrim").appendKeySegment(1).build());
    assertNotNull(request);

    final ODataRetrieveResponse<ODataEntity> response = request.execute();
    assertEquals(HttpStatusCode.OK.getStatusCode(), response.getStatusCode());
    assertThat(response.getContentType(), containsString(ContentType.APPLICATION_JSON.toContentTypeString()));

    final ODataEntity entity = response.getBody();
    assertNotNull(entity);

    final ODataEntityCreateRequest<ODataEntity> createRequest = getClient().getCUDRequestFactory()
        .getEntityCreateRequest(getClient().newURIBuilder(SERVICE_URI)
            .appendEntitySetSegment("ESCollAllPrim").build(), entity);
    assertNotNull(createRequest);
    ODataEntityCreateResponse<ODataEntity> createResponse = createRequest.execute();

    final ODataEntity createdEntity = createResponse.getBody();
    assertNotNull(createdEntity);
    final ODataProperty property = createdEntity.getProperty("CollPropertyInt16");
    assertNotNull(property);
    assertNotNull(property.getCollectionValue());
    assertEquals(3, property.getCollectionValue().size());
    Iterator<ODataValue> iterator = property.getCollectionValue().iterator();
    assertEquals(1000, iterator.next().asPrimitive().toValue());
    assertEquals(2000, iterator.next().asPrimitive().toValue());
    assertEquals(30112, iterator.next().asPrimitive().toValue());
  }

  @Override
  protected ODataClient getClient() {
    ODataClient odata = ODataClientFactory.getV4();
    odata.getConfiguration().setDefaultPubFormat(ODataFormat.JSON);
    return odata;
  }
}
