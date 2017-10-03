public class Main {

    public static void main(String[] args) {
        Parkometer parkometer = new Parkometer();
        int insertedCoin;
        while(true){
            parkometer.states.add(parkometer.state);
            System.out.println("Current balance(state): " + parkometer.state);
            switch (parkometer.state) {
                case 0:
                    insertedCoin = parkometer.insertCoin();
                    if(insertedCoin == 1)
                        parkometer.state=1;
                    if(insertedCoin == 2)
                        parkometer.state=2;
                    if(insertedCoin == 5)
                        parkometer.state=5;
                    break;
                case 1:
                    insertedCoin = parkometer.insertCoin();
                    if(insertedCoin == 1)
                        parkometer.state=2;
                    if(insertedCoin == 2)
                        parkometer.state=3;
                    if(insertedCoin == 5)
                        parkometer.state=6;
                    break;
                case 2:
                    insertedCoin = parkometer.insertCoin();
                    if(insertedCoin == 1)
                        parkometer.state=3;
                    if(insertedCoin == 2)
                        parkometer.state=4;
                    if(insertedCoin == 5)
                        parkometer.state=7;
                    break;
                case 3:
                    insertedCoin = parkometer.insertCoin();
                    if(insertedCoin == 1)
                        parkometer.state=4;
                    if(insertedCoin == 2)
                        parkometer.state=5;
                    if(insertedCoin == 5){
                        parkometer.state=0;
                        parkometer.returnMoney();
                        parkometer.states.clear();
                    }
                    break;
                case 4:
                    insertedCoin = parkometer.insertCoin();
                    if(insertedCoin == 1)
                        parkometer.state=5;
                    if(insertedCoin == 2)
                        parkometer.state=6;
                    if(insertedCoin == 5){
                        parkometer.state=0;
                        parkometer.returnMoney();
                        parkometer.states.clear();
                    }
                    break;
                case 5:
                    insertedCoin = parkometer.insertCoin();
                    if(insertedCoin == 1)
                        parkometer.state=6;
                    if(insertedCoin == 2)
                        parkometer.state=7;
                    if(insertedCoin == 5){
                        parkometer.state=0;
                        parkometer.returnMoney();
                        parkometer.states.clear();
                    }
                    break;
                case 6:
                    insertedCoin = parkometer.insertCoin();
                    if(insertedCoin == 1)
                        parkometer.state=7;
                    if(insertedCoin == 2){
                        parkometer.state=0;
                        parkometer.returnMoney();
                        parkometer.states.clear();
                    }
                    if(insertedCoin == 5){
                        parkometer.state=0;
                        parkometer.returnMoney();
                        parkometer.states.clear();
                    }
                    break;
                case 7:
                    parkometer.sellTicket();
                    parkometer.state=0;
                    parkometer.states.clear();
                    break;
            }
        }
    }
}



