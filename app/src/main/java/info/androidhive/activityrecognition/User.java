package info.androidhive.activityrecognition;

class User {
    private String Running;
    private String Still;
    private String walking;
    private String Bycing;
    private String driving;

    public User() {
    }

    public User(String running, String still, String walking, String bycing, String driving) {
        this.Running = running;
        this.Still = still;
        this.walking = walking;
        this.Bycing = bycing;
        this.driving = driving;
    }

    public User(String label, String startTime, String endTime) {

    }

    public String getRunning() {
        return Running;
    }

    public void setRunning(String running) {
        Running = running;
    }

    public String getStill() {
        return Still;
    }

    public void setStill(String still) {
        Still = still;
    }

    public String getWalking() {
        return walking;
    }

    public void setWalking(String walking) {
        this.walking = walking;
    }

    public String getBycing() {
        return Bycing;
    }

    public void setBycing(String bycing) {
        Bycing = bycing;
    }

    public String getDriving() {
        return driving;
    }

    public void setDriving(String driving) {
        this.driving = driving;
    }

}
