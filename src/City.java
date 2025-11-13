
public class City {
    protected int citizensHappiness;
    protected int businessSatisfaction;
    protected int environmentQuality;
    protected int securityLevel;

    private int[] effectStrengths = new int[4]; // indeksai pagal grupes: 0=CIVILIAN, 1=BUSINESS, 2=ENVIRONMENT, 3=SECURITY
    private int[] effectDurations = new int[4];

    public City(int citizensHappiness, int businessSatisfaction, int environmentQuality, int securityLevel) {
        this.citizensHappiness = citizensHappiness;
        this.businessSatisfaction = businessSatisfaction;
        this.environmentQuality = environmentQuality;
        this.securityLevel = securityLevel;
    }

    public void setCitizensHappiness(int citizensHappiness) {
        this.citizensHappiness = citizensHappiness;
    }
    public void setBusinessSatisfaction(int businessSatisfaction) {
        this.businessSatisfaction = businessSatisfaction;
    }
    public void setEnvironmentQuality(int environmentQuality) {
        this.environmentQuality = environmentQuality;
    }
    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    public int getStatus(Type type){
        switch(type) {
            case CIVILIAN:
                return citizensHappiness;
            case ENVIRONMENT:
                return environmentQuality;
            case BUSINESS:
                return businessSatisfaction;
            case SECURITY:
                return securityLevel;
            default:
                throw new IllegalArgumentException("Unknown type: " + type);

        }
    }

    public void updateGroups(){   // auto updatas kas turna
        int min1 = citizensHappiness;
        int min2 = businessSatisfaction;
        if (min2 < min1) {
            int temp = min1; min1 = min2; min2 = temp;
        }

        if (environmentQuality < min1) {
            min2 = min1;
            min1 = environmentQuality;
        } else if (environmentQuality < min2) {
            min2 = environmentQuality;
        }

        if (securityLevel < min1) {
            min2 = min1;
            min1 = securityLevel;
        } else if (securityLevel < min2) {
            min2 = securityLevel;
        }

        // didziausias
        int max = citizensHappiness;
        if (businessSatisfaction > max) max = businessSatisfaction;
        if (environmentQuality > max) max = environmentQuality;
        if (securityLevel > max) max = securityLevel;


        if(max > 80) max = -111;
        //logika
        if (citizensHappiness == min1 || citizensHappiness == min2) citizensHappiness -= 2+citizensHappiness*7/100;
        else if (citizensHappiness == max) citizensHappiness += citizensHappiness*5/100;

        if (businessSatisfaction == min1 || businessSatisfaction == min2) businessSatisfaction -= 2+businessSatisfaction*7/100;
        else if (businessSatisfaction == max) businessSatisfaction += businessSatisfaction*5/100;

        if (environmentQuality == min1 || environmentQuality == min2) environmentQuality -= 2+environmentQuality*7/100;
        else if (environmentQuality == max) environmentQuality += environmentQuality*5/100;

        if (securityLevel == min1 || securityLevel == min2) securityLevel -= 2+securityLevel*7/100;
        else if (securityLevel == max) securityLevel += securityLevel*5/100;
    }


    public boolean checkStatus(){
        if (citizensHappiness<=0) return true;
        else if (businessSatisfaction<=0) return true;
        else if (environmentQuality<=0) return true;
        else if (securityLevel<=0) return true;
        else return false;
    }



    public void activeEffectManager() {

        for(int i=0; i<4; i++){
            if(effectDurations[i]>0){
                switch(i){
                    case 0:
                        citizensHappiness += effectStrengths[i];
                        citizensHappiness = clamp(citizensHappiness);
                        break;
                    case 1:
                        environmentQuality += effectStrengths[i];
                        environmentQuality = clamp(environmentQuality);
                        break;
                    case 2:
                        businessSatisfaction += effectStrengths[i];
                        businessSatisfaction = clamp(businessSatisfaction);
                        break;
                    case 3:
                        securityLevel += effectStrengths[i];
                        securityLevel = clamp(securityLevel);
                        break;

                }
                effectDurations[i] --;
            }
        }

    }

    public void effectManager(Type type, int value) {

        switch(type){
            case CIVILIAN:
                citizensHappiness += value;
                citizensHappiness = clamp(citizensHappiness);
                break;
            case ENVIRONMENT:
                environmentQuality += value;
                environmentQuality = clamp(environmentQuality);
                break;
            case BUSINESS:
                businessSatisfaction += value;
                businessSatisfaction = clamp(businessSatisfaction);
                break;
            case SECURITY:
                securityLevel += value;
                securityLevel = clamp(securityLevel);
                break;

        }


    }

    public int[] getEffectDurations() {
        return effectDurations;
    }

    public void setEffectDuration(int index, int duration) {
        effectDurations[index] = duration;
    }

    public int[] getEffectStrengths() {
        return effectStrengths;
    }

    public void setEffectStrength(int index, int strength) {
        effectStrengths[index] = strength;
    }

    public int clamp(int value) {
        if (value > 100) return 100;
        if (value < 0) return 0;
        return value;
    }
}
