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
package org.apache.olingo.client.core.edm;

import java.util.Collections;
import java.util.List;

import org.apache.olingo.client.api.edm.xml.CommonFunctionImport;
import org.apache.olingo.client.api.edm.xml.EntityContainer;
import org.apache.olingo.client.api.edm.xml.EntitySet;
import org.apache.olingo.client.api.edm.xml.Schema;
import org.apache.olingo.client.api.edm.xml.v3.FunctionImport;
import org.apache.olingo.client.api.edm.xml.v4.ActionImport;
import org.apache.olingo.client.api.edm.xml.v4.Singleton;
import org.apache.olingo.client.api.v3.UnsupportedInV3Exception;
import org.apache.olingo.client.core.edm.v3.EdmActionImportProxy;
import org.apache.olingo.client.core.edm.v3.EdmEntitySetProxy;
import org.apache.olingo.client.core.edm.v3.EdmFunctionImportProxy;
import org.apache.olingo.client.core.edm.v3.FunctionImportUtils;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmActionImport;
import org.apache.olingo.commons.api.edm.EdmAnnotation;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmFunctionImport;
import org.apache.olingo.commons.api.edm.EdmSingleton;
import org.apache.olingo.commons.api.edm.EdmTerm;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.core.edm.AbstractEdmEntityContainer;
import org.apache.olingo.commons.core.edm.EdmAnnotationHelper;
import org.apache.olingo.commons.core.edm.EdmTypeInfo;

public class EdmEntityContainerImpl extends AbstractEdmEntityContainer {

  private final EntityContainer xmlEntityContainer;

  private final List<? extends Schema> xmlSchemas;

  private EdmAnnotationHelper helper;

  public EdmEntityContainerImpl(final Edm edm, final FullQualifiedName entityContainerName,
          final EntityContainer xmlEntityContainer, final List<? extends Schema> xmlSchemas) {

    super(edm, entityContainerName, xmlEntityContainer.getExtends() == null
            ? null : new FullQualifiedName(xmlEntityContainer.getExtends()));

    this.xmlEntityContainer = xmlEntityContainer;
    this.xmlSchemas = xmlSchemas;
    if (xmlEntityContainer instanceof org.apache.olingo.client.api.edm.xml.v4.EntityContainer) {
      this.helper = new EdmAnnotationHelperImpl(edm,
              (org.apache.olingo.client.api.edm.xml.v4.EntityContainer) xmlEntityContainer);
    }
  }

  @Override
  public boolean isDefault() {
    return xmlEntityContainer instanceof org.apache.olingo.client.api.edm.xml.v4.EntityContainer
            ? true
            : xmlEntityContainer.isDefaultEntityContainer();
  }

  @Override
  protected EdmSingleton createSingleton(final String singletonName) {
    if (!(xmlEntityContainer instanceof org.apache.olingo.client.api.edm.xml.v4.EntityContainer)) {
      throw new UnsupportedInV3Exception();
    }

    final Singleton singleton = ((org.apache.olingo.client.api.edm.xml.v4.EntityContainer) xmlEntityContainer).
            getSingleton(singletonName);
    return singleton == null
            ? null
            : new EdmSingletonImpl(edm, this, singletonName, new EdmTypeInfo.Builder().
                    setTypeExpression(singleton.getEntityType()).
                    setDefaultNamespace(entityContainerName.getNamespace()).
                    build().getFullQualifiedName(), singleton);
  }

  @Override
  protected EdmEntitySet createEntitySet(final String entitySetName) {
    EdmEntitySet result = null;

    final EntitySet entitySet = xmlEntityContainer.getEntitySet(entitySetName);
    if (entitySet != null) {
      final FullQualifiedName entityType = new EdmTypeInfo.Builder().setTypeExpression(entitySet.getEntityType()).
              setDefaultNamespace(entityContainerName.getNamespace()).build().getFullQualifiedName();
      if (entitySet instanceof org.apache.olingo.client.api.edm.xml.v4.EntitySet) {
        result = new EdmEntitySetImpl(edm, this, entitySetName, entityType,
                (org.apache.olingo.client.api.edm.xml.v4.EntitySet) entitySet);
      } else {
        result = new EdmEntitySetProxy(edm, this, entitySetName, entityType, xmlSchemas);
      }
    }

    return result;
  }

  @Override
  protected EdmActionImport createActionImport(final String actionImportName) {
    EdmActionImport result = null;

    if (xmlEntityContainer instanceof org.apache.olingo.client.api.edm.xml.v4.EntityContainer) {
      final ActionImport actionImport = ((org.apache.olingo.client.api.edm.xml.v4.EntityContainer) xmlEntityContainer).
              getActionImport(actionImportName);
      if (actionImport != null) {
        result = new EdmActionImportImpl(edm, this, actionImportName, actionImport);
      }
    } else {
      final FunctionImport functionImport = (FunctionImport) xmlEntityContainer.getFunctionImport(actionImportName);
      if (functionImport != null) {
        result = new EdmActionImportProxy(edm, this, actionImportName, functionImport);
      }
    }

    return result;
  }

