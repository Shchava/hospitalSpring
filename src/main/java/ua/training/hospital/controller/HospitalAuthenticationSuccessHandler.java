package ua.training.hospital.controller;


import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.training.hospital.entity.UserAuthentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Getter
@Setter
@Component
public class HospitalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LogManager.getLogger(HospitalAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        handle(httpServletRequest, httpServletResponse, authentication);
        clearAuthenticationAttributes(httpServletRequest);

    }

    private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            logger.warn("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
        logger.debug("Login success, redirecting to: " + targetUrl);
    }

    public static String determineTargetUrl(Authentication authentication) {
        UserAuthentication authData = (UserAuthentication)authentication.getPrincipal();
        boolean isPersonal = false;
        boolean isPatient = false;
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_DOCTOR") || grantedAuthority.getAuthority().equals("ROLE_NURSE")) {
                isPersonal = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_PATIENT")) {
                isPatient = true;
                break;
            }
        }

        if (isPersonal) {
            return "/patientsList";
        } else if (isPatient) {
            return "/patient" + authData.getId();
        } else {
            throw new IllegalStateException();
        }
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
