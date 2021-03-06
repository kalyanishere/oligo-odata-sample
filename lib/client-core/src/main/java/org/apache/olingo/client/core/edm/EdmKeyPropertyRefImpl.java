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
package org.apache.olingo.client.core.edm;

import org.apache.olingo.client.api.edm.xml.PropertyRef;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.core.edm.AbstractEdmKeyPropertyRef;

public class EdmKeyPropertyRefImpl extends AbstractEdmKeyPropertyRef {

  private final PropertyRef propertyRef;

  public EdmKeyPropertyRefImpl(final EdmEntityType edmEntityType, final PropertyRef propertyRef) {
    super(edmEntityType);
    this.propertyRef = propertyRef;
  }

  @Override
  public String getKeyPropertyName() {
    return propertyRef.getName();
  }

  @Override
  public String getAlias() {
    return propertyRef.getAlias();
  }

  @Override
  public String getPath() {
    throw new UnsupportedOperationException("Not supported in client code.");
  }

}
