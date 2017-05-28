package api.utils;

/**
 * Created by Vileven on 28.05.17.
 */
public class TimeHelper {
    public static void sleep(long period){
        if (period <= 0) {
            return;
        }
        try{
            Thread.sleep(period);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(){
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

