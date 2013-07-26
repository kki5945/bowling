package bowling;

import java.util.Scanner;

public class BowlingGame {	
	String scoreBoder = "|";
	String printBorder = "|";
	int totalScoreBoder[][] = new int[15][5];
	int totalScore = 0;
	int spareCheck = 0;
	
	public BowlingGame() {
		System.out.println("게임을 시작합니다. \n");
	}

	public void endGame(int result) {
		System.out.println("게임이 종료되었습니다. \n최종 스코어는 " + result + "점 입니다.");
	}
	
	public int pointCalculate() {
		Boolean twoBall;
		int oneScore;
		int twoScore;

		for (int i = 1; i < 13; i++) {
			oneScore = 0;
			twoScore = 0;
			twoBall = false;
			
			/////////////// 초구를 점수를 얻어와 점수판에 뿌려준다. ////////////////
			oneScore = inputScore(i, twoScore,twoBall);
			roll(i, oneScore,twoScore,twoBall);
			
			/////////////// 초구 투구 시 전 프레임에서의 보너스 점수 처리. ///////////////
			frameBonusCheck(i,oneScore,twoBall);
			
			/////////////// 스트라이크 처리 ///////////////
			if(oneScore == 10)
			{	
				if(i == 12) {
					totalScoreBoder[i-2][1] = totalScore + 30;
					
					return totalScore;
				}
				if(i < 10)
					continue;
			}
			
			if(i==11) {
				return totalScore;
			}
				
			////////////// 이구 점수를 얻어와 점수판에 뿌려준다. /////////////////
			twoBall = true;
			twoScore = inputScore(i,twoScore,twoBall);
			roll(i, oneScore,twoScore,twoBall);
			totalScoreBoder[i][0] = oneScore + twoScore;
			
			/////////////// 이구 투구 시 전 프레임에서의 보너스 점수 처리. ///////////////
			frameBonusCheck(i,oneScore,twoBall);
			
			
			/////////////// 스페어 처리 /////////////////
			if((oneScore + twoScore) > 9) {
				spareCheck = 1;
				spareShot(i);
				totalScore = totalScore + totalScoreBoder[i][0];
				
				continue;
			}
			
			
			////////////// 일반 투구 처리 ////////////////
			nomalShot(i);
			totalScore = totalScore + totalScoreBoder[i][0];
			printTotalScore(i);
			
			if(i == 10) {
				if(totalScoreBoder[i][2] == 0 && spareCheck == 0) {
					return totalScore;
				}
			}
        }
		return totalScore;
	}

	//////////////////// 점수를 입력 받아온다. ///////////////////////
	private int inputScore(int frameNumber, int twoScore, Boolean twoBall) {
		//초구,이구 구분을 위하여 twoBall 파라메터를 얻어온다.
		int score = 0;
		
		if(twoBall == false)
			System.out.println(frameNumber + " 프레임 초구 점수를 입력하여 주시기 바랍니다. (0 ~ 10)");
		
		if(twoBall == true)
			System.out.println(frameNumber + " 프레임 이구 점수를 입력하여 주시기 바랍니다. (0 ~ 10)");
		
		System.out.printf("=> ");
		Scanner sc = new Scanner(System.in);
		
	    while (!sc.hasNextInt()) {
	        sc.next(); // 에러일 경우, 방금 입력받은 값 무시
	        System.err.println("에러! 올바른 범위의 점수를 다시 입력하여 주시기 바랍니다.. (0 ~ 10)");
	    }

	    score = sc.nextInt();
		
		return score;	
	}

