package ua.training.hospital.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.locks.ReadWriteLock;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private static final Logger logger = LogManager.getLogger(ErrorController.class);


    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String handleError(HttpServletRequest request) {
        logger.debug(" /error requested");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                logger.debug(" http status 404, returning pageNotFound.jsp page");
                return "pageNotFound";
            }
        }
        logger.debug("returning errorPage.jsp page");
        return getErrorPath();
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
