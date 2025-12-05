package phonelocation;

public class PhoneNumberInfo {
    private String phoneNumber;
    private String countryCode;
    private String countryName;
    private String region;
    private String carrier;
    private String numberType;
    private String timeZone;
    private boolean isValid;
    private String formattedNumber;
    private String internationalFormat;

    // Constructor
    public PhoneNumberInfo() {
    }

    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getFormattedNumber() {
        return formattedNumber;
    }

    public void setFormattedNumber(String formattedNumber) {
        this.formattedNumber = formattedNumber;
    }

    public String getInternationalFormat() {
        return internationalFormat;
    }

    public void setInternationalFormat(String internationalFormat) {
        this.internationalFormat = internationalFormat;
    }

    @Override
    public String toString() {
        return "PhoneNumberInfo{" +
                "\n  phoneNumber='" + phoneNumber + '\'' +
                "\n  countryCode='" + countryCode + '\'' +
                "\n  countryName='" + countryName + '\'' +
                "\n  region='" + region + '\'' +
                "\n  carrier='" + carrier + '\'' +
                "\n  numberType='" + numberType + '\'' +
                "\n  timeZone='" + timeZone + '\'' +
                "\n  isValid=" + isValid +
                "\n  formattedNumber='" + formattedNumber + '\'' +
                "\n  internationalFormat='" + internationalFormat + '\'' +
                "\n}";
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════════════════════╗\n");
        sb.append("║           PHONE NUMBER INFORMATION                    ║\n");
        sb.append("╠════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Original Number    : %-30s║\n", phoneNumber));
        sb.append(String.format("║ Valid              : %-30s║\n", isValid ? "Yes" : "No"));
        if (isValid) {
            sb.append(String.format("║ Formatted Number   : %-30s║\n", formattedNumber));
            sb.append(String.format("║ International      : %-30s║\n", internationalFormat));
            sb.append(String.format("║ Country Code       : %-30s║\n", countryCode));
            sb.append(String.format("║ Country Name       : %-30s║\n", countryName));
            sb.append(String.format("║ Region/City        : %-30s║\n", region != null ? region : "N/A"));
            sb.append(String.format("║ Carrier/Operator   : %-30s║\n", carrier != null && !carrier.isEmpty() ? carrier : "Unknown"));
            sb.append(String.format("║ Number Type        : %-30s║\n", numberType));
            sb.append(String.format("║ Time Zone          : %-30s║\n", timeZone != null ? timeZone : "N/A"));
        }
        sb.append("╚════════════════════════════════════════════════════════╝\n");
        return sb.toString();
    }
}
