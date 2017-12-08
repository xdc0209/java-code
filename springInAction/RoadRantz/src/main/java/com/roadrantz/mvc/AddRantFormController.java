package com.roadrantz.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;
import com.roadrantz.service.RantService;

public class AddRantFormController extends SimpleFormController {
   private static final String[] ALL_STATES = { "AL", "AK", "AZ", "AR", "CA",
         "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA",
         "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
         "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR",
         "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WV",
         "WI", "WY" };

   public AddRantFormController() {
      setCommandClass(Rant.class);
      setCommandName("rant");
   }

   @Override
   protected Object formBackingObject(HttpServletRequest request)
                     throws Exception {
      Rant rantForm = (Rant) super.formBackingObject(request);
      rantForm.setVehicle(new Vehicle());
      return rantForm;
   }

   @Override
   protected Map referenceData(HttpServletRequest request) throws Exception {
      Map referenceData = new HashMap();

      referenceData.put("states", ALL_STATES);

      return referenceData;
   }

   @Override
   protected ModelAndView onSubmit(Object command, BindException bindException)
                     throws Exception {

      Rant rant = (Rant) command;
      rantService.addRant(rant);

      return new ModelAndView(getSuccessView());
   }

   // injected
   private RantService rantService;

   public void setRantService(RantService rantService) {
      this.rantService = rantService;
   }
}
