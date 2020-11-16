public class Race {
    private int min_sleep;
    private int max_sleep;
    private int distance;
    private int photoFinish = 0;
    private int place = 1;

    public Race(int min_sleep, int max_sleep, int distance) {
        super();
        this.min_sleep = min_sleep;
        this.max_sleep = max_sleep;
        this.distance = distance;
    }

    public void setPhotoFinish(int photoFinish) {
        this.photoFinish = photoFinish;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getMin_sleep() {
        return min_sleep;
    }

    public int getMax_sleep() {
        return max_sleep;
    }

    public int getDistance() {
        return distance;
    }

    public int getPhotoFinish() {
        return photoFinish;
    }

    public int getPlace() {
        return place;
    }
}
