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

import java.util.Collections;
import java.util.List;

import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmAnnotation;
import org.apache.olingo.commons.api.edm.EdmEntityContainer;
import org.apache.olingo.commons.api.edm.EdmTerm;
import org.apache.olingo.commons.api.edm.Target;
import org.apache.olingo.commons.core.edm.AbstractEdmOperationImport;

public abstract class EdmOperationImportImpl extends AbstractEdmOperationImport {

  protected EdmOperationImportImpl(final Edm edm, final EdmEntityContainer container, final String name,
          final String entitySet) {

    super(edm, container, name, entitySet == null ? null : new Target.Builder(entitySet, container).build());
  }

  @Override
  public EdmAnnotation getAnnotation(final EdmTerm term) {
    return null;
  }

  @Override
  public List<EdmAnnotation> getAnnotations() {
    return Collections.<EdmAnnotation>emptyList();
  }
}
