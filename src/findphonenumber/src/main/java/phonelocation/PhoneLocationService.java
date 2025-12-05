package phonelocation;

import com.google.i18n.phonenumbers.*;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

import java.util.List;
import java.util.Locale;

public class PhoneLocationService {
    private final PhoneNumberUtil phoneNumberUtil;
    private final PhoneNumberOfflineGeocoder geocoder;
    private final PhoneNumberToCarrierMapper carrierMapper;
    private final PhoneNumberToTimeZonesMapper timeZonesMapper;

    public PhoneLocationService() {
        this.phoneNumberUtil = PhoneNumberUtil.getInstance();
        this.geocoder = PhoneNumberOfflineGeocoder.getInstance();
        this.carrierMapper = PhoneNumberToCarrierMapper.getInstance();
        this.timeZonesMapper = PhoneNumberToTimeZonesMapper.getInstance();
    }

    /**
     * Get comprehensive information about a phone number
     * @param phoneNumberStr Phone number string (with or without country code)
     * @param defaultRegion Default region code (e.g., "US", "IN", "GB")
     * @return PhoneNumberInfo object with all available information
     */
    public PhoneNumberInfo getPhoneNumberInfo(String phoneNumberStr, String defaultRegion) {
        PhoneNumberInfo info = new PhoneNumberInfo();
        info.setPhoneNumber(phoneNumberStr);

        try {
            // Parse the phone number
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phoneNumberStr, defaultRegion);

            // Validate the phone number
            boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
            info.setValid(isValid);

            if (isValid) {
                // Get country code
                int countryCode = phoneNumber.getCountryCode();
                info.setCountryCode("+" + countryCode);

                // Get country name
                String regionCode = phoneNumberUtil.getRegionCodeForNumber(phoneNumber);
                info.setCountryName(getCountryName(regionCode));

                // Get formatted numbers
                String formattedNumber = phoneNumberUtil.format(phoneNumber,
                        PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
                info.setFormattedNumber(formattedNumber);

                String internationalFormat = phoneNumberUtil.format(phoneNumber,
                        PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                info.setInternationalFormat(internationalFormat);

                // Get geographic location
                String location = geocoder.getDescriptionForNumber(phoneNumber, Locale.ENGLISH);
                info.setRegion(location);

                // Get carrier information
                String carrier = carrierMapper.getNameForNumber(phoneNumber, Locale.ENGLISH);
                info.setCarrier(carrier);

                // Get number type
                PhoneNumberUtil.PhoneNumberType numberType = phoneNumberUtil.getNumberType(phoneNumber);
                info.setNumberType(getNumberTypeString(numberType));

                // Get time zones
                List<String> timeZones = timeZonesMapper.getTimeZonesForNumber(phoneNumber);
                if (!timeZones.isEmpty()) {
                    info.setTimeZone(String.join(", ", timeZones));
                }
            }

        } catch (NumberParseException e) {
            info.setValid(false);
            info.setRegion("Error: " + e.getMessage());
        }

        return info;
    }

    /**
     * Validate if a phone number is valid
     */
    public boolean isValidPhoneNumber(String phoneNumberStr, String defaultRegion) {
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phoneNumberStr, defaultRegion);
            return phoneNumberUtil.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            return false;
        }
    }

    /**
     * Get possible phone number format example for a country
     */
    public String getExampleNumber(String regionCode) {
        Phonenumber.PhoneNumber exampleNumber = phoneNumberUtil.getExampleNumber(regionCode);
        if (exampleNumber != null) {
            return phoneNumberUtil.format(exampleNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        }
        return "No example available";
    }

    /**
     * Convert number type enum to readable string
     */
    private String getNumberTypeString(PhoneNumberUtil.PhoneNumberType type) {
        switch (type) {
            case FIXED_LINE:
                return "Fixed Line";
            case MOBILE:
                return "Mobile";
            case FIXED_LINE_OR_MOBILE:
                return "Fixed Line or Mobile";
            case TOLL_FREE:
                return "Toll Free";
            case PREMIUM_RATE:
                return "Premium Rate";
            case SHARED_COST:
                return "Shared Cost";
            case VOIP:
                return "VoIP";
            case PERSONAL_NUMBER:
                return "Personal Number";
            case PAGER:
                return "Pager";
            case UAN:
                return "UAN (Universal Access Number)";
            case VOICEMAIL:
                return "Voicemail";
            default:
                return "Unknown";
        }
    }

    /**
     * Get full country name from region code
     */
    private String getCountryName(String regionCode) {
        Locale locale = new Locale("", regionCode);
        return locale.getDisplayCountry(Locale.ENGLISH);
    }
}
