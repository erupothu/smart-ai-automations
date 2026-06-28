package com.smartautomation.validation;

public class ValidatonError {
    
    private final String fieldLoaction;
    private final String message;
    private final String errorCode;
    public ValidatonError(String fieldLoaction, String message, String errorCode) {
        this.fieldLoaction = fieldLoaction;
        this.message = message;
        this.errorCode = errorCode;
    }
    public String getFieldLoaction() {
        return fieldLoaction;
    }
    public String getMessage() {
        return message;
    }
    public String getErrorCode() {
        return errorCode;
    }
    @Override
    public String toString() {
        return "ValidatonError [fieldLoaction=" + fieldLoaction + ", message=" + message + ", errorCode=" + errorCode
                + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fieldLoaction == null) ? 0 : fieldLoaction.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ValidatonError other = (ValidatonError) obj;
        if (fieldLoaction == null) {
            if (other.fieldLoaction != null)
                return false;
        } else if (!fieldLoaction.equals(other.fieldLoaction))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (errorCode == null) {
            if (other.errorCode != null)
                return false;
        } else if (!errorCode.equals(other.errorCode))
            return false;
        return true;
    }

    
}
