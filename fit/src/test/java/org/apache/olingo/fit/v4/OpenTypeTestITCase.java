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
package org.apache.olingo.fit.v4;

import org.apache.olingo.client.api.communication.request.cud.ODataEntityCreateRequest;
import org.apache.olingo.client.api.communication.response.ODataDeleteResponse;
import org.apache.olingo.client.api.communication.response.ODataEntityCreateResponse;
import org.apache.olingo.client.api.uri.v4.URIBuilder;
import org.apache.olingo.commons.api.domain.ODataComplexValue;
import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.apache.olingo.commons.api.domain.v4.ODataProperty;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.EdmSchema;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OpenTypeTestITCase extends AbstractTestITCase {

  @Test
  public void checkOpenTypeEntityTypesExist() {
    final Edm metadata = getClient().getRetrieveRequestFactory().
        getMetadataRequest(testOpenTypeServiceRootURL).execute().getBody();

    final EdmSchema schema = metadata.getSchemas().get(0);

    assertTrue(metadata.getEntityType(new FullQualifiedName(schema.getNamespace(), "Row")).isOpenType());
    assertTrue(metadata.getEntityType(new FullQualifiedName(schema.getNamespace(), "IndexedRow")).isOpenType());
    assertTrue(metadata.getEntityType(new FullQualifiedName(schema.getNamespace(), "RowIndex")).isOpenType());
  }

  private ODataEntity readRow(final ODataFormat format, final String uuid) {
    final URIBuilder builder = getClient().newURIBuilder(testOpenTypeServiceRootURL).
        appendEntitySetSegment("Row").appendKeySegment(UUID.fromString(uuid));
    return read(format, builder.build());
  }

  private void read(final ODataFormat format) {
    ODataEntity row = readRow(format, "71f7d0dc-ede4-45eb-b421-555a2aa1e58f");
    assertEquals(EdmPrimitiveTypeKind.Double, row.getProperty("Double").getPrimitiveValue().getTypeKind());
    assertEquals(EdmPrimitiveTypeKind.Guid, row.getProperty("Id").getPrimitiveValue().getTypeKind());

    row = readRow(format, "672b8250-1e6e-4785-80cf-b94b572e42b3");
    assertEquals(EdmPrimitiveTypeKind.Decimal, row.getProperty("Decimal").getPrimitiveValue().getTypeKind());
  }

  @Test
  public void readAsAtom() {
    read(ODataFormat.ATOM);
  }

  @Test
  public void readAsJSON() {
    read(ODataFormat.JSON_FULL_METADATA);
  }

  private void cud(final ODataFormat format) {
    final Integer id = 1426;

    ODataEntity rowIndex = getClient().getObjectFactory().newEntity(
        new FullQualifiedName("Microsoft.Test.OData.Services.OpenTypesServiceV4.RowIndex"));
    getClient().getBinder().add(rowIndex,
        getClient().getObjectFactory().newPrimitiveProperty("Id",
            getClient().getObjectFactory().newPrimitiveValueBuilder().buildInt32(id)));
    getClient().getBinder().add(rowIndex,
        getClient().getObjectFactory().newPrimitiveProperty("aString",
            getClient().getObjectFactory().newPrimitiveValueBuilder().buildString("string")));
    getClient().getBinder().add(rowIndex,
        getClient().getObjectFactory().newPrimitiveProperty("aBoolean",
            getClient().getObjectFactory().newPrimitiveValueBuilder().buildBoolean(true)));
    getClient().getBinder().add(rowIndex,
        getClient().getObjectFactory().newPrimitiveProperty("aDouble",
            getClient().getObjectFactory().newPrimitiveValueBuilder().buildDouble(1.5D)));
    getClient().getBinder().add(rowIndex,
        getClient().getObjectFactory().newPrimitiveProperty("aByte",
            getClient().getObjectFactory().newPrimitiveValueBuilder().
                setType(EdmPrimitiveTypeKind.SByte).setValue(Byte.MAX_VALUE).
                build()));
    getClient().getBinder().add(rowIndex,
        getClient().getObjectFactory().newPrimitiveProperty("aDate",
            getClient().getObjectFactory().newPrimitiveValueBuilder().
                setType(EdmPrimitiveTypeKind.DateTimeOffset).setValue(Calendar.getInstance()).
                build()));
    getClient().getBinder().add(rowIndex,
        getClient().getObjectFactory().newPrimitiveProperty("aDate",
            getClient().getObjectFactory().newPrimitiveValueBuilder().
                setType(EdmPrimitiveTypeKind.DateTimeOffset).setValue(Calendar.getInstance()).
                build()));
    getClient().getBinder().add(rowIndex,
        getClient().getObjectFactory().newEnumProperty("aColor", getClient().getObjectFactory().
            newEnumValue("Microsoft.Test.OData.Services.ODataWCFService.Color", "Blue")));

    final ODataComplexValue<ODataProperty> contactDetails = getClient().getObjectFactory().newComplexValue(
        "Microsoft.Test.OData.Services.OpenTypesServiceV4.ContactDetails");
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("FirstContacted",
        getClient().getObjectFactory().newPrimitiveValueBuilder().buildBinary("text".getBytes())));
    Calendar dateTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+00:01"));
    dateTime.set(2014, 3, 5, 5, 5, 5);
    dateTime.set(Calendar.MILLISECOND, 1);
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("LastContacted",
        getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.DateTimeOffset).setValue(dateTime).build()));
    Calendar date = Calendar.getInstance();
    date.set(2001, 3, 5);
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("Contacted",
        getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.Date).setValue(date).build()));
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("GUID",
        getClient().getObjectFactory().newPrimitiveValueBuilder().buildGuid(UUID.randomUUID())));
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("PreferedContactTime",
        getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.Duration).setValue(0).build()));
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("Byte",
        getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.Byte).setValue(24).build()));
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("SignedByte",
        getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.SByte).setValue(Byte.MAX_VALUE).build()));
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("Double",
        getClient().getObjectFactory().newPrimitiveValueBuilder().buildDouble(Double.MAX_VALUE)));
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("Single",
        getClient().getObjectFactory().newPrimitiveValueBuilder().buildSingle(Float.MAX_VALUE)));
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("Short",
        getClient().getObjectFactory().newPrimitiveValueBuilder().buildInt16(Short.MAX_VALUE)));
    contactDetails.add(getClient().getObjectFactory().newPrimitiveProperty("Int",
        getClient().getObjectFactory().newPrimitiveValueBuilder().buildInt32(Integer.MAX_VALUE)));
    getClient().getBinder().add(rowIndex,
        getClient().getObjectFactory().newComplexProperty("aContact", contactDetails));

    final ODataEntityCreateRequest<ODataEntity> createReq = getClient().getCUDRequestFactory().
        getEntityCreateRequest(getClient().newURIBuilder(testOpenTypeServiceRootURL).
            appendEntitySetSegment("RowIndex").build(), rowIndex);
    createReq.setFormat(format);
    final ODataEntityCreateResponse<ODataEntity> createRes = createReq.execute();
    assertEquals(201, createRes.getStatusCode());

    final URIBuilder builder = getClient().newURIBuilder(testOpenTypeServiceRootURL).
        appendEntitySetSegment("RowIndex").appendKeySegment(id);
    rowIndex = read(format, builder.build());
    assertNotNull(rowIndex);
    assertEquals(EdmPrimitiveTypeKind.Int32, rowIndex.getProperty("Id").getPrimitiveValue().getTypeKind());
    assertEquals(EdmPrimitiveTypeKind.String, rowIndex.getProperty("aString").getPrimitiveValue().getTypeKind());
    assertEquals(EdmPrimitiveTypeKind.Boolean, rowIndex.getProperty("aBoolean").getPrimitiveValue().getTypeKind());
    assertTrue(rowIndex.getProperty("aDouble").hasPrimitiveValue());
    assertTrue(rowIndex.getProperty("aByte").hasPrimitiveValue());
    assertTrue(rowIndex.getProperty("aDate").hasPrimitiveValue());
    assertNotNull(rowIndex.getProperty("aColor"));
    assertTrue(rowIndex.getProperty("aContact").hasComplexValue());
    assertTrue(rowIndex.getProperty("aContact").getComplexValue().get("SignedByte").hasPrimitiveValue());

    final ODataDeleteResponse deleteRes = getClient().getCUDRequestFactory().
        getDeleteRequest(rowIndex.getEditLink()).execute();
    assertEquals(204, deleteRes.getStatusCode());
  }

  @Test
  public void cudAsAtom() {
    cud(ODataFormat.ATOM);
  }

  @Test
  public void cudAsJSON() {
    cud(ODataFormat.JSON_FULL_METADATA);
  }

}
