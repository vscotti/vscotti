package com.uade.tesis.utils;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("deprecation")
public class InitializingPhaseListener implements PhaseListener{

	private static final long serialVersionUID = 1L;

	private static final String MAP_VALUE_BINDING="com.uade.tesis.utils.InitializingPhaseListener.MAP_VALUE_BINDING";
	private static final String MAP_VALUE_BINDING_DEFAULT="appbean.viewmap";

	public void afterPhase(PhaseEvent arg0) {
	}

	@SuppressWarnings({ "unchecked" })
	public void beforePhase(PhaseEvent arg0) {
		try {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		String method = request.getMethod();
		if (method.equals("GET")) {
			//get app bean name from web.xml
			String applicationbeanname=MAP_VALUE_BINDING_DEFAULT;
			ExternalContext ec = context.getExternalContext();
			String value = ec.getInitParameter(MAP_VALUE_BINDING);
			if(value != null){
				applicationbeanname = value;
			}

			//get the map
			Application application = context.getApplication();
			ValueBinding vb = application.createValueBinding("#{" + applicationbeanname + "}");
			Object val = vb.getValue(context);
			Map<String, String> viewmap = (Map<String, String>) val;

			UIViewRoot viewcurrent = context.getViewRoot();
			String viewid=viewcurrent.getViewId();
			//System.out.println("SamplePhaseListener.beforePhase, viewid=" + viewid);
			String methodname = viewmap.get(viewid);
			if(methodname != null){
				//System.out.println("SamplePhaseListener.beforePhase, methodname=" + methodname);
				MethodBinding action = application
				.createMethodBinding("#{" + methodname + "}", null);
				String outcome = (String) action.invoke(context, null);
				//get the page to display according to the nav rules
				NavigationHandler navigationHandler = application
						.getNavigationHandler();
				navigationHandler.handleNavigation(context, null, outcome);
			}
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
