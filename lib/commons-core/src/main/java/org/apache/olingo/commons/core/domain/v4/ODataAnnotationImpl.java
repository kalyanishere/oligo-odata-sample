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
package org.apache.olingo.commons.core.domain.v4;

import org.apache.olingo.commons.api.domain.ODataCollectionValue;
import org.apache.olingo.commons.api.domain.ODataComplexValue;
import org.apache.olingo.commons.api.domain.ODataPrimitiveValue;
import org.apache.olingo.commons.api.domain.v4.ODataAnnotation;
import org.apache.olingo.commons.api.domain.v4.ODataEnumValue;
import org.apache.olingo.commons.api.domain.v4.ODataLinkedComplexValue;
import org.apache.olingo.commons.api.domain.v4.ODataProperty;
import org.apache.olingo.commons.api.domain.v4.ODataValuable;
import org.apache.olingo.commons.api.domain.v4.ODataValue;

public class ODataAnnotationImpl implements ODataAnnotation {

  private final String term;

  private final ODataValuable valuable;

  public ODataAnnotationImpl(final String term, final ODataValue value) {
    this.term = term;
    this.valuable = new ODataValuableImpl(value);
  }

  @Override
  public String getTerm() {
    return term;
  }

  @Override
  public ODataValue getValue() {
    return valuable.getValue();
  }

  @Override
  public boolean hasNullValue() {
    return valuable.hasNullValue();
  }

  @Override
  public boolean hasPrimitiveValue() {
    return valuable.hasPrimitiveValue();
  }

  @Override
  public ODataPrimitiveValue getPrimitiveValue() {
    return valuable.getPrimitiveValue();
  }

  @Override
  public boolean hasCollectionValue() {
    return valuable.hasCollectionValue();
  }

  @Override
  public ODataCollectionValue<ODataValue> getCollectionValue() {
    return valuable.getCollectionValue();
  }

  @Override
  public boolean hasComplexValue() {
    return valuable.hasComplexValue();
  }

  @Override
  public ODataComplexValue<ODataProperty> getComplexValue() {
    return valuable.getComplexValue();
  }

  @Override
  public ODataLinkedComplexValue getLinkedComplexValue() {
    return valuable.getLinkedComplexValue();
  }

  @Override
  public boolean hasEnumValue() {
    return valuable.hasEnumValue();
  }

  @Override
  public ODataEnumValue getEnumValue() {
    return valuable.getEnumValue();
  }

}
