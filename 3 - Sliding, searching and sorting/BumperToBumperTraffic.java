import java.util.*;
import java.io.*;


public class BumperToBumperTraffic {


    // A class for the vehicle 
    // Handles the driving sim
    // and holds information
    // about times the vehicle 
    // drives and stops as well as the vehicles current position
    static class Vehicle {

        // Queue for each vehicle drive and stop times
        Queue<Integer> driveList = new LinkedList<>();

        // A boolean that determines if said vehicle is moving.
        // Always starts as false since the vehicles do not drive at T = 0
        boolean driving = false;

        // The position of the rear of the vehicle
        int position;

        public Vehicle(int initPosition) {
            this.position = initPosition;
        }

        void drive(int time) {

            // Check if the "command" list for driving is stopping is empty
            // and also check is current time is equal to the next
            // time in the driveList 
            // If both are satisifed, flip the driving boolean and remove 
            // "command" from the queue
            if (!driveList.isEmpty() && time == driveList.peek()) {
                // Flip the boolean to change from driving to stop or other way around
                driving = !driving;

                driveList.poll();
            }

            // If driving, move vehicle 1 forward
            if(driving) {
                position += 1;
            }
        }
    }

    // Solves the problem by simulating the driving for both vehicles
    static int drive(Vehicle one, Vehicle two) {
        int time = -1;

        // Run the drive function on both vehicle until the driveList of both are empty
        while(!one.driveList.isEmpty() || !two.driveList.isEmpty()) {

            // Run the drive function
            one.drive(time);
            two.drive(time);

            // Increment time
            time += 1;
            
            // If distance between vehicles after driving is 4 or less, they have collided
            // return the time of collision
            if(Math.abs(one.position - two.position) < 5) {
                return time;
            }
        }
        // If the simulation end with the vehicle behind driving and the one in front not driving
        // Return the time when they impact
        // Do this for both the cars
        if(one.position < two.position && one.driving && !two.driving) {
            // In this case, vehicle one is behind two and one is driving, two is not
            // Therefore there will bi impact at time + (two.pos-one.pos) - 4
            return time + (two.position - one.position) - 4;
        }

        // Do the same procedure the other way around
        if(two.position < one.position && two.driving && !one.driving) {
            return time + (one.position - two.position) - 4;
        }

        // Else we return -1 to confirm that there were no collisions
        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String next;
        next = br.readLine();
        String[] nums = next.split(" ");

        // Get the start position for vehicle one and two
        int onePos = Integer.parseInt(nums[0]);
        int twoPos = Integer.parseInt(nums[1]);

        // Init the two vehicles
        Vehicle one = new Vehicle(onePos);
        Vehicle two = new Vehicle(twoPos);

        // Get the "commands" for both vehicles   
        String[] oneCommands = br.readLine().split(" ");
        String[] twoCommands = br.readLine().split(" ");

        for(int i = 0; i < Integer.parseInt(oneCommands[0]); i++) {
            // Add start and stops to vehicle one's driveList
            one.driveList.add(Integer.parseInt(oneCommands[i+1]));
        }

        for(int i = 0; i < Integer.parseInt(twoCommands[0]); i++) {
            // Add start and stops to vehicle two's drive list
            two.driveList.add(Integer.parseInt(twoCommands[i+1]));
        }

        // Get result from simulating the driving
        int result = drive(one, two);

        // If no collision -> result = -1
        // else it is a collision and we print message with time of collision
        if(result == -1) {
            System.out.println("safe and sound");
        } else {
            System.out.println("bumper tap at time " + result);
        }
    }



}

