/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.faces.application;

import java.beans.PropertyEditorSupport;

/**
 * Abstract base for a {@link java.beans.PropertyEditor} that delegates to a faces Converter that
 * was registered by-type in a faces-config descriptor. Concrete implementations (such as generated
 * by {@link ConverterPropertyEditorFactory}) will override {@link #getTargetClass}. (This is based
 * on the original ConverterPropertyEditor code).
 */
public abstract class ConverterPropertyEditorBase extends PropertyEditorSupport {

    /**
     * Return the target class of the objects that are being edited. This is used as a key to find
     * the appropriate {@link javax.faces.convert.Converter} from the Faces application.
     *
     * @return the target class.
     */
    protected abstract Class<?> getTargetClass();

    /**
     * Convert the <code>textValue</code> to an object of type {@link #getTargetClass} by delegating
     * to a converter in the faces Application.
     */
    @Override
    public void setAsText(String textValue) throws IllegalArgumentException {
        try {
            Object appAssociate = getPropertyEditorHelper();
            // Get targetClass for the current ClassLoader
            Class<?> targetClass = Thread.currentThread().getContextClassLoader().loadClass(getTargetClass().getName());

            Object value = appAssociate.getClass().getMethod("convertToObject", Class.class, String.class).invoke(appAssociate, targetClass, textValue);

            if (value != null) {
                setValue(value);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected Error attempting to use this ConverterPropertyEditor.  You're deployment environment may not support"
                    + "ConverterPropertyEditors.  Try restarting your server or disabling " + "com.sun.faces.registerConverterPropertyEditors", e);
        }
    }

    private Object getPropertyEditorHelper() throws Exception {
        // Load the current
        Class<?> facesContextClass = Thread.currentThread().getContextClassLoader().loadClass("com.sun.faces.application.ApplicationAssociate");

        // Get the current context version of this class in case
        Object appAssociate = facesContextClass.getMethod("getCurrentInstance").invoke(null);

        if (appAssociate == null) {
            throw new IllegalStateException("Unable to find Deployed JSF Application.  You're deployment environment may not support"
                    + "ConverterPropertyEditors.  Try restarting your server or turn off " + "com.sun.faces.registerConverterPropertyEditors");
        }

        return appAssociate.getClass().getMethod("getPropertyEditorHelper").invoke(appAssociate);
    }

    /**
     * Convert an object of type {@link #getTargetClass} to text by delegating to a converter
     * obtained from the Faces Application.
     */
    @Override
    public String getAsText() {
        try {
            Object application = getPropertyEditorHelper();

            Class<?> targetClass = Thread.currentThread().getContextClassLoader().loadClass(getTargetClass().getName());

            String text = (String) application.getClass().getMethod("convertToString", Class.class, Object.class).invoke(application, targetClass, getValue());

            if (text != null) {
                return text;
            }

            return super.getAsText();

        } catch (Exception e) {
            return super.getAsText();
        }
    }
}
