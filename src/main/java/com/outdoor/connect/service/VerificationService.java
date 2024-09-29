package com.outdoor.connect.service;

import java.util.Map;

public interface VerificationService {

    void reSendVerification();

    public Map<String, Object> verifyCode(String verificationCode);

}
