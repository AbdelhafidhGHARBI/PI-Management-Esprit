package tn.esprit.user_service.service.servicesInterfaces;

import jakarta.servlet.http.HttpServletRequest;
import tn.esprit.user_service.entities.DeviceInfo;

public interface DeviceMetadataService {
    DeviceInfo extractDeviceInfo(HttpServletRequest request) ;
}
