/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.commons.api.domain.v3;

import org.apache.olingo.commons.api.domain.CommonODataObjectFactory;
import org.apache.olingo.commons.api.domain.CommonODataProperty;
import org.apache.olingo.commons.api.domain.ODataCollectionValue;
import org.apache.olingo.commons.api.domain.ODataComplexValue;
import org.apache.olingo.commons.api.domain.ODataLink;
import org.apache.olingo.commons.api.domain.ODataPrimitiveValue;
import org.apache.olingo.commons.api.domain.ODataValue;
import org.apache.olingo.commons.api.edm.FullQualifiedName;

import java.net.URI;

public interface ODataObjectFactory extends CommonODataObjectFactory {

  @Override
  ODataEntitySet newEntitySet();

  @Override
  ODataEntitySet newEntitySet(URI next);

  @Override
  ODataEntity newEntity(FullQualifiedName typeName);

  @Override
  ODataEntity newEntity(FullQualifiedName typeName, URI link);

  /**
   * Instantiates a new association link.
   * 
   * @param link link.
   * @return association link.
   */
  ODataLink newAssociationLink(URI link);

  @Override
  ODataComplexValue<ODataProperty> newComplexValue(String typeName);

  @Override
  ODataCollectionValue<ODataValue> newCollectionValue(String typeName);

  @Override
  ODataProperty newPrimitiveProperty(String name, ODataPrimitiveValue value);

  @Override
  ODataProperty newComplexProperty(String name, ODataComplexValue<? extends CommonODataProperty> value);

  @Override
  ODataProperty newCollectionProperty(String name, ODataCollectionValue<? extends ODataValue> value);

}
