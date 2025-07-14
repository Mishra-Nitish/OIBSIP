import java.util.Random;
import java.util.Scanner;

public class RandomGuess {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random rdm = new Random();

        int minRange = 1;
        int maxRange = 100;
        int totalNumOfAttempts = 10;
        int randomNum = rdm.nextInt(maxRange);
        int score = 0;

        for(int attempt = 1; attempt <= totalNumOfAttempts; attempt++){


            System.out.println("------ATTEMPT : " + attempt + "----------");
            System.out.print("Enter your Guess : ");
            int userGuess = sc.nextInt();

            if(randomNum == userGuess){
                System.out.println("Congrats! You have guess the correct answer at " + attempt + " attempts.");
                score = totalNumOfAttempts - attempt + 1;
                break;
            }
            else if(randomNum > userGuess){
                System.out.println("Your guess is low!!");
            }
            else{
                System.out.println("Your guess is high !!");
            }
        }

        if(score > 0){
            System.out.println("Your total score is : " + score);
        }
        else{
            System.out.println("Sorry! you were not able to guess the answer. the answer is " + randomNum);
        }

    }
}
