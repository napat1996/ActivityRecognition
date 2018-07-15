package info.androidhive.activityrecognition;

class Label {
    private String startTime;
    private String stopTime;

    public Label() {
    }

    public Label(String startTime, String stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }
}
