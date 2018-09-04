package com.lw.dmappserver.factory;

import com.lw.dmappserver.service.AuthService;
import com.lw.dmappserver.service.ExportService;

public class ServiceFactory {
    public static ExportService createExportService() {
        return new ExportService();
    }
    public static AuthService createExportService(String secret) {
        return new AuthService(secret);
    }
}
