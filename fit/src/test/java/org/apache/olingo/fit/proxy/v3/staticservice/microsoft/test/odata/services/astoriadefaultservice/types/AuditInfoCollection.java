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
package org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types;

//CHECKSTYLE:OFF (Maven checkstyle)
import org.apache.olingo.ext.proxy.api.AbstractTerm;
import java.util.Collection;
//CHECKSTYLE:ON (Maven checkstyle)


public interface AuditInfoCollection extends 
    org.apache.olingo.ext.proxy.api.StructuredCollectionQuery<org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.AuditInfoCollection>,
    org.apache.olingo.ext.proxy.api.ComplexCollection<org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.AuditInfo, org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.AuditInfoCollection, org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.AuditInfoCollection> {

        Operations operations();

    interface Operations extends org.apache.olingo.ext.proxy.api.Operations{
    
        }
  Object getAnnotation(Class<? extends AbstractTerm> term);

  Collection<Class<? extends AbstractTerm>> getAnnotationTerms();
}
