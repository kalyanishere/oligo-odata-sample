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

import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.uri.URIFilter;
import org.apache.olingo.client.api.uri.v4.FilterArgFactory;
import org.apache.olingo.client.api.uri.v4.FilterFactory;
import org.apache.olingo.client.api.uri.v4.URIBuilder;
import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.apache.olingo.commons.api.domain.v4.ODataEntitySet;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilterFactoryTestITCase extends AbstractTestITCase {

  private FilterFactory getFilterFactory() {
    return getClient().getFilterFactory();
  }

  private FilterArgFactory getFilterArgFactory() {
    return getFilterFactory().getArgFactory();
  }

  @Test
  public void crossjoin() {
    final URIFilter filter = getFilterFactory().eq(
        getFilterArgFactory().property("Orders/OrderID"), getFilterArgFactory().property("Customers/Order"));

    final URIBuilder uriBuilder =
        client.newURIBuilder(testStaticServiceRootURL).appendCrossjoinSegment("Customers", "Orders").filter(filter);

    final ODataEntitySetRequest<ODataEntitySet> req =
        client.getRetrieveRequestFactory().getEntitySetRequest(uriBuilder.build());
    req.setFormat(ODataFormat.JSON_FULL_METADATA);

    final ODataEntitySet feed = req.execute().getBody();
    assertEquals(3, feed.getEntities().size());

    for (ODataEntity entity : feed.getEntities()) {
      assertEquals(2, entity.getNavigationLinks().size());
    }
  }
}
