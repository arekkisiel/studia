public class Digit {
    int position;
    String value;

    Digit(int position, String value){
        this.position=position;
        this.value=value;
    }
    public Digit shiftLeft(){
        this.position++;
        return this;
    }

    public Digit shiftRight(){
        this.position--;
        return this;
    }

    public Digit plusThree(){
        String additionResult = String.valueOf(Integer.valueOf(this.value)+3);
        this.value=additionResult.substring(additionResult.length()-1);
        return this;
    }
    public Digit plusOne(){
        String additionResult = String.valueOf(Integer.valueOf(this.value)+1);
        this.value=additionResult.substring(additionResult.length()-1);
        return this;
    }
    public Digit minusThree(){
        String subtractionResult = String.valueOf(Integer.valueOf(this.value)+7);
        this.value=subtractionResult.substring(subtractionResult.length()-1);
        return this;
    }

    public Digit minusThreeAndSignChange(){
        this.value=String.valueOf(Math.abs(Integer.valueOf(this.value)-3));
        return this;
    }

    public Digit minusOne(){
        this.value=String.valueOf(Integer.valueOf(this.value)-1);
        return this;
    }
}
