package com.roadrantz.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.roadrantz.service.RantService;

public class HomeAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    ApplicationContext ctx =
        WebApplicationContextUtils.getRequiredWebApplicationContext(
        getServlet().getServletContext());
    
    RantService rantService = (RantService) ctx.getBean("rantService");
    
    List recentRants = rantService.getRecentRants();
    request.setAttribute("rants", recentRants);

    return mapping.findForward("home");
  }
  
  private RantService rantService;
  public void setRantService(RantService rantService) {
    this.rantService = rantService;
  }
}
