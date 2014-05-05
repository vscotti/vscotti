package com.uade.tesis.web.utils;

import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

@SuppressWarnings("deprecation")
public class ValueBindingViewHandler extends ViewHandlerWrapper {

	protected ViewHandler wrappedViewHandler;

	public ValueBindingViewHandler(ViewHandler viewHandler) {
		super();
		this.wrappedViewHandler = viewHandler;
	}

	protected ViewHandler getWrapped() {
		return this.wrappedViewHandler;
	}

	@Override
	public String getActionURL(FacesContext context, String viewId) {
		String result = viewId;
		ValueBinding vb =
		context.getApplication().createValueBinding(viewId);
		result = vb.getValue(context).toString();
		return super.getActionURL(context, result);
	}
}
