/*
 * $Id: ResponseStateManager.java,v 1.15 2005/03/11 21:05:27 edburns Exp $
 */

/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.faces.render;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.application.StateManager.SerializedView;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;



/**
 * <p><strong>ResponseStateManager</strong> is the helper class to
 * {@link javax.faces.application.StateManager} that knows the specific
 * rendering technology being used to generate the response.  It is a
 * singleton abstract class, vended by the {@link RenderKit}.  This
 * class knows the mechanics of saving state, whether it be in hidden
 * fields, session, or some combination of the two.</p>
 */

public abstract class ResponseStateManager {


    /**
     *       
     * <p>Take the argument <code>state</code> and write it into
     * the output using the current {@link ResponseWriter}, which
     * must be correctly positioned already.</p>
     *
     * <p>If the {@link
     * javax.faces.application.StateManager.SerializedView} is to be
     * written out to hidden fields, the implementation must take care
     * to make all necessary character replacements to make the Strings
     * suitable for inclusion as an HTTP request paramater.</p>
     *
     * <p>If the state saving method for this application is {@link
     * javax.faces.application.StateManager#STATE_SAVING_METHOD_SERVER},
     * the implementation may encrypt the state to be saved to the
     * client.  We recommend that the state be unreadable by the client,
     * and also be tamper evident.  The reference implementation follows
     * these recommendations.  </p>
     *
     * @param context The {@link FacesContext} instance for the current request
     * @param state The serialized state information previously saved
     *
     */
    public abstract void writeState(FacesContext context,
				    SerializedView state) throws IOException;
    

    /**
     * <p>The implementation must inspect the current request and return
     * the tree structure Object passed to it on a previous invocation of
     * <code>writeState()</code>.</p>
     *
     * @param context The {@link FacesContext} instance for the current request
     * @param viewId View identifier of the view to be restored
     *
     * @return the tree structure Object passed in to
     * <code>writeState</code>.  If this is the initial request, this
     * method returns null.
     */
    public abstract Object getTreeStructureToRestore(FacesContext context, 
						     String viewId);


    /**
     * <p>The implementation must inspect the current request and return
     * the component state Object passed to it on a previous invocation
     * of <code>writeState()</code>.</p>
     *
     * @param context The {@link FacesContext} instance for the current request
     *
     * @return the component state Object passed in to
     * <code>writeState</code>.
     */
    public abstract Object getComponentStateToRestore(FacesContext context);


}
