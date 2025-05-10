package tn.esprit.user_service.entities.enums;

public enum ActivityType {
    LOGIN,
    LOGOUT,
    LOGIN_FAILURE, // NEW
    PASSWORD_CHANGE,
    PROFILE_UPDATE,
    REFRESH_TOKEN_USED, // NEW
    DEVICE_VERIFIED // NEW
}