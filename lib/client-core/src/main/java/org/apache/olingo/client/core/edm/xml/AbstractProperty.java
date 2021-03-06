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
package org.apache.olingo.client.core.edm.xml;

import org.apache.olingo.client.api.edm.xml.CommonProperty;
import org.apache.olingo.commons.api.edm.geo.SRID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = PropertyDeserializer.class)
public abstract class AbstractProperty extends AbstractEdmItem implements CommonProperty {

  private static final long serialVersionUID = -1489578611146966897L;

  private String name;

  private String type;

  private boolean nullable = true;

  private String defaultValue;

  private Integer maxLength;

  private Integer precision;

  private Integer scale;

  private boolean unicode = true;

  private SRID srid;

  @Override
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  @Override
  public boolean isNullable() {
    return nullable;
  }

  public void setNullable(final boolean nullable) {
    this.nullable = nullable;
  }

  @Override
  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(final String defaultValue) {
    this.defaultValue = defaultValue;
  }

  @Override
  public Integer getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(final Integer maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public Integer getPrecision() {
    return precision;
  }

  public void setPrecision(final Integer precision) {
    this.precision = precision;
  }

  @Override
  public Integer getScale() {
    return scale;
  }

  public void setScale(final Integer scale) {
    this.scale = scale;
  }

  @Override
  public boolean isUnicode() {
    return unicode;
  }

  public void setUnicode(final boolean unicode) {
    this.unicode = unicode;
  }

  @Override
  public SRID getSrid() {
    return srid;
  }

  public void setSrid(final SRID srid) {
    this.srid = srid;
  }
}
