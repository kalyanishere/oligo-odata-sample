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
package org.apache.olingo.client.core.edm.xml.v3;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.client.api.edm.xml.v3.AssociationSet;
import org.apache.olingo.client.api.edm.xml.v3.AssociationSetEnd;
import org.apache.olingo.client.core.edm.xml.AbstractEdmItem;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = AssociationSetDeserializer.class)
public class AssociationSetImpl extends AbstractEdmItem implements AssociationSet {

  private static final long serialVersionUID = 5618645304372365820L;

  private String name;

  private String association;

  private List<AssociationSetEnd> ends = new ArrayList<AssociationSetEnd>();

  @Override
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String getAssociation() {
    return association;
  }

  public void setAssociation(final String association) {
    this.association = association;
  }

  @Override
  public List<AssociationSetEnd> getEnds() {
    return ends;
  }
}
