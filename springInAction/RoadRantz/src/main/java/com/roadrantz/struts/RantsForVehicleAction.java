package com.roadrantz.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.ActionSupport;

import com.roadrantz.domain.Vehicle;
import com.roadrantz.service.RantService;

public class RantsForVehicleAction extends ActionSupport
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        VehicleForm vehicleForm = (VehicleForm) form;
        Vehicle vehicle = vehicleForm.getVehicle();

        RantService rantService = (RantService) getWebApplicationContext().getBean("rantService");

        request.setAttribute("rants", rantService.getRantsForVehicle(vehicleForm.getVehicle()));
        request.setAttribute("vehicle", vehicle);

        return mapping.findForward("rantList");
    }

    private RantService rantService;

    public void setRantService(RantService rantService)
    {
        this.rantService = rantService;
    }
}
