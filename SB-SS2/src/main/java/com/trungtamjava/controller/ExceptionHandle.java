package com.trungtamjava.controller;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ExceptionHandle implements ErrorController {
	
	@RequestMapping(value = "/error")
	public String handlerError(HttpServletRequest request) {
		 Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		    if (status != null) {
		      Integer statusCode = Integer.valueOf(status.toString());
		      if (statusCode == HttpStatus.NOT_FOUND.value()) {
		        return "client/404";
		      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
		        return "client/500";
		      }
		    }
		    return "client/error";
	}

	@Override
	public String getErrorPath() {
		
		return "redirect:/error";
	}


}