	public void roll(int frameNumber, int oneScore, int twoScore, Boolean twoBall) {
		String scoreSymbol = ""+twoScore;
		
		//////////////  볼 처리. start /////////////////
		System.out.println("토탈 스코어 = " + totalScore);
        
        if(oneScore == 10)
        {	
        	totalScoreBoder[frameNumber][0] = 10;
        	totalScoreBoder[frameNumber][1] = 0;
        	totalScoreBoder[frameNumber][2] = 1;
        	
        	scoreSymbol = "X";
        	printScore(scoreSymbol);
        	
        	scoreSymbol = " ";
        	printScore(scoreSymbol);
        	
        	return;
        }

        
        if(oneScore == 0)
        {
        	scoreSymbol = "-";	
        	printScore(scoreSymbol);
        	
        	return;
        }        
        
        if((oneScore + twoScore) > 9) {

			totalScoreBoder[frameNumber][0] = oneScore + twoScore;
			
        	scoreSymbol = "/";
        	printScore(scoreSymbol);
        	
        	return;
        }
        
        if(twoScore == 0 && twoBall == true) {
        	scoreSymbol = "-";
            printScore(scoreSymbol);
            
            return;
        }
       
        if(twoScore != 0) {
        	scoreSymbol = ""+twoScore;
            printScore(scoreSymbol);
            
            return;
        }
       	       
        scoreSymbol = "" + oneScore;
        printScore(scoreSymbol);
        
        return;
	}
	
	public void frameBonusCheck(int frameNumber,int oneScore, Boolean twoBall) {
		if(spareCheck == 1) {
			totalScoreBoder[frameNumber-1][1] = totalScore + oneScore;
			printTotalScore(frameNumber-1);
			spareCheck = 0;
			
			totalScore = totalScoreBoder[frameNumber-1][1];
			
			return;
		}
		
		if(frameNumber > 2 && totalScoreBoder[frameNumber-2][2] == 1 && totalScoreBoder[frameNumber][2] != 1) {
			
			totalScoreBoder[frameNumber-2][1] = totalScore + totalScoreBoder[frameNumber-2][0] + totalScoreBoder[frameNumber-1][0] + oneScore;			
			printTotalScore(frameNumber-2);
			totalScoreBoder[frameNumber-2][2] = 0;
			
			totalScore = totalScoreBoder[frameNumber-2][1];
			
			return;
				
		}
		
		if(frameNumber > 1 && totalScoreBoder[frameNumber-1][2] == 1 && twoBall == true && oneScore != 0) {
			totalScoreBoder[frameNumber-1][1] = totalScore + totalScoreBoder[frameNumber-1][0] + totalScoreBoder[frameNumber][0];
			printTotalScore(frameNumber-1);
			totalScoreBoder[frameNumber-1][2] = 0;
			
			totalScore = totalScoreBoder[frameNumber-1][1];
			
			return;
		}
		
		if(frameNumber > 2 && totalScoreBoder[frameNumber-2][2] == 1 && totalScoreBoder[frameNumber][2] == 1) {
			
			if(frameNumber == 3) {
				totalScoreBoder[frameNumber-2][1] = totalScoreBoder[frameNumber-2][0] + totalScoreBoder[frameNumber-1][0] + totalScoreBoder[frameNumber][0];
			}
			
			if(frameNumber != 3) {
				totalScoreBoder[frameNumber-2][1] = totalScore + totalScoreBoder[frameNumber-2][0] + totalScoreBoder[frameNumber-1][0] + totalScoreBoder[frameNumber][0];
			}
			
			printTotalScore(frameNumber-2);
			totalScoreBoder[frameNumber-2][2] = 0;
			
			totalScore = totalScoreBoder[frameNumber-2][1];
			
			return;
		}
	}

	private void printScore(String scoreSymbol) {	
		clearScreen();
		
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		System.out.println("|    1    |    2    |    3    |    4    |    5    |    6    |    7    |    8    |    9    |      10      |");
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		
		scoreBoder = scoreBoder + "  " + scoreSymbol + " |";
		
		System.out.println(scoreBoder);
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
	}
	
	public static void clearScreen() {
		int x, y;
		for(y=0;y<10;y++) {
			for(x=0;x<10;x++) System.out.print(" ");
				System.out.println("");
		}
	}
	
	private void nomalShot(int frameNumber) {
		totalScoreBoder[frameNumber][1] = totalScore + totalScoreBoder[frameNumber][0];
	}	
	
	private void spareShot(int frameNumber) {
		totalScoreBoder[frameNumber][1] = totalScore + totalScoreBoder[frameNumber][0];
	}
	
	private void printTotalScore(int frameNumber) {
		printBorder = printBorder + "    " + totalScoreBoder[frameNumber][1] + "    |";
		System.out.println(printBorder);
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
	}
	
}
