package com.outdoor.connect.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.outdoor.connect.exception.NoParticipantException;
import com.outdoor.connect.model.Participant;
import com.outdoor.connect.repository.ParticipantRepository;
import com.outdoor.connect.security.bean.UserBean;

public abstract class UserUtils {

    @Autowired
    private static ParticipantRepository participantRepository;

    public static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    
	    return username;
	}

    public static Long getPrincipalId() {
        UserBean userBean = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getPrincipal().equals("anonymousUser")) {
            userBean = (UserBean) authentication.getPrincipal();

            return userBean.getId();
        } else {        
            return null;
        }
    }

    public static Participant getParticipant() {

        UserBean userBean;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getPrincipal().equals("anonymousUser")) {
            userBean = (UserBean) authentication.getPrincipal();

            Long principalId = userBean.getId();

            if (principalId == null) {
                return null;
            } else {
                return participantRepository.findById(principalId).orElseThrow(() -> new NoParticipantException());
            }
        } else {        
            return null;
        }
    }
}