  @Override
  protected EdmFunctionImport createFunctionImport(final String functionImportName) {
    EdmFunctionImport result = null;

    final CommonFunctionImport functionImport = xmlEntityContainer.getFunctionImport(functionImportName);
    if (functionImport != null) {
      if (functionImport instanceof org.apache.olingo.client.api.edm.xml.v4.FunctionImport) {
        result = new EdmFunctionImportImpl(edm, this, functionImportName,
                (org.apache.olingo.client.api.edm.xml.v4.FunctionImport) functionImport);
      } else {
        result = new EdmFunctionImportProxy(edm, this, functionImportName,
                (org.apache.olingo.client.api.edm.xml.v3.FunctionImport) functionImport);
      }
    }

    return result;
  }

  @Override
  protected void loadAllEntitySets() {
    List<? extends EntitySet> localEntitySets = xmlEntityContainer.getEntitySets();
    if (localEntitySets != null) {
      for (EntitySet entitySet : localEntitySets) {
        EdmEntitySet edmSet;
        final FullQualifiedName entityType = new EdmTypeInfo.Builder().setTypeExpression(entitySet.getEntityType()).
                setDefaultNamespace(entityContainerName.getNamespace()).build().getFullQualifiedName();
        if (entitySet instanceof org.apache.olingo.client.api.edm.xml.v4.EntitySet) {
          edmSet = new EdmEntitySetImpl(edm, this, entitySet.getName(), entityType,
                  (org.apache.olingo.client.api.edm.xml.v4.EntitySet) entitySet);
        } else {
          edmSet = new EdmEntitySetProxy(edm, this, entitySet.getName(), entityType, xmlSchemas);
        }
        entitySets.put(edmSet.getName(), edmSet);
      }
    }

  }

  @Override
  protected void loadAllFunctionImports() {
    final List<? extends CommonFunctionImport> localFunctionImports = xmlEntityContainer.getFunctionImports();
    for (CommonFunctionImport functionImport : localFunctionImports) {
      EdmFunctionImport edmFunctionImport;
      if (functionImport instanceof org.apache.olingo.client.api.edm.xml.v4.FunctionImport) {
        edmFunctionImport = new EdmFunctionImportImpl(edm, this, functionImport.getName(),
                (org.apache.olingo.client.api.edm.xml.v4.FunctionImport) functionImport);
        functionImports.put(edmFunctionImport.getName(), edmFunctionImport);
      } else if (FunctionImportUtils.canProxyFunction(
              (org.apache.olingo.client.api.edm.xml.v3.FunctionImport) functionImport)
              && !((org.apache.olingo.client.api.edm.xml.v3.FunctionImport) functionImport).isBindable()
              && !((org.apache.olingo.client.api.edm.xml.v3.FunctionImport) functionImport).isAlwaysBindable()) {
        edmFunctionImport = new EdmFunctionImportProxy(edm, this, functionImport.getName(),
                (org.apache.olingo.client.api.edm.xml.v3.FunctionImport) functionImport);
        functionImports.put(edmFunctionImport.getName(), edmFunctionImport);
      }
    }
  }

  @Override
  protected void loadAllSingletons() {
    if (!(xmlEntityContainer instanceof org.apache.olingo.client.api.edm.xml.v4.EntityContainer)) {
      throw new UnsupportedInV3Exception();
    }

    final List<Singleton> localSingletons =
            ((org.apache.olingo.client.api.edm.xml.v4.EntityContainer) xmlEntityContainer).getSingletons();
    if (localSingletons != null) {
      for (Singleton singleton : localSingletons) {
        singletons.put(singleton.getName(), new EdmSingletonImpl(edm, this, singleton.getName(),
                new EdmTypeInfo.Builder().
                setTypeExpression(singleton.getEntityType()).setDefaultNamespace(entityContainerName.getNamespace()).
                build().getFullQualifiedName(), singleton));
      }
    }
  }

  @Override
  protected void loadAllActionImports() {
    if (xmlEntityContainer instanceof org.apache.olingo.client.api.edm.xml.v4.EntityContainer) {
      final List<ActionImport> localActionImports =
              ((org.apache.olingo.client.api.edm.xml.v4.EntityContainer) xmlEntityContainer).getActionImports();
      if (actionImports != null) {
        for (ActionImport actionImport : localActionImports) {
          actionImports.put(actionImport.getName(),
                  new EdmActionImportImpl(edm, this, actionImport.getName(), actionImport));
        }
      }
    } else {
      @SuppressWarnings("unchecked")
      final List<FunctionImport> localFunctionImports = (List<FunctionImport>) xmlEntityContainer.getFunctionImports();
      for (FunctionImport functionImport : localFunctionImports) {
        if (!FunctionImportUtils.canProxyFunction(functionImport) && !functionImport.isBindable()
                && !functionImport.isAlwaysBindable()) {
          actionImports.put(functionImport.getName(),
                  new EdmActionImportProxy(edm, this, functionImport.getName(), functionImport));
        }
      }
    }
  }

  @Override
  public TargetType getAnnotationsTargetType() {
    return TargetType.EntityContainer;
  }

  @Override
  public EdmAnnotation getAnnotation(final EdmTerm term) {
    return helper == null ? null : helper.getAnnotation(term);
  }

  @Override
  public List<EdmAnnotation> getAnnotations() {
    return helper == null ? Collections.<EdmAnnotation>emptyList() : helper.getAnnotations();
  }

}
