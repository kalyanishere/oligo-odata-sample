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
package org.apache.olingo.odata4.server.api.uri;

import org.apache.olingo.odata4.commons.api.edm.EdmType;

/**
 * Class indicating the $it reference. $it may be used within filter to
 * refer to the last EDM object reference in the resource path. Since $it is 
 * optional in some cases ( e.g. first member expressions) the {@link #isExplicitIt()} 
 * method can be used to check if $it was explicitly noted in the URI
 */
public interface UriResourceIt extends UriResourcePartTyped {

  EdmType getTypeFilterOnCollection();

  EdmType getTypeFilterOnEntry();
}