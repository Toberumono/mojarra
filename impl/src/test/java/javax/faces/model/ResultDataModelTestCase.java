/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2014 Oracle and/or its affiliates. All rights reserved.
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
package javax.faces.model;

import java.util.Map;
import com.sun.faces.mock.MockResult;
import static junit.framework.Assert.assertTrue;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for {@link ResultDataModel}.</p>
 */
public class ResultDataModelTestCase extends DataModelTestCaseBase {

    // ------------------------------------------------------------ Constructors
    /**
     * Construct a new instance of this test case.
     *
     * @param name Name of the test case
     */
    public ResultDataModelTestCase(String name) {

        super(name);

    }

    // ------------------------------------------------------ Instance Variables
    // The Result passed to our ResultDataModel
    protected MockResult result = null;

    // ---------------------------------------------------- Overall Test Methods
    // Set up instance variables required by this test case.
    @Override
    public void setUp() throws Exception {
        beans = new BeanTestImpl[5];
        for (int i = 0; i < beans.length; i++) {
            beans[i] = new BeanTestImpl();
        }
        configure();
        result = new MockResult(beans);
        model = new ResultDataModel(result);
        super.setUp();
    }

    // Return the tests included in this test case.
    public static Test suite() {
        return (new TestSuite(ResultDataModelTestCase.class));
    }

    // ------------------------------------------------- Individual Test Methods
    // ------------------------------------------------------- Protected Methods
    @Override
    protected BeanTestImpl data() throws Exception {
        Object data = model.getRowData();
        assertTrue(data instanceof Map);
        BeanTestImpl bean = new BeanTestImpl();
        Map map = (Map) data;

        bean.setBooleanProperty(((Boolean) map.get("booleanProperty")).booleanValue());
        bean.setBooleanSecond(((Boolean) map.get("booleanSecond")).booleanValue());
        bean.setByteProperty(((Byte) map.get("byteProperty")).byteValue());
        bean.setDoubleProperty(((Double) map.get("doubleProperty")).doubleValue());
        bean.setFloatProperty(((Float) map.get("floatProperty")).floatValue());
        bean.setIntProperty(((Integer) map.get("intProperty")).intValue());
        bean.setLongProperty(((Long) map.get("longProperty")).longValue());
        bean.setNullProperty((String) map.get("nullProperty"));
        bean.setShortProperty(((Short) map.get("shortProperty")).shortValue());
        bean.setStringProperty((String) map.get("stringProperty"));
        bean.setWriteOnlyProperty((String) map.get("writeOnlyPropertyValue"));

        return (bean);
    }
}
